import java.awt.*;

public class Unit{
    //Blue team is the true team!
    private int health, rangeAttack, rangeMovement, attack, unitType, x, y;
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
    public int getRA(){return rangeMovement;}
    public int getH(){return health;}
    public boolean isClicked(){return isClicked;}

    public int setH(int h){health = h;}
    public void mousePressed(int X, int Y){
        int xl = Math.max(x, X);
        int yl = Math.max(y, Y);
        int xs = Math.min(x, X);
        int ys = Math.min(y, Y);
        if (isClicked && Math.sqrt((Math.pow((xl-xs),2))+Math.pow((yl-ys),2))<100){
            x = X;
            y = Y;
            isClicked = false;
        }
        if (X > x && X < x+20 && Y > y && Y < y+20) {
            isClicked = true;
        }
    }
    public void combat(Unit other){
        other.setH(other.getH()-attack);
        if (other.getH()>0) {
            health = 0;
        }
    }
    public void mouseReleased(){}
    public void paint(Graphics2D g) {


    }
}
