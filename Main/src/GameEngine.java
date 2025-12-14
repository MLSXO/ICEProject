import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * GameEngine der er Swing/JFrame-kompatibel
 * Bruger javax.swing.Timer i stedet for while-loop
 */
public class GameEngine implements ActionListener {

    private Snake snake;
    private Fruit currentFruit;
    private int score;

    private PowerUp activePowerUp;
    private Random rand;

    private boolean running;
    private Timer gameTimer;
    private int delay; // ms mellem updates (styrer speed)

    public GameEngine() {
        this.snake = new Snake();
        this.score = 0;
        this.rand = new Random();
        this.delay = 120; // start-hastighed
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

    public boolean isRunning() {
        return running;
    }

    public Snake getSnake() {
        return snake;
    }

    public Fruit getFruits() {
        return currentFruit;
    }

    public int getScore() {
        return score;
    }

    public String getActivePowerUp() {
        if (activePowerUp != null && activePowerUp.isActive()) {
            return activePowerUp.getName();
        }
        return "None";
    }

    /* ======================
       SWING GAME LOOP
       ====================== */

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!running) return;

        update();
    }

    private void update() {

        snake.move();

        // Eat fruit
        if (snake.getHeadX() == currentFruit.getX() &&
                snake.getHeadY() == currentFruit.getY()) {
            onFruitEaten();
        }

        // PowerUp expired
        if (activePowerUp != null && !activePowerUp.isActive()) {
            deactivatePowerUp();
        }

        // Self collision
        if (snake.hitSelf() && !hasGhostPowerUp()) {
            stopGame();
        }

        // Wall collision
        if (snake.hitWall()) {
            stopGame();
        }
    }

    /* ======================
       FRUIT HANDLING
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
        FruitType type = currentFruit.getType();

        switch (type) {
            case APPLE -> {
                snake.grow(1);
                addScore(10);
            }
            case GRAPE -> {
                snake.grow(2);
                addScore(20);
            }
            case BANANA -> {
                snake.grow(3);
                tempSpeedBoost(40, 2000);
            }
            case CHERRY -> {
                snake.grow(1);
                if (System.currentTimeMillis() - currentFruit.getSpawnTime() < 3000) {
                    addScore(50);
                }
            }
            case MELON -> {
                snake.grow(2);
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
            case SLOW -> resetSpeed();
            case DOUBLE_SCORE -> {} // ingen ekstra handling
        }

        activePowerUp = null;
    }

    private boolean hasGhostPowerUp() {
        return activePowerUp != null && activePowerUp.getType() == PowerUp.Type.GHOST;
    }

    /* ======================
       SPEED CONTROL (Swing-safe)
       ====================== */

    private void tempSpeedBoost(int newDelay, int durationMs) {
        gameTimer.setDelay(newDelay);
        new Timer(durationMs, e -> resetSpeed()).start();
    }

    private void resetSpeed() {
        gameTimer.setDelay(delay);
    }
}

