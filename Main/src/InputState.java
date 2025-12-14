import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputState implements KeyListener {

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;

    public InputState() {
        up = down = left = right = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                up = true;
                break;

            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                down = true;
                break;

            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                left = true;
                break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                up = false;
                break;

            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                down = false;
                break;

            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                left = false;
                break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }
}
