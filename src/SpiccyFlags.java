import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SpiccyFlags extends JPanel{
    private int blueBoon, redBoon;
    private boolean turnOrder;
    private Unit[] blue = new Unit[5];
    private Unit[] red = new Unit[5];

    public SpiccyFlags() {
        blueBoon = 2;
        redBoon = 2;
        turnOrder = true;
        for(int t=0; t<5; t++)
        {
            blue[t]=new Unit(100, 100, 100, 100, 100, 100, 100+(50*t),false,true);
            red[t]=new Unit(100, 100, 100, 100, 100,400,100+(50*t), false, false);
        }

        addMouseListener(new MouseListener(){
            public void mouseExited(MouseEvent e){}
            public void mouseClicked(MouseEvent e){}
            public void mouseEntered(MouseEvent e){}
            public void mousePressed(MouseEvent e){for (int i = 0; i<blue.length; i++){blue[i].mousePressed(e.getX(), e.getY());}
                for (int i = 0; i<red.length; i++){red[i].mousePressed(e.getX(), e.getY());}
                System.out.println("i am pressed");
            }
            public void mouseReleased(MouseEvent e){}
        });
        //(100, 100, 100, 100, 100, 100, 100, false, true);
        //(100, 100, 100, 100, 100, 500, 100, false,
    }

    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.green);
        g2d.fillRect(0,0,1020,640);
        for(int t=0; t<5; t++)
        {
            blue[t].paint(g2d);
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

            f.repaint();
            Thread.sleep(10);
        }
    }
}
