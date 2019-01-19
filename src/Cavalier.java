import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class Cavalier extends Unit {

    public Cavalier(int h, int ra, int rm, int a, int x, int y, boolean upgraded, boolean side) {
        super(h, ra, rm, a, x, y, upgraded, side);

        try {
            blue = ImageIO.read(new File("res\\cavalryBlue.png"));
            blueUsed = ImageIO.read(new File("res\\cavalryBlueUsed.png"));
            blueUp = ImageIO.read(new File("res\\cavalryBlueUpgrade.png"));
            blueUpUsed = ImageIO.read(new File("res\\cavalryBlueUpgradedUse.png"));
        } catch (IOException e) {
        }
        try{
            red = ImageIO.read(new File("res\\cavalryRed.png"));
            redUsed = ImageIO.read(new File("res\\cavalryRedUsed.png"));
            redUp = ImageIO.read(new File("res\\cavalryRedUpgrade.png"));
            redUpUsed = ImageIO.read(new File("res\\cavalryRedUpgradeUsed.png"));
        } catch (IOException e) {
        }
    }

    public void upgrade(int X, int Y){
        super.upgrade(X,Y);
        if (X > getX() && X < getX()+20 && Y > getY() && Y < getY()+20){
            setRM(getRM()+50);
            setAV(getAV()+10);
        }
    }

    public void combatDealing(Unit other){
        super.combatDealing(other);
        int x = this.getX()-this.getMX()+10;
        int y = this.getY()-this.getMY()+10;
        int X = other.getX()-this.getMX()+10;
        int Y = other.getY()-this.getMY()+10;

        if(Math.atan2(X,Y)-Math.atan2(x,y)<0.07 && Math.atan2(X,Y)-Math.atan2(x,y)>-0.07 && !other.getIsDead()&& Math.sqrt((Math.pow((other.getX() - this.getMX() - 10), 2)) + Math.pow((other.getY() - this.getMY() - 10), 2)) < Math.sqrt((Math.pow((this.getX()-this.getMX()), 2)) + Math.pow((this.getY() - this.getMY()), 2))){
            other.setH(other.getH() - 15);
            if (other.getH() < 0)
                other.setDead(true);
        }
    }

    public void paint(Graphics2D g){
        super.paint(g);
        int x = this.getX()-this.getMX()+10;
        int y =this.getY()-this.getMY()+10;
        double h = Math.sqrt((Math.pow((this.getX()-this.getMX()), 2)) + Math.pow((this.getY() - this.getMY()), 2));
        if(isClicked()) {

            g.drawLine(this.getMX() + 10, this.getMY() + 10, (int) (Math.sin(Math.atan2(x, y) + 0.07)*h+this.getMX()), (int) (Math.cos(Math.atan2(x, y) + 0.07)*h+this.getMY()));
            g.drawLine(this.getMX() + 10, this.getMY() + 10, (int) (Math.sin(Math.atan2(x, y) - 0.07)*h+this.getMX()), (int) (Math.cos(Math.atan2(x, y) - 0.07)*h+this.getMY()));
        }
    }

}
