import javax.swing.*;
import java.awt.*;


public class GUI {
    private int score;
    private int speed;
    private String activePowerUp;
    public GUI(){
        this.score = 0;
        this.speed = 0;
        this.activePowerUp = "None";
    }
   public void update (int score, int speed, String activePowerUp) {
       this.score = score;
       this.speed = speed;
       this.activePowerUp = activePowerUp != null ? activePowerUp : "None";
   }
   public void draw (Graphics2D g) {
       g.setColor(Color.WHITE);
       g.setFont(new Font("Arial", Font.BOLD, 20));

       g.drawString("Score: " + score, 20, 30);
       g.drawString("Speed: " + speed, 20, 55);
       g.drawString("Power-Up: " + activePowerUp, 20, 80);

   }

}
