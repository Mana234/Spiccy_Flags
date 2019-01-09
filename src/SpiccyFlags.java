import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SpiccyFlags extends JPanel{
    private int blueBoon, redBoon;
    private boolean Turn=true;
    private Unit[] blue = new Unit[5];
    private Unit[] red = new Unit[5];

    public SpiccyFlags() {
        //number of boons each side has
        blueBoon = 2;
        redBoon = 2;
        //create Blue Units
        blue[3] = new Mage(100, 100, 500, 100, 100, 100, false, true);
        blue[1] = new Infantry(100, 100, 500, 50, 100, 150, false, true);
        blue[2] = new Armour(100, 100, 500, 100, 100, 200, false, true);
        blue[0] = new Cavalier(100, 100, 500, 100, 100, 250, false, true);
        blue[4] = new Ranger(100, 100, 500, 100, 100, 300, false, true);
        //create Red units
        red[3] = new Mage(100, 100, 500, 100,  500, 100, false, false);
        red[1] = new Infantry(100, 100, 500, 50, 500, 150, false, false);
        red[2] = new Armour(100, 100, 500, 100, 500, 200, false, false);
        red[0] = new Cavalier(100, 100, 500, 100, 500, 250, false, false);
        red[4] = new Ranger(100, 100, 500, 100,  500, 300, false, false);

        addMouseListener(new MouseListener(){
            public void mouseExited(MouseEvent e){}
            public void mouseClicked(MouseEvent e){}
            public void mouseEntered(MouseEvent e){}
            //check if unit is clicked
            public void mousePressed(MouseEvent e){
                for (int i = 0; i<5; i++){
                    blue[i].mousePressed(e.getX(), e.getY());
                    red[i].mousePressed(e.getX(), e.getY());
                }
            }
            //runs what happens when a unit is released
            public void mouseReleased(MouseEvent e) {
                //runs activated abilities
                for (int i = 3; i<=4; i++){
                    for(int t = 0; t<5; t++)
                    {
                        blue[i].ability(e.getX(),e.getY(),red[t]);
                        red[i].ability(e.getX(),e.getY(),blue[t]);
                    }
                }
                //moves units and runs combat
                for (int t = 0; t < 5; t++) {
                    for (int i = 0; i < 5; i++) {
                        blue[i].mouseReleased(e.getX(), e.getY());
                        red[i].mouseReleased(e.getX(), e.getY());

                        blue[i].combat(red[t]);
                        red[i].combat(blue[t]);
                    }
                }
                //moves dead units offscreen
                for (int t = 0; t < 5; t++) {
                    if (blue[t].getIsDead())
                        blue[t].setX(-100);
                    if (red[t].getIsDead())
                        red[t].setX(-100);
                }

            }
        });

        addMouseMotionListener(new MouseMotionListener() {
            //makes units move with mouse when clicked
            public void mouseDragged(MouseEvent e) {
                for (int i = 0; i<blue.length; i++){blue[i].mouseDragged(e.getX(), e.getY());}
                for (int i = 0; i<red.length; i++){red[i].mouseDragged(e.getX(), e.getY());}
            }

            public void mouseMoved(MouseEvent e) {

            }
        });

        addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }
            @Override
            //used for any toggling(danger area etc.)
            public void keyReleased(KeyEvent e) {
                for (int i = 0; i<5; i++){
                    blue[i].keyReleased(e);
                    red[i].keyReleased(e);
                }
            }
            public void keyPressed(KeyEvent e) {
            }
        });
        setFocusable(true);
    }

    //changes turns when all units acts
    public void changeTurn()
    {
        //counter for units that have acted
        int unitsActed=0;

        for(int t=0;t<5;t++)
        {
            //makes sure dead units are considered to have already acted
            if(blue[t].getIsDead()) {
                blue[t].setCanAct(false);
            }
            if(red[t].getIsDead()) {
                red[t].setCanAct(false);
            }

            //counts the number of units that have acted
            if(!blue[t].getAct() ||!red[t].getAct()) {
                unitsActed++;
            }

            //if all units have acted, changes turns
            if(unitsActed==10){
                //if it is blue's turn
                if(Turn)
                {
                    //lets red units act
                    for(int i=0; i<5; i++) {
                        red[i].setCanAct(true);
                    }
                    //it is now red side's turn
                    Turn=false;
                }
                //if it is red's turn
                else
                {
                    //lets blue units act
                    for(int i= 0; i<5; i++) {
                        blue[i].setCanAct(true);
                    }
                    //it is now blue side's turn
                    Turn=true;
                }
            }
        }
    }

    public void paint(Graphics g)
    {
        //required shtuff
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //paint background
        g2d.setColor(Color.green);
        g2d.fillRect(0,0,1020,640);

        for(int t=0; t<5; t++) {
            //paints danger area if toggled
            if (Turn)
                red[t].showDanger(g2d);
            else
                blue[t].showDanger(g2d);

            //paints Units
                blue[t].paint(g2d);
                red[t].paint(g2d);
        }
    }

    //what can i say it's the main class
    public static void main(String[] args) throws InterruptedException
    {
        JFrame f = new JFrame("spiccyFlags");
        SpiccyFlags c = new SpiccyFlags();
        f.add(c);
        f.setSize(1020,640);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        while (true)
        {
            c.changeTurn();
            c.repaint();
            Thread.sleep(10);
        }
    }
}
