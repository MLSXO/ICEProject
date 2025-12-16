import java.awt.*;
import java.util.LinkedList;

public class Snake {

    private LinkedList<Point> body;
    private Direction direction;
    private boolean phase;

    private Color bodyColor = Color.GREEN;

    private int speed = 1; // bruges til GUI
    private final int tileSize = 20;
    private final int width = 800;
    private final int height = 600;

    public Snake() {
        body = new LinkedList<>();
        body.add(new Point(100, 100));
        body.add(new Point(80, 100));
        body.add(new Point(60, 100));
        direction = Direction.RIGHT;
        phase = false;
    }

    /* ======================
       MOVEMENT
       ====================== */
    public void move() {
        Point head = body.getFirst();
        Point newHead = new Point(head);

        switch (direction) {
            case UP -> newHead.y -= tileSize;
            case DOWN -> newHead.y += tileSize;
            case LEFT -> newHead.x -= tileSize;
            case RIGHT -> newHead.x += tileSize;
        }

        body.addFirst(newHead);
        body.removeLast();
    }

    public void grow(int amount) {
        for (int i = 0; i < amount; i++) {
            body.addLast(new Point(body.getLast()));
        }
    }

    /* ======================
       ENDLESS MODE
       ====================== */
    public void wrapAround(int screenWidth, int screenHeight) {
        Point head = body.getFirst();

        if (head.x < 0) head.x = screenWidth - tileSize;
        if (head.x >= screenWidth) head.x = 0;
        if (head.y < 0) head.y = screenHeight - tileSize;
        if (head.y >= screenHeight) head.y = 0;
    }

    /* ======================
       COLLISION
       ====================== */
    public boolean hitSelf() {
        if (phase) return false;

        Point head = body.getFirst();
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) return true;
        }
        return false;
    }

    public boolean hitWall() {
        Point head = body.getFirst();
        return head.x < 0 || head.y < 0 || head.x >= width || head.y >= height;
    }

    /* ======================
       GETTERS / SETTERS
       ====================== */
    public int getHeadX() { return body.getFirst().x; }
    public int getHeadY() { return body.getFirst().y; }
    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }
    public void setDirection(Direction direction) { this.direction = direction; }
    public void setPhase(boolean value) { this.phase = value; }
    public void setBodyColor(Color color) { this.bodyColor = color; }

    /* ======================
       DRAW
       ====================== */
    public void draw(Graphics2D g) {
        g.setColor(phase ? Color.CYAN : bodyColor);

        LinkedList<Point> copy = new LinkedList<>(body);
        for (Point p : copy) {
            g.fillRect(p.x, p.y, tileSize, tileSize);
        }
    }
}

enum Direction {
    UP, DOWN, LEFT, RIGHT
}
