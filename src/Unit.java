import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class Unit{
    //Blue team is the true team!
    private int health,maxhealth, rangeAttack, rangeMovement, attackValue, unitType, x, y, mx, my;
    private boolean isUpgraded, side, isClicked, canAct, isDead, toggle, highlight;

    public Unit(int h, int ra, int rm, int a, int u, int x, int y, boolean upgraded, boolean side)
    {
        health=h;
        maxhealth=h;
        rangeAttack=ra;
        rangeMovement=rm;
        attackValue=a;
        unitType=u;
        this.x=x;
        this.y=y;
        mx=x;
        my=y;
        isUpgraded=upgraded;
        this.side=side;
        canAct=side;
        isDead=false;
    }

    public int getX(){return x;}
    public int getY(){return y;}
    public int getRM(){return rangeMovement;}
    public int getRA(){return rangeAttack;}
    public int getAV(){return attackValue;}
    public int getH(){return health;}
    public int getMH() {return maxhealth; }
    public boolean getAct(){return canAct;}
    public boolean getIsDead(){return isDead;}
    public boolean isClicked(){return isClicked;}

    public void setH(int h){health = h;}
    public void setX(int X){x=X;}
    public void setCanAct(boolean act){canAct=act;}
    public void setDead(boolean dead){isDead=dead;}
    public void mousePressed(int X, int Y){
        if (X > x && X < x+20 && Y > y && Y < y+20 && canAct)
            isClicked = true; mx = x; my = y;

        if(X > x && X < x+20 && Y > y && Y < y+20 && !canAct&& highlight || canAct)
            highlight=false;
        else if(X > x && X < x+20 && Y > y && Y < y+20 )
            highlight=true;

    }
    public void mouseDragged(int X, int Y){
        if(isClicked)
        {
            x=X-10;
            y=Y-10;
        }
    }
    public void mouseReleased(int X, int Y) {
        if (isClicked && !isDead && Math.sqrt((Math.pow((X - mx - 10), 2)) + Math.pow((Y - my - 10), 2)) < rangeMovement / 2) {
            x = X - 10;
            y = Y - 10;
            canAct = false;
        }
        else if(Math.sqrt((Math.pow((X - mx - 10), 2)) + Math.pow((Y - my - 10), 2)) > rangeMovement / 2){
            x=mx;
            y=my;
        }
        isClicked = false;
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE&&toggle)
        {
            toggle=false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_SPACE&&!toggle)
        {
            toggle=true;
        }
    }

    public void ability(int X, int Y, Unit other)
    {

    }

    public void combat(Unit other){
        if (Math.sqrt((Math.pow((other.x-x-10), 2)) + Math.pow((other.y - y - 10), 2)) < rangeAttack/2 ) {
            other.setH(other.getH() - attackValue);
            if (other.getH() < 0)
                other.isDead = true;
        }
        if (Math.sqrt((Math.pow((other.x - x - 10), 2)) + Math.pow((other.y - y - 10), 2)) < other.rangeAttack/2 ) {
            health = health - other.attackValue;
            if (health < 0)
                isDead = true;
        }

    }

    public void paint(Graphics2D g) {
        if(!canAct)
            g.setColor(Color.black);
        else if(isUpgraded)
            g.setColor(Color.yellow);
        else if(side)
            g.setColor(Color.blue);
        else
            g.setColor(Color.red);
        g.fillRect(x,y,20,20);

        if(isClicked)
        {
            g.setColor(Color.black);
            g.drawOval(x-(rangeAttack/2-10),y-(rangeAttack/2-10),rangeAttack,rangeAttack);
            g.drawOval(mx-(rangeMovement/2-10),my-(rangeMovement/2-10),rangeMovement,rangeMovement);
        }
    }

    public void showDanger(Graphics2D g)
    {
        if(highlight){
            g.setColor(Color.red);
            g.fillOval(x-((rangeAttack+rangeMovement)/2-10),y-((rangeAttack+rangeMovement)/2-10),rangeAttack+rangeMovement,rangeAttack+rangeMovement);
            g.setColor(Color.black);
            g.drawOval(x-((rangeAttack+rangeMovement)/2-10),y-((rangeAttack+rangeMovement)/2-10),rangeAttack+rangeMovement,rangeAttack+rangeMovement);

        }
        else if(toggle && !isDead)
        {
            Color c=new Color(1f,0f,0f,.3f );
            g.setColor(c);
            g.fillOval(x-((rangeAttack+rangeMovement)/2-10),y-((rangeAttack+rangeMovement)/2-10),rangeAttack+rangeMovement,rangeAttack+rangeMovement);
            g.setColor(Color.black);
            g.drawOval(x-((rangeAttack+rangeMovement)/2-10),y-((rangeAttack+rangeMovement)/2-10),rangeAttack+rangeMovement,rangeAttack+rangeMovement);
        }
    }
}
