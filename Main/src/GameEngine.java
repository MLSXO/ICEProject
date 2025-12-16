import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameEngine implements ActionListener {

    private Snake snake;
    private Fruit currentFruit;
    private int score;

    private PowerUp activePowerUp;
    private Random rand;

    private boolean running;
    private boolean endlessMode = false;

    private Timer gameTimer;
    private int delay = 120;

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

        if (endlessMode) snake.wrapAround(800, 600);

        if (snake.getHeadX() == currentFruit.getX() &&
                snake.getHeadY() == currentFruit.getY()) {
            onFruitEaten();
        }

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

        int x = rand.nextInt(40) * 20;
        int y = rand.nextInt(30) * 20;

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
                tempSpeedBoost(40, 2000);
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
        } else score += amount;
    }

    private void activateRandomPowerUp() {
        int roll = rand.nextInt(3);

        switch (roll) {
            case 0 -> {
                activePowerUp = new PowerUp(PowerUp.Type.GHOST, 3000);
                snake.setPhase(true);
            }
            case 1 -> {
                activePowerUp = new PowerUp(PowerUp.Type.SLOW, 5000);
                tempSpeedBoost(200, 5000);
            }
            case 2 -> activePowerUp = new PowerUp(PowerUp.Type.DOUBLE_SCORE, 10000);
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
            case DOUBLE_SCORE -> {}
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
        new Timer(durationMs, e -> resetSpeed()).start();
    }

    private void resetSpeed() {
        gameTimer.setDelay(delay);
        snake.setSpeed(1);
    }
}
