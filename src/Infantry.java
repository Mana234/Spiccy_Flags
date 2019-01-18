import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Infantry extends Unit {

    public Infantry(int h, int ra, int rm, int a, int x, int y, boolean upgraded, boolean side) {
        super(h, ra, rm, a, x, y, upgraded, side);

        try {
            blue = ImageIO.read(new File("res\\infantryBlue.png"));
            blueUsed = ImageIO.read(new File("res\\infantryBlueUsed.png"));
            blueUp = ImageIO.read(new File("res\\infantryBlueUpgrade.png"));
            blueUpUsed = ImageIO.read(new File("res\\infantryBlueUpgradeUsed.png"));
        } catch (IOException e) {
        }
        try{
            red = ImageIO.read(new File("res\\infantryRed.png"));
            redUsed = ImageIO.read(new File("res\\infantryRedUsed.png"));
            redUp = ImageIO.read(new File("res\\infantryRedUpgrade.png"));
            redUpUsed = ImageIO.read(new File("res\\infantryRedUpgradeUsed.png"));
        } catch (IOException e) {
        }
    }

    public void combatDealing(Unit other)
    {
        super.combatDealing(other);
        if (Math.sqrt((Math.pow((other.getX()-this.getX()-10), 2)) + Math.pow((other.getY() - this.getY() - 10), 2)) < this.getRA()/2 && !this.getIsDead()) {
            other.setH(other.getH() - this.getAV());
            if (other.getH() < 0)
                other.setDead(true);
        }
    }

}
