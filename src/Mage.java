import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Mage extends Unit {

    public Mage(int h, int ra, int rm, int a, int x, int y, boolean upgraded, boolean side) {
        super(h, ra, rm, a, x, y, upgraded, side);

        try {
            blue = ImageIO.read(new File("res\\mageBlue.png"));
            blueUsed = ImageIO.read(new File("res\\mageBlueUsed.png"));
            blueUp = ImageIO.read(new File("res\\mageBlueUpgraded.png"));
            blueUpUsed = ImageIO.read(new File("res\\mageBlueUpgradedUsed.png"));
        } catch (IOException e) {
        }
        try{
            red = ImageIO.read(new File("res\\mageRed.png"));
            redUsed = ImageIO.read(new File("res\\mageRedUsed.png"));
            redUp = ImageIO.read(new File("res\\mageRedUpgraded.png"));
            redUpUsed = ImageIO.read(new File("res\\mageRedUpgradedUsed.png"));
        } catch (IOException e) {
        }
    }

    public boolean inRange(Unit other)
    {
        if(Math.sqrt((Math.pow((other.getX()-10), 2)) + Math.pow((other.getY() - getY() - 10), 2)) < getRM()/2 )
            return true;
        else
            return false;
    }

    public void ability(int X, int Y, Unit other){
        super.ability(X,Y,other);

        int x = Math.abs(other.getX()-this.getMX());
        int y = Math.abs(other.getY()-this.getMY());
        double h = Math.sqrt((Math.pow((other.getX()-this.getMX()), 2)) + Math.pow((other.getY() - this.getMY()), 2))-100;

        if(this.isClicked()&& !inRange(other) && X > other.getX() && X < other.getX()+20 && Y > other.getY() && Y < other.getY()+20){
            double theta= Math.atan2(y,x);
            other.setX((int)(Math.cos(theta)*h+this.getMX()));
            other.setY((int)(Math.sin(theta)*h+this.getMY()));
            this.setCanAct(false);
        }
    }
}
