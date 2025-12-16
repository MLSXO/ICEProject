import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameEngine implements ActionListener {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int TILE_SIZE = 20;

    private Snake snake;
    private Fruit currentFruit;
    private int score;

    private PowerUp activePowerUp;
    private Random rand;

    private boolean running;
    private boolean endlessMode = false;

    private Timer gameTimer;
    private int delay = 120; // default timer delay

    public GameEngine() {
        snake = new Snake();
        rand = new Random();
        score = 0;

        spawnFruit();
        gameTimer = new Timer(delay, this);
    }

    /* ======================
       GAME STATE
       ====================== */
    public void startGame() {
        running = true;
        gameTimer.start();
    }

    public void stopGame() {
        running = false;
        gameTimer.stop();
    }

    public boolean isRunning() { return running; }

    public void toggleEndlessMode() { endlessMode = !endlessMode; }

    public Snake getSnake() { return snake; }

    public Fruit getFruits() { return currentFruit; }

    public int getScore() { return score; }

    public String getActivePowerUp() {
        if (activePowerUp != null && activePowerUp.isActive()) return activePowerUp.getName();
        return "None";
    }

    /* ======================
       GAME LOOP
       ====================== */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!running) return;
        update();
    }

    private void update() {
        snake.move();

        if (endlessMode) snake.wrapAround(WIDTH, HEIGHT);

        if (snake.getHeadX() == currentFruit.getX() &&
                snake.getHeadY() == currentFruit.getY()) {
            onFruitEaten();
        }

        // PowerUp deaktiveres automatisk når tid er udløbet
        if (activePowerUp != null && !activePowerUp.isActive()) deactivatePowerUp();

        if (snake.hitSelf() && !hasGhostPowerUp()) stopGame();

        if (!endlessMode && snake.hitWall()) stopGame();
    }

    /* ======================
       FRUITS
       ====================== */
    private void spawnFruit() {
        FruitType type;
        int roll = rand.nextInt(100);

        if (roll < 50) type = FruitType.APPLE;
        else if (roll < 70) type = FruitType.GRAPE;
        else if (roll < 85) type = FruitType.BANANA;
        else if (roll < 95) type = FruitType.CHERRY;
        else type = FruitType.MELON;

        int maxX = (WIDTH / TILE_SIZE) - 1;
        int maxY = (HEIGHT / TILE_SIZE) - 1;

        int x = rand.nextInt(maxX + 1) * TILE_SIZE;
        int y = rand.nextInt(maxY + 1) * TILE_SIZE;

        currentFruit = new Fruit(x, y, type);
    }

    private void onFruitEaten() {
        switch (currentFruit.getType()) {
            case APPLE -> {
                snake.grow(1);
                snake.setBodyColor(Color.GREEN);
                addScore(10);
            }
            case GRAPE -> {
                snake.grow(2);
                snake.setBodyColor(new Color(138, 43, 226));
                addScore(20);
            }
            case BANANA -> {
                snake.grow(3);
                snake.setBodyColor(Color.YELLOW);
                snake.setSpeed(2);
                tempSpeedBoost(40, 2000); // midlertidig hastighedsboost
            }
            case CHERRY -> {
                snake.grow(1);
                snake.setBodyColor(Color.PINK);
                if (System.currentTimeMillis() - currentFruit.getSpawnTime() < 3000) addScore(50);
            }
            case MELON -> {
                snake.grow(2);
                snake.setBodyColor(Color.ORANGE);
                activateRandomPowerUp();
            }
        }

        spawnFruit();
    }

    /* ======================
       SCORE & POWERUPS
       ====================== */
    private void addScore(int amount) {
        if (activePowerUp != null && activePowerUp.getType() == PowerUp.Type.DOUBLE_SCORE) {
            score += amount * 2;
        } else {
            score += amount;
        }
    }

    private void activateRandomPowerUp() {
        int roll = rand.nextInt(3);

        switch (roll) {
            case 0 -> { // GHOST
                activePowerUp = new PowerUp(PowerUp.Type.GHOST, 3000);
                snake.setPhase(true);
            }
            case 1 -> { // SLOW
                activePowerUp = new PowerUp(PowerUp.Type.SLOW, 5000);
                tempSpeedBoost(200, 5000);
            }
            case 2 -> { // DOUBLE_SCORE
                activePowerUp = new PowerUp(PowerUp.Type.DOUBLE_SCORE, 10000);
            }
        }

        activePowerUp.activate();
    }

    private void deactivatePowerUp() {
        if (activePowerUp == null) return;

        switch (activePowerUp.getType()) {
            case GHOST -> snake.setPhase(false);
            case SLOW -> {
                resetSpeed();
                snake.setSpeed(1);
            }
            case DOUBLE_SCORE -> {} // ingen ekstra handling
        }

        activePowerUp = null;
    }

    private boolean hasGhostPowerUp() {
        return activePowerUp != null && activePowerUp.getType() == PowerUp.Type.GHOST;
    }

    /* ======================
       SPEED CONTROL
       ====================== */
    private void tempSpeedBoost(int newDelay, int durationMs) {
        gameTimer.setDelay(newDelay);
        // Power-up holder styr på varighed, reset sker automatisk når power-up udløber
    }

    private void resetSpeed() {
        gameTimer.setDelay(delay);
        snake.setSpeed(1);
    }
}
