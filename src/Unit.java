import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;


public abstract class Unit{
    //Blue team is the true team!(blue.side=true, red.side=false)
    private int health,maxHealth, rangeAttack, rangeMovement, attackValue,x, y, mx, my;
    private boolean isUpgraded, side, isClicked, canAct, isDead, toggle, highlight,isGuarded;
    private BufferedImage red, redUsed, redUp, redUpUsed, blue, blueUsed, blueUp, blueUpUsed;

    public Unit(int h, int ra, int rm, int a, int x, int y, boolean upgraded, boolean side)
    {
        //set stats
        health=h;
        maxHealth=h;
        rangeAttack=ra;
        rangeMovement=rm;
        attackValue=a;
        //set location
        this.x=x;
        this.y=y;
        mx=x;
        my=y;
        //set booleans
        isUpgraded=upgraded;
        this.side=side;
        canAct=side;
        isDead=false;
        isGuarded=false;

        //create location to store blue images
        blue=null;
        blueUp=null;
        blueUpUsed=null;
        blueUsed=null;
        //create location to store red images
        red=null;
        redUp=null;
        redUpUsed=null;
        redUsed=null;
    }

    //location getters
    public int getX(){return x;}
    public int getY(){return y;}
    public int getMX() {return mx;}
    public int getMY() {return my;}
    //stats getters
    public int getRM(){return rangeMovement;}
    public int getRA(){return rangeAttack;}
    public int getAV(){return attackValue;}
    public int getH(){return health;}
    public int getMH() {return maxHealth;}
    //boolean getters
    public boolean getAct(){return canAct;}
    public boolean getIsDead(){return isDead;}
    public boolean isClicked(){return isClicked;}
    //setters
    public void setH(int h){health = h;}
    public void setX(int X){x=X;}
    public void setY(int Y){y=Y;}
    public void setCanAct(boolean act){canAct=act;}
    public void setDead(boolean dead){isDead=dead;}

    public void mousePressed(int X, int Y){
        //checks if turn unit is clicked & stores unit's original position(mx & my)
        if (X > x && X < x+20 && Y > y && Y < y+20 && canAct)
            isClicked = true; mx = x; my = y;

        //toggle off highlight
        if(X > x && X < x+20 && Y > y && Y < y+20 && !canAct&& highlight || canAct)
            highlight=false;

        //toggle on highlight
        else if(X > x && X < x+20 && Y > y && Y < y+20 )
            highlight=true;

    }

    //moves clicked unit along with mouse
    public void mouseDragged(int X, int Y){
        if(isClicked)
        {
            x=X-10;
            y=Y-10;
        }
    }

    public void mouseReleased(int X, int Y) {

        //checks if released in the area it can move
        if (isClicked && !isDead && Math.sqrt((Math.pow((X - mx - 10), 2)) + Math.pow((Y - my - 10), 2)) < rangeMovement / 2) {
            //moves clicked unit to where the mouse is released
            x = X - 10;
            y = Y - 10;
            //unit has acted
            canAct = false;
        }

        //checks if released in an area the unit CAN'T move to
        else if(Math.sqrt((Math.pow((X - mx - 10), 2)) + Math.pow((Y - my - 10), 2)) > rangeMovement / 2){
            //returns to original position
            x=mx;
            y=my;
        }

        //unit is no longer clicked
        isClicked = false;
    }

    public void keyReleased(KeyEvent e) {
        //toggles showDangerArea with spacebar click
        if (e.getKeyCode() == KeyEvent.VK_SPACE&&toggle)
        {
            toggle=false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_SPACE&&!toggle)
        {
            toggle=true;
        }
    }

    //just a generic class for subclass' triggered abilities
    public void ability(int X, int Y, Unit other) {}

    //runs combat
    public void combat(Unit other){
        //checks if opponent is close to unit
        if (Math.sqrt((Math.pow((other.x-x-10), 2)) + Math.pow((other.y - y - 10), 2)) < rangeAttack/2 ) {
            //damages opponent equal to attack value
            other.setH(other.getH() - attackValue);
            //checks if other unit has health, sets unit dead if not
            if (other.getH() < 0)
                other.isDead = true;
        }
        //checks
        if (Math.sqrt((Math.pow((other.x - x - 10), 2)) + Math.pow((other.y - y - 10), 2)) < other.rangeAttack/2 ) {
            health = health - other.attackValue;
            if (health < 0)
                isDead = true;
        }

    }

    public void paint(Graphics2D g) {
        if (side) {
            if (isUpgraded) {
                if (canAct)
                    g.drawImage(blueUp, x, y,20,20, null);
                else
                    g.drawImage(blueUpUsed, x, y,20, 20, null);
            }

            else {
                if (getAct())
                    g.drawImage(blue, x, y,20,20, null);
                else
                    g.drawImage(blueUsed, x, y,20,20, null);
            }
        }

        if (!side) {
            if (isUpgraded) {
                if (canAct)
                    g.drawImage(redUp, x, y,20,20, null);
                else
                    g.drawImage(redUpUsed, x, y,20,20, null);
            }
            else {
                if (getAct())
                    g.drawImage(red, x, y,20,20, null);
                else
                    g.drawImage(redUsed, x, y,20,20, null);
            }
        }

        if (this.isClicked()) {
            g.setColor(Color.black);
            g.drawOval(x - (rangeAttack / 2 - 10), y - (rangeAttack / 2 - 10), rangeAttack, rangeAttack);
            g.drawOval(mx - (rangeMovement / 2 - 10), my - (rangeMovement / 2 - 10), rangeMovement, rangeMovement);
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
