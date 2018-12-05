import javax.swing.*;
import java.awt.*;

public class SpiccyFlags extends JPanel{
    private int blueBoon, redBoon;
    private boolean turnOrder;
    private Unit blue = new Unit(100, 100, 100, 100, 100, 100, 100, false, true);
    private Unit red = new Unit(100, 100, 100, 100, 100, 100, 100, false, false);

    public SpiccyFlags() {
        blueBoon = 2;
        redBoon = 2;
        turnOrder = true;

    }

    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.green);
        g2d.fillRect(0,0,1020,640);

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

            Thread.sleep(10);
        }
    }
}
