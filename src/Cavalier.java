import javax.imageio.ImageIO;
import java.io.*;

public class Cavalier extends Unit {

    public Cavalier(int h, int ra, int rm, int a, int x, int y, boolean upgraded, boolean side) {
        super(h, ra, rm, a, x, y, upgraded, side);

        try {
            blue = ImageIO.read(new File("res\\cavalryBlue.png"));
            blueUsed = ImageIO.read(new File("res\\cavalryBlueUsed.png"));
            blueUp = ImageIO.read(new File("res\\cavalryBlueUpgraded.png"));
            blueUpUsed = ImageIO.read(new File("res\\cavalryBlueUpgradedUsed.png"));
        } catch (IOException e) {
        }
        try{
            red = ImageIO.read(new File("res\\cavalryRed.png"));
            redUsed = ImageIO.read(new File("res\\cavalryRedUsed.png"));
            redUp = ImageIO.read(new File("res\\cavalryRedUpgraded.png"));
            redUpUsed = ImageIO.read(new File("res\\cavalryRedUpgradedUsed.png"));
        } catch (IOException e) {
        }
    }

    public void combat(Unit other){
        //if()
    }

}
