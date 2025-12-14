import java.awt.*;

public class Fruit {

    private int x, y;
    private FruitType type;
    private long spawnTime;

    public Fruit(int x, int y, FruitType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.spawnTime = System.currentTimeMillis();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public FruitType getType() {
        return type;
    }

    public long getSpawnTime() {
        return spawnTime;
    }

    public void draw(Graphics2D g) {
        switch (type) {
            case APPLE -> g.setColor(Color.RED);
            case GRAPE -> g.setColor(Color.MAGENTA);
            case BANANA -> g.setColor(Color.YELLOW);
            case CHERRY -> g.setColor(Color.PINK);
            case MELON -> g.setColor(Color.GREEN);
        }
        g.fillOval(x, y, 20, 20);
    }
}

enum FruitType {
    APPLE, GRAPE, BANANA, CHERRY, MELON
}
