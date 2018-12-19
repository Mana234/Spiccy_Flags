public class infantry extends Unit{
    infantry(int h, int ra, int rm, int a, int x, int y, boolean upgraded, boolean side){
        health=h;
        rangeAttack=ra;
        rangeMovement=rm;
        attackValue=a;
        this.x=x;
        this.y=y;
        isUpgraded=upgraded;
        this.side=side;
        canAct=side;
        isDead=false;
    }
        public void combat(Unit other){
            if (Math.sqrt(Math.pow(other.x-x-10, 2) + Math.pow(other.y-y-10, 2)) < rangeAttack/2){
                damage(other);
            }
        }

    }

