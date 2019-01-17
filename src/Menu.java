import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class Menu {
    BufferedImage logo, exit, start;
    public Menu()
    {
        try {
            logo = ImageIO.read(new File("res\\Logo2.png"));
            exit = ImageIO.read(new File("res\\ExitGameButton.png"));
            start = ImageIO.read(new File("res\\StartGameButton.png"));
        } catch (IOException e) {
        }
    }

    public void mouseReleased(int X, int Y) {
        if(X > 275 && X < 650 && Y > 300 && Y < 425)
            State
        if(X > 350 && X < 550 && Y > 400 && Y < 500)
            System.exit(0);
    }

    public void paint(Graphics2D g) {
        g.drawImage(logo, 250, 20,null);
        g.drawImage(exit, 350, 400,200,100,null);
        g.drawImage(start, 275, 300,375,125,null);
    }
}
