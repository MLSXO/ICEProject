import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    private InputState input;
    private GamePanel panel;

    public GameWindow(String title, int width, int height) {
        super(title);

        input = new InputState();
        panel = new GamePanel();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(width, height);
        setLocationRelativeTo(null);

        add(panel);
        addKeyListener(input);

        setVisible(true);
    }

    public InputState getInput() {
        return input;
    }

    public Graphics2D getGraphics2D() {
        return (Graphics2D) panel.getGraphics();
    }
}

class GamePanel extends JPanel {

    public GamePanel() {
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
