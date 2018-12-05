import javax.swing.*;
import java.awt.*;

public class SpiccyFlags {
    private int blueBoon, redBoon;
    private boolean turnOrder;

    public SpiccyFlags()
    {
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
        JFrame f = new JFrame("Sorting Room");
        SpiccyFlags c = new SpiccyFlags();
        f.setSize(1020,640);
        f.setVisible(true);
        f.add(f);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true)
        {

            Thread.sleep(10);
        }
    }
}
