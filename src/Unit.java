import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;


public abstract class Unit{
    //Blue team is the true team!
    private int health,maxHealth, rangeAttack, rangeMovement, attackValue,x, y, mx, my;
    private boolean isUpgraded, side, isClicked, canAct, isDead, toggle, highlight, display;
    BufferedImage red, redUsed, redUp, redUpUsed, blue, blueUsed, blueUp, blueUpUsed;

    public Unit(int h, int ra, int rm, int a, int x, int y, boolean upgraded, boolean side)
    {
        health=h;
        maxHealth=h;
        rangeAttack=ra;
        rangeMovement=rm;
        attackValue=a;
        this.x=x;
        this.y=y;
        mx=x;
        my=y;
        isUpgraded=upgraded;
        this.side=side;
        canAct=side;
        isDead=false;

        blue=null;
        blueUp=null;
        blueUpUsed=null;
        blueUsed=null;

        red=null;
        redUp=null;
        redUpUsed=null;
        redUsed=null;
    }



    public int getX(){return x;}
    public int getY(){return y;}
    public int getMX() {return mx;}
    public int getMY() {return my;}
    public int getRM(){return rangeMovement;}
    public int getRA(){return rangeAttack;}
    public int getAV(){return attackValue;}
    public int getH(){return health;}
    public int getMH() {return maxHealth; }
    public boolean getAct(){return canAct;}
    public boolean getIsDead(){return isDead;}
    public boolean isClicked(){return isClicked;}
    public boolean isUpgraded(){return isUpgraded;}

    public void setH(int h){health = h;}
    public void setAV(int av){attackValue=av;}
    public void setRM(int rm){rangeMovement=rm;}
    public void setMH(int mh){maxHealth=mh;}
    public void setX(int X){x=X;}
    public void setY(int Y){y=Y;}
    public void setCanAct(boolean act){canAct=act;}
    public void setDead(boolean dead){isDead=dead;}


    public void mouseEntered(int X, int Y){
        if(X > x && X < x+20 && Y > y && Y < y+20 )
            display=true;
        else
            display=false;
    }

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

    public void upgrade(int X, int Y){
        if (X > x && X < x+20 && Y > y && Y < y+20)
            isUpgraded=true;
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

    public void ability(int X, int Y, Unit other) {}

    public boolean withinRange(Unit other){
        return Math.sqrt((Math.pow((other.x - x - 10), 2)) + Math.pow((other.y - y - 10), 2)) < other.rangeAttack / 2 && !other.isDead;
    }

    public void combatDealing(Unit other){
        if (withinRange(other)){
            other.setH(other.getH() - attackValue);
            if (other.getH() < 0)
                other.isDead = true;
        }
    }

    public void combatTaking(Unit other){
        if (withinRange(other)) {
            health = health - other.attackValue;
            if (health < 0)
                isDead = true;
        }
    }

    public boolean armoredCheck(Unit other){
        if (withinRange(other) && other instanceof Armor){return true;}
        else {return false;}
    }
    public void paint(Graphics2D g) {
        g.setColor(Color.black);
        g.drawRect(x-1,y-11,21,5);
        g.setColor(Color.red);
        g.fillRect(x,y-10,20,4);
        g.setColor(Color.green);
        double percentHealth=(1.0 *health)/maxHealth;
        g.fillRect(x,y-10,(int)(20*percentHealth),4);

        if (side) {
            if (isUpgraded) {
                if (canAct)
                    g.drawImage(blueUp, x, y,20,20, null);
                else
                    g.drawImage(blueUpUsed, x, y,20, 20, null);
            }
            else {
                if (canAct)
                    g.drawImage(blue, x, y,20,20, null);
                else
                    g.drawImage(blueUsed, x, y,20,20, null);
            }
        }

        if (!side) {
            if (isUpgraded) {
                if (canAct||SpiccyFlags.State== SpiccyFlags.STATE.Menu||SpiccyFlags.State==SpiccyFlags.STATE.StartUp)
                    g.drawImage(redUp, x, y,20,20, null);
                else
                    g.drawImage(redUpUsed, x, y,20,20, null);
            }
            else {
                if (canAct||SpiccyFlags.State== SpiccyFlags.STATE.Menu||SpiccyFlags.State==SpiccyFlags.STATE.StartUp)
                    g.drawImage(red, x, y,20,20, null);
                else
                    g.drawImage(redUsed, x, y,20,20, null);
            }
        }
        if (isClicked) {
            g.setColor(Color.black);
            g.drawOval(x - (rangeAttack / 2 - 10), y - (rangeAttack / 2 - 10), rangeAttack, rangeAttack);
            g.drawOval(mx - (rangeMovement / 2 - 10), my - (rangeMovement / 2 - 10), rangeMovement, rangeMovement);
        }
    }

    public void unitDisplay(Graphics2D g){

        g.setColor(Color.white);
        if(display) {
            if (!side) {
                g.drawString(health+"/"+maxHealth,570,15);
                g.drawString(" "+ attackValue,580,35);
                g.drawString(" "+ rangeMovement,580,55);
                if (isUpgraded)
                    g.drawImage(redUp, 665, 5, 90, 90, null);
                else
                    g.drawImage(red, 665, 5, 90, 90, null);
            }
            else {
                g.drawString(health+"/"+maxHealth,385,15);
                g.drawString(" "+ attackValue,395,35);
                g.drawString(" "+ rangeMovement,395,55);
                if (isUpgraded)
                    g.drawImage(blueUp, 265, 5, 90, 90, null);
                else
                    g.drawImage(blue, 265, 5, 90, 90, null);
            }
        }
    }

    public void showDanger(Graphics2D g)
    {
        if (display) {
            g.setColor(Color.black);
            g.drawOval(x - (rangeAttack / 2 - 10), y - (rangeAttack / 2 - 10), rangeAttack, rangeAttack);
            g.drawOval(mx - (rangeMovement / 2 - 10), my - (rangeMovement / 2 - 10), rangeMovement, rangeMovement);
        }
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
