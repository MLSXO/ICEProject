import java.awt.*;
import java.util.Random;

enum FruitType {
    APPLE, // +1 length, +10 points
    GRAPE, // +2 length, +20 points
    BANANA, // +3 length, temporary speed boost
    CHERRY, // +1 length, +50 points IF eaten in time
    MELON // +2 length + activates powerup
}

// FRUIT CATEGORY

class Fruit {
   private int x, y;
   private FruitType type;
   private long spawnTime; // Used for CHERRY

    public Fruit(int x, int y, FruitType type) {
       this.x = x;
       this.y = y;
       this.type = type;
       this.spawnTime = System.currentTimeMillis();
    }
   public int getX() { return x; }
   public int getY() { return y; }
   public FruitType getType() { return type; }
   public long getSpawnTime() { return spawnTime; }

   public void draw(Graphics2D g) {
      switch (type) {
          case APPLE:
              g.setColor(Color.RED);
              break;
          case GRAPE:
              g.setColor(Color.MAGENTA);
              break;
          case BANANA:
              g.setColor(Color.YELLOW);
              break;
          case CHERRY:
              g.setColor(Color.PINK);
              break;
          case MELON:
              g.setColor(Color.GREEN)
               break;
      }
      g.fillOval(x, y, 20, 20);
   }
}

// Power Up Types

enum PowerUpType {
   PHASE_THROUGH,
   SLOW_MOTION,
   DOUBLE_POINTS
}


// Active Power-Up

class PowerUp {
   private PowerUpType type;
   private long duration;
   private long activatedAT;

   public PowerUp(PowerUpType type, long durationsMs) {
       this.type = type;
       this.duration = durationMs;
       this.activatedAT = System.currentTimeMillis();
   }
    public boolean isActive() {
       return System.currentTimeMillis() - activatedAT < duration;
    }
    public PowerUpType getType() {
       return type;
    }
}

//

// ---------------------------


private Snake snake;
private Fruit currentFruit;
private int score;
private PowerUp activePowerUp;
private Random rand = new Random();


public GameEngine() {
    spawnFruit();
}


public Snake getSnake() { return snake; }
public Fruit getFruits() { return currentFruit; }
public int getScore() { return score; }
public String getActivePowerUp() {
    return activePowerUp != null && activePowerUp.isActive() ? activePowerUp.getType().toString() : "None";
}


public void spawnFruit() {
    FruitType type;


    int roll = rand.nextInt(100);
    if (roll < 50) type = FruitType.APPLE;
    else if (roll < 70) type = FruitType.GRAPE;
    else if (roll < 85) type = FruitType.BANANA;
    else if (roll < 95) type = FruitType.CHERRY;
    else type = FruitType.MELON;


    currentFruit = new Fruit(rand.nextInt(40)*20, rand.nextInt(30)*20, type);
}



// DET ER JOHNNYS EKESEMPEL

/* Call when snake eats fruit
public void onFruitEaten() {
    FruitType type = currentFruit.getType();


    switch(type) {
        case APPLE:
            snake.grow(1);
            score += 10;
            break;


        case GRAPE:
            snake.grow(2);
            score += 20;
            break;


        case BANANA:
            snake.grow(3);
            snake.tempSpeedBoost(2000); // 2 seconds
            break;


        case CHERRY:
            if (System.currentTimeMillis() - currentFruit.getSpawnTime() < 3000) {
                score += 50; // Only if eaten fast
            }
            snake.grow(1);
            break;


        case MELON:
            snake.grow(2);
            activateRandomPowerUp();
            break;
    }


    spawnFruit();
}


private void activateRandomPowerUp() {
    int roll = rand.nextInt(3);
    switch (roll) {
        case 0:
            activePowerUp = new PowerUp(PowerUpType.PHASE_THROUGH, 3000);
            break;
        case 1:
            activePowerUp = new PowerUp(PowerUpType.SLOW_MOTION, 5000);
            break;
        case 2:
            activePowerUp = new PowerUp(PowerUpType.DOUBLE_POINTS, 10000);
            break;
    }
}
}
*/