import java.awt.*;

public abstract class Unit {
    private int health, rangeAttack, rangeMovement, attackValue, unitType, x, y;
    private boolean isUpgraded, side;

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

    public void paint(Graphics2D g) {

    }
}
