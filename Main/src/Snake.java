import java.awt.*;
import java.util.LinkedList;

public class Snake {

    private LinkedList<Point> body;
    private Direction direction;
    private int speed;
    private boolean phase;

    private int width = 800;
    private int height = 600;
    private int tileSize = 20;

    public Snake() {
        body = new LinkedList<>();
        body.add(new Point(100, 100));
        body.add(new Point(80, 100));
        body.add(new Point(60, 100));
        direction = Direction.RIGHT;
        speed = 5;
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
       COLLISION
       ====================== */

    public boolean hitSelf() {
        Point head = body.getFirst();
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) return true;
        }
        return false;
    }

    public boolean hitWall() {
        Point head = body.getFirst();
        return head.x < 0 || head.y < 0 ||
                head.x >= width || head.y >= height;
    }

    /* ======================
       GETTERS & SETTERS
       ====================== */

    public int getHeadX() {
        return body.getFirst().x;
    }

    public int getHeadY() {
        return body.getFirst().y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setPhase(boolean value) {
        this.phase = value;
    }

    /* ======================
       DRAW
       ====================== */

    public void draw(Graphics2D g) {
        g.setColor(Color.GREEN);
        for (Point p : body) {
            g.fillRect(p.x, p.y, tileSize, tileSize);
        }
    }
}

enum Direction {
    UP, DOWN, LEFT, RIGHT
}
