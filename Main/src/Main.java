import javax.swing.*;

public class Main {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int FPS = 60;

    public static void main (String[] args) {

        // Game Window
        GameWindow window = new GameWindow("Snake Game", WIDTH, HEIGHT);
        Renderer renderer = new Renderer(window);

        // Game main components
        GameEngine engine = new GameEngine();
        GUI gui = new GUI();

        // Start game
        engine.startGame();

        long frameTime = 1000 / FPS;

        // Game Loop
        while (engine.isRunning()) {

            long start = System.currentTimeMillis();

            // Inputs
            InputState input = window.getInput();

            if (input.isUp()) engine.getSnake().setDirection(Direction.UP);
            if (input.isDown()) engine.getSnake().setDirection(Direction.DOWN);
            if (input.isLeft()) engine.getSnake().setDirection(Direction.LEFT);
            if (input.isRight()) engine.getSnake().setDirection(Direction.RIGHT);

            // Update
            engine.update();
            gui.update(engine.getScore(), engine.getSnake().getSpeed(), engine.getActivePowerUp());

            // Draw
            renderer.clear();
            renderer.draw(engine.getSnake());
            renderer.draw(engine.getFruits());
            renderer.draw(gui);
            renderer.display();

            // FPS Control
            long elapsed = System.currentTimeMillis() - start;
            long sleep = frameTime - elapsed;

            if (sleep > 0) {
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // Game over screen
        GameOverScreen over = new GameOverScreen(engine.getScore());
        over.display();

        // Restart?
        if (over.playerWantsRestart()) {
            main(args); // Restart game
        } else {
            System.exit(0);
        }
    }
}

