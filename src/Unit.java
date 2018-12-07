import java.awt.*;

public class Unit{
    //Blue team is the true team!
    private int health, rangeAttack, rangeMovement, attackValue, unitType, x, y;
    private boolean isUpgraded, side, isClicked;

    public Unit(int h, int ra, int rm, int a, int u, int x, int y, boolean upgraded, boolean side)
    {
        health=h;
        rangeAttack=ra;
        rangeMovement=rm;
        attackValue=a;
        unitType=u;
        this.x=x;
        this.y=y;
        isUpgraded=upgraded;
        this.side=side;

    }
    public int getX(){return x;}
    public int getY(){return y;}
    public void mousePressed(int X, int Y){
        if (isClicked){
            x = X;
            y = Y;
            isClicked = false;
        }
        if (X > x && X < x+20 && Y > y && Y < y+20) {
            isClicked = true;
        }
    }
    public void mouseReleased(){}
    public void paint(Graphics2D g) {


    }
}
