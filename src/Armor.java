import javax.imageio.*;
import java.io.*;


public class Armor extends Unit {

    public Armor(int h, int ra, int rm, int a, int x, int y, boolean upgraded, boolean side) {
        super(h, ra, rm, a, x, y, upgraded, side);


        try {
            blue = ImageIO.read(new File("res\\armourBlue.png"));
            blueUsed = ImageIO.read(new File("res\\armourBlueUsed.png"));
            blueUp = ImageIO.read(new File("res\\armourBlueUpgraded.png"));
            blueUpUsed = ImageIO.read(new File("res\\armourBlueUpgradedUsed.png"));
        } catch (IOException e) {
        }
        try{
            red = ImageIO.read(new File("res\\armourRed.png"));
            redUsed = ImageIO.read(new File("res\\armourRedUsed.png"));
            redUp = ImageIO.read(new File("res\\armourRedUpgraded.png"));
            redUpUsed = ImageIO.read(new File("res\\armourRedUpgradedUsed.png"));
        } catch (IOException e) {
        }
    }

}
