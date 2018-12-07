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
        blue[0] = new Unit(100, 100, 100, 100, 100, 100, 100, false, true);
        blue[1] = new Unit(100, 100, 100, 100, 100, 100, 200, false, true);
        blue[2] = new Unit(100, 100, 100, 100, 100, 100, 300, false, true);
        blue[3] = new Unit(100, 100, 100, 100, 100, 100, 400, false, true);
        blue[4] = new Unit(100, 100, 100, 100, 100, 100, 450, false, true);
        red[0] = new Unit(100, 100, 100, 100, 100, 500, 100, false, true);
        red[1] = new Unit(100, 100, 100, 100, 100, 500, 200, false, true);
        red[2] = new Unit(100, 100, 100, 100, 100, 500, 300, false, true);
        red[3] = new Unit(100, 100, 100, 100, 100, 500, 400, false, true);
        red[4] = new Unit(100, 100, 100, 100, 100, 500, 450, false, true);

        addMouseListener(new MouseListener(){
            public void mouseExited(MouseEvent e){}
            public void mouseClicked(MouseEvent e){}
            public void mouseEntered(MouseEvent e){}
            public void mousePressed(MouseEvent e){
                for (int i = 0; i<blue.length; i++){blue[i].mousePressed(e.getX(), e.getY());}
                for (int i = 0; i<red.length; i++){red[i].mousePressed(e.getX(), e.getY());}
            }
            public void mouseReleased(MouseEvent e){}
        });
    }

    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.green);
        g2d.fillRect(0,0,1020,640);
        g2d.setColor(Color.blue);
        for (int i = 0; i < blue.length; i++) {
            int x = blue[i].getX();
            int y = blue[i].getY();
            int ra = blue[i].getRA();
            g2d.fillRect(blue[i].getX(), blue[i].getY(), 20, 20);
            if (blue[i].isClicked()){
                g2d.drawOval(x-ra+10, y-ra+10, 2*ra, 2*ra);
            }
        }
        g2d.setColor(Color.red);
        for (int i = 0; i < red.length; i++) {
            g2d.fillRect(red[i].getX(), red[i].getY(), 20, 20);
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
