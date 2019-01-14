import javax.swing.*;
import javax.sound.sampled.*;
import java.io.*;
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
        blueBoon = 2;
        redBoon = 2;
        blue[3] = new Mage(100, 100, 500, 100, 100, 100, false, true);
        blue[1] = new Infantry(100, 100, 500, 50, 100, 150, false, true);
        blue[2] = new Armor(100, 100, 500, 100, 100, 200, false, true);
        blue[0] = new Cavalier(100, 100, 500, 100, 100, 250, false, true);
        blue[4] = new Ranger(100, 100, 500, 100, 100, 300, false, true);
        red[3] = new Mage(100, 100, 500, 100,  500, 100, false, false);
        red[1] = new Infantry(100, 100, 500, 50, 500, 150, false, false);
        red[2] = new Armor(100, 100, 500, 100, 500, 200, false, false);
        red[0] = new Cavalier(100, 100, 500, 100, 500, 250, false, false);
        red[4] = new Ranger(100, 100, 500, 100,  500, 300, false, false);

        addMouseListener(new MouseListener(){
            public void mouseExited(MouseEvent e){}
            public void mouseClicked(MouseEvent e){}
            public void mouseEntered(MouseEvent e){}
            public void mousePressed(MouseEvent e){
                for (int i = 0; i<5; i++){
                    blue[i].mousePressed(e.getX(), e.getY());
                    red[i].mousePressed(e.getX(), e.getY());
                }


            }
            public void mouseReleased(MouseEvent e) {
                for (int i = 3; i<=4; i++){
                    for(int t = 0; t<5; t++)
                    {
                        blue[i].ability(e.getX(),e.getY(),red[t]);
                        red[i].ability(e.getX(),e.getY(),blue[t]);
                    }
                }
                    for (int t = 0; t < 5; t++) {
                        for (int i = 0; i < 5; i++) {
                            if (Turn) {
                                blue[i].mouseReleased(e.getX(), e.getY());
                                blue[i].combatDealing(red[t]);
                                blue[i].combatTaking(red[t]);
                            } else {
                                red[i].mouseReleased(e.getX(), e.getY());
                                red[i].combatDealing(blue[t]);
                                red[i].combatTaking(blue[t]);
                            }
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
        addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }
            @Override
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
        try {
            File titleTheme = new File("res\\Trap Knight.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(titleTheme);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (LineUnavailableException e){
            e.printStackTrace();
        }

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
        for(int t=0; t<5; t++) {
            if (Turn)
                red[t].showDanger(g2d);
            else
                blue[t].showDanger(g2d);
        }

        for(int t=0; t<5; t++)
        {
            if(!blue[t].getIsDead()) {
                blue[t].paint(g2d);
            }
            if(!red[t].getIsDead()) {
                red[t].paint(g2d);
            }

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
