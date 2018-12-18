import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class SpiccyFlags extends JPanel{
    private int blueBoon, redBoon;
    private boolean Turn=true;
    private Unit[] blue = new Unit[5];
    private Unit[] red = new Unit[5];

    public SpiccyFlags() {
        blueBoon = 2;
        redBoon = 2;
        blue[0] = new Unit(100, 100, 500, 100, 100, 100, 100, false, true);
        blue[1] = new Unit(100, 100, 500, 100, 100, 100, 150, false, true);
        blue[2] = new Unit(100, 100, 500, 100, 100, 100, 200, false, true);
        blue[3] = new Unit(100, 100, 500, 100, 100, 100, 250, false, true);
        blue[4] = new Unit(100, 100, 500, 100, 100, 100, 300, false, true);
        red[0] = new Unit(100, 100, 500, 100, 100, 500, 100, false, false);
        red[1] = new Unit(100, 100, 500, 100, 100, 500, 150, false, false);
        red[2] = new Unit(100, 100, 500, 100, 100, 500, 200, false, false);
        red[3] = new Unit(100, 100, 500, 100, 100, 500, 250, false, false);
        red[4] = new Unit(100, 100, 500, 100, 100, 500, 300, false, false);

        addMouseListener(new MouseListener(){
            public void mouseExited(MouseEvent e){}
            public void mouseClicked(MouseEvent e){}
            public void mouseEntered(MouseEvent e){}
            public void mousePressed(MouseEvent e){
                for (int i = 0; i<blue.length; i++){blue[i].mousePressed(e.getX(), e.getY());}
                for (int i = 0; i<red.length; i++){red[i].mousePressed(e.getX(), e.getY());}
            }
            public void mouseReleased(MouseEvent e) {
                for (int t = 0; t < 5; t++) {
                    for (int i = 0; i < blue.length; i++) {
                        blue[i].mouseReleased(e.getX(), e.getY());
                        blue[i].combat(red[t]);
                    }
                    for (int i = 0; i < red.length; i++) {
                        red[i].mouseReleased(e.getX(), e.getY());
                        red[i].combat(blue[t]);
                    }
                }
                for (int t = 0; t < 5; t++) {
                    if (blue[t].getIsDead())
                        blue[t].setX(-100);
                    if (red[t].getIsDead())
                        red[t].setX(-100);
                }
            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                for (int i = 0; i<blue.length; i++){blue[i].mouseDragged(e.getX(), e.getY());}
                for (int i = 0; i<red.length; i++){red[i].mouseDragged(e.getX(), e.getY());}
            }

            public void mouseMoved(MouseEvent e) {

            }
        });
    }

    public void changeTurn()
    {
        int unitsActed=0;
        for(int t=0;t<5;t++)
        {
            if(blue[t].getIsDead()) {
                blue[t].setCanAct(false);
            }
            if(red[t].getIsDead()) {
                red[t].setCanAct(false);
            }
            if(!blue[t].getAct())
                unitsActed++;
            if(!red[t].getAct())
                unitsActed++;
            if(unitsActed==10){

                if(Turn)
                {
                    for(int i=0; i<5; i++) {
                        red[i].setCanAct(true);
                    }
                    Turn=false;
                }
                else
                {
                    for(int i= 0; i<5; i++) {
                        blue[i].setCanAct(true);
                    }
                    Turn=true;
                }
            }
        }
    }

    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.green);
        g2d.fillRect(0,0,1020,640);
        g2d.setColor(Color.blue);
        for(int t=0; t<blue.length; t++)
        {
            if(!blue[t].getIsDead())
                blue[t].paint(g2d);
        }
        for(int t=0; t<red.length; t++)
        {
            if(!red[t].getIsDead())
                red[t].paint(g2d);
        }
    }

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
