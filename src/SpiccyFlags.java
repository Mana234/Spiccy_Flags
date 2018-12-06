import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SpiccyFlags extends JPanel{
    private int blueBoon, redBoon;
    private boolean turnOrder;
    private Unit blue = new Unit(100, 100, 100, 100, 100, 100, 100, false, true);
    private Unit red = new Unit(100, 100, 100, 100, 100, 100, 100, false, false);

    public SpiccyFlags() {
        blueBoon = 2;
        redBoon = 2;
        turnOrder = true;
        addMouseListener(new MouseListener(){
            public void mouseExited(MouseEvent e){}
            public void mouseClicked(MouseEvent e){}
            public void mouseEntered(MouseEvent e){}
            public void mousePressed(MouseEvent e){blue.mousePressed(e.getX(), e.getY());}
            public void mouseReleased(MouseEvent e){blue.mouseReleased(e.getX(), e.getY());}
        });

    }

    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.green);
        g2d.fillRect(0,0,1020,640);
        g2d.setColor(Color.blue);
        g2d.fillRect(blue.getX(), blue.getY(), 20, 20);

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
