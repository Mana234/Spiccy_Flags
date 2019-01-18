import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Armor extends Unit{
    public Armor(int h, int ra, int rm, int a, int x, int y, boolean upgraded, boolean side){
        super(h,ra,rm,a,x,y,upgraded,side);
        try {
            blue = ImageIO.read(new File("res\\armourBlue.png"));
            blueUsed = ImageIO.read(new File("res\\armourBlueUsed.png"));
            blueUp = ImageIO.read(new File("res\\armourBlueUpgrade.png"));
            blueUpUsed = ImageIO.read(new File("res\\armourBlueUpgradeUsed.png"));
        } catch (IOException e) {
        }
        try{
            red = ImageIO.read(new File("res\\armourRed.png"));
            redUsed = ImageIO.read(new File("res\\armourRedUsed.png"));
            redUp = ImageIO.read(new File("res\\armourRedUpgrade.png"));
            redUpUsed = ImageIO.read(new File("res\\armourRedUpgradeUsed.png"));
        } catch (IOException e) {
        }
    }

}
