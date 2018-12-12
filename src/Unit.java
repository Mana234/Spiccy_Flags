import java.awt.*;

public class Unit{
    //Blue team is the true team!
    private int health, rangeAttack, rangeMovement, attackValue, unitType, x, y;
    private boolean isUpgraded, side, isClicked, canAct;

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
        canAct=side;
    }
    public int getX(){return x;}
    public int getY(){return y;}
    public int getRA(){return rangeMovement;}
    public int getH(){return health;}
    public boolean getAct(){return canAct;}
    public boolean isClicked(){return isClicked;}

    public void setH(int h){health = h;}
    public void setCanAct(boolean act){canAct=act;}
    public void mousePressed(int X, int Y){
        if (X > x && X < x+20 && Y > y && Y < y+20 && canAct) {
            isClicked = true;
        }
    }
    public void mouseReleased(int X, int Y){
        if (isClicked){//&& Math.sqrt((Math.pow((X-x-10),2))+Math.pow((Y-y-10),2))<rangeMovement/2){
        x = X-10;
        y = Y-10;
        isClicked = false;
        canAct=false;
        }
    }
    public void combat(Unit other){
        if(Math.sqrt((Math.pow((other.x-x-10),2))+Math.pow((other.y-y-10),2))<rangeAttack/2) {
            other.setH(other.getH() - attackValue);
            if (other.getH() < 0)
                other.health = 0;
        }
        if(Math.sqrt((Math.pow((other.x-x-10),2))+Math.pow((other.y-y-10),2))<other.rangeAttack/2) {
            health = health - other.attackValue;
            if (health < 0)
                health = 0;
        }
    }

    public void paint(Graphics2D g) {
        if(canAct==false)
            g.setColor(Color.black);
        else if(side)
            g.setColor(Color.blue);
        else
            g.setColor(Color.red);
        g.fillRect(x,y,20,20);

        if(isClicked)
        {
            g.setColor(Color.red);
            g.drawOval(x-(rangeMovement/2-10),y-(rangeMovement/2-10),rangeMovement,rangeMovement);
        }
    }
}
