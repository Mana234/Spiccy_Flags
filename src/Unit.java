import java.awt.*;
import java.awt.event.*;

public abstract class Unit implements MouseListener {
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
    public void mousePressed(MouseEvent e) {
        if (e.getX() > x && e.getX() < x+20 && e.getY() > y && e.getY() < y+20){
            isClicked = true;
        }
    }
    public void mouseReleased(MouseEvent e){
        if (isClicked = true){
            x = e.getX();
            y = e.getY();
            isClicked = false;
        }
    }
    public void paint(Graphics2D g) {


    }
}
