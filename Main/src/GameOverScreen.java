import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverScreen extends JFrame {

    private boolean restart;

    public GameOverScreen(int score) {
        super("Game Over");
        restart = false;

        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Game Over", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.RED);

        JLabel scoreLabel = new JLabel("Score: " + score, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JPanel buttonPanel = new JPanel();

        JButton restartButton = new JButton("Restart");
        JButton quitButton = new JButton("Quit");

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restart = true;
                dispose();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restart = false;
                dispose();
            }
        });

        buttonPanel.add(restartButton);
        buttonPanel.add(quitButton);

        add(title, BorderLayout.NORTH);
        add(scoreLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void display() {
        setVisible(true);

        // Vent indtil vinduet lukkes
        while (isDisplayable()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {}
        }
    }

    public boolean playerWantsRestart() {
        return restart;
    }
}
