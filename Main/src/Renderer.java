import java.awt.*;

/**
 * EGEN Renderer-klasse (IKKE javax.swing.Renderer!)
 * Bruges til at tegne Snake, Fruit og GUI
 */
public class Renderer {

    private GameWindow window;

    public Renderer(GameWindow window) {
        this.window = window;
    }

    public void clear() {
        Graphics2D g = window.getGraphics2D();
        if (g == null) return;

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, window.getWidth(), window.getHeight());
    }

    public void draw(Snake snake) {
        Graphics2D g = window.getGraphics2D();
        if (g != null) snake.draw(g);
    }

    public void draw(Fruit fruit) {
        Graphics2D g = window.getGraphics2D();
        if (g != null) fruit.draw(g);
    }

    public void draw(GUI gui) {
        Graphics2D g = window.getGraphics2D();
        if (g != null) gui.draw(g);
    }

    public void display() {
        // Swing håndterer repaint automatisk
    }
}
