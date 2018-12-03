import javax.swing.*;

public class SpiccyFlags {
    private int blueBoon, redBoon;
    private boolean turnOrder;

    public static void main(String[] args) throws InterruptedException
    {
        JFrame f = new JFrame("Sorting Room");
        SpiccyFlags c = new SpiccyFlags();
        f.setSize(1020,640);
        f.setVisible(true);
        f.add(c);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true)
        {

            Thread.sleep(10);
        }
    }
}
