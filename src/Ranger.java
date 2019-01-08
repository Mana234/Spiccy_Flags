import javax.imageio.ImageIO;
import java.io.*;

public class Ranger extends Unit {
    private int critChance;
    private int critDamage;

    public Ranger(int h, int ra, int rm, int a, int x, int y, boolean upgraded, boolean side) {
        super(h, ra, rm, a, x, y, upgraded, side);

        try {
            blue = ImageIO.read(new File("res\\rangerBlue.png"));
            blueUsed = ImageIO.read(new File("res\\rangerBlueUsed.png"));
            blueUp = ImageIO.read(new File("res\\rangerBlueUpgraded.png"));
            blueUpUsed = ImageIO.read(new File("res\\rangerBlueUpgradedUsed.png"));
        } catch (IOException e) {
        }
        try{
            red = ImageIO.read(new File("res\\rangerRed.png"));
            redUsed = ImageIO.read(new File("res\\rangerRedUsed.png"));
            redUp = ImageIO.read(new File("res\\rangerRedUpgraded.png"));
            redUpUsed = ImageIO.read(new File("res\\rangerRedUpgradedUsed.png"));
        } catch (IOException e) {
        }

        if(upgraded){
            critChance=15;
            critDamage=2;
        }
        else{
            critChance=10;
            critDamage=5;
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
        if(this.isClicked()&& !inRange(other) && X > other.getX() && X < other.getX()+20 && Y > other.getY() && Y < other.getY()+20){
            double crit=Math.random()*100;
            if(crit<critChance)
                other.setH(other.getH()-15-(other.getMH()/critDamage));
            else
                other.setH(other.getH()-15);
            this.setCanAct(false);
        }
    }

}
