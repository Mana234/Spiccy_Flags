import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class Menu {
    BufferedImage logo, exit, start,redWin,blueWin;
    public Menu()
    {
        try {
            logo = ImageIO.read(new File("res\\Logo2.png"));
            exit = ImageIO.read(new File("res\\ExitGameButton.png"));
            start = ImageIO.read(new File("res\\StartGameButton.png"));
        } catch (IOException e) {
        }
        try {
            redWin=ImageIO.read(new File("res\\RedWin.png"));
            blueWin=ImageIO.read(new File("res\\BlueWin.png"));
        } catch (IOException e) {
        }
    }

    public void mouseReleased(int X, int Y) {
        if(X > 275 && X < 650 && Y > 300 && Y < 425)
            SpiccyFlags.State= SpiccyFlags.State.StartUp;
        if(X > 350 && X < 550 && Y > 400 && Y < 500)
            System.exit(0);
    }

    public void paint(Graphics2D g) {
        if(SpiccyFlags.State== SpiccyFlags.STATE.Menu){
            g.drawImage(logo, 265, 20,null);
            g.drawImage(exit, 365, 400,200,100,null);
            g.drawImage(start, 290, 300,375,125,null);
        }
        if(SpiccyFlags.State== SpiccyFlags.STATE.BlueWin)
            g.drawImage(blueWin, 275, 100,400,200,null);
        if(SpiccyFlags.State== SpiccyFlags.STATE.RedWin)
            g.drawImage(redWin, 275, 100,400,200,null);
    }
}
