import javax.imageio.ImageIO;
import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SpiccyFlags extends JPanel{
    private boolean Turn=true;
    private Unit[] blue = new Unit[5];
    private Unit[] red = new Unit[5];
    private Menu Menu= new Menu();
    BufferedImage map=null;

    public enum STATE{Menu, Game, StartUp, BlueWin, RedWin}
    public static STATE State = STATE.Menu;

    public SpiccyFlags() {

        blue[3] = new Mage(100, 100, 500, 100, 100, 100, false, true);
        blue[1] = new Infantry(100, 100, 500, 50, 100, 150, false, true);
        blue[0] = new Armor(100, 100, 500, 100, 100, 200, false, true);
        blue[2] = new Cavalier(100, 100, 500, 100, 100, 250, false, true);
        blue[4] = new Ranger(100, 100, 500, 100, 100, 300, false, true);
        red[3] = new Mage(100, 100, 500, 100,  900, 100, false, false);
        red[1] = new Infantry(100, 100, 500, 50, 900, 150, false, false);
        red[0] = new Armor(100, 100, 500, 100, 900, 200, false, false);
        red[2] = new Cavalier(100, 100, 500, 100, 900, 250, false, false);
        red[4] = new Ranger(100, 100, 500, 100,  900, 300, false, false);


        try {
            map= ImageIO.read(new File("res\\Map.png"));
        } catch (IOException e) {
        }

        addMouseListener(new MouseListener(){
            public void mouseExited(MouseEvent e){}
            public void mouseClicked(MouseEvent e){}
            public void mouseEntered(MouseEvent e){

            }

            public void mousePressed(MouseEvent e) {
                if (State == STATE.Game) {
                    for (int i = 0; i < 5; i++) {
                        blue[i].mousePressed(e.getX(), e.getY());
                        red[i].mousePressed(e.getX(), e.getY());
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                if(State==STATE.Menu)
                {
                    Menu.mouseReleased(e.getX(),e.getY());
                }

                if(State==STATE.StartUp)
                {
                    for(int t = 0; t<5; t++)
                    {
                        blue[t].upgrade(e.getX(),e.getY());
                        red[t].upgrade(e.getX(),e.getY());
                    }
                }

                int armor = -1;
                for (int i = 3; i<=4; i++){
                    for(int t = 0; t<5; t++)
                    {
                        blue[i].ability(e.getX(),e.getY(),red[t]);
                        red[i].ability(e.getX(),e.getY(),blue[t]);
                    }
                }
                for (int t = 0; t < 5; t++) {
                    if (Turn) {
                        for (int i = 0; i < 5; i++) {
                            blue[i].mouseReleased(e.getX(), e.getY());
                        }
                        for (int i = 0; i < 5; i++){
                            if (blue[t].armoredCheck(red[i])){
                                armor = i;
                            }
                        }
                        if (armor < 0) {
                            for (int i = 0; i < 5; i++)
                                blue[t].combatDealing(red[i]);
                        } else {
                            int d = 0;
                            for (int i = 0; i < 5; i++) {
                                if (blue[t].withinRange(red[i]))
                                    d += blue[t].getAV();
                            }
                            red[armor].setH(red[armor].getH() - d);
                            if (red[armor].getH()<=0){red[armor].setDead(true);}
                        }
                        for (int i = 0; i < 5; i++)
                            blue[t].combatTaking(red[i]);
                        // ignore this, just a benchmark
                    } else {
                            for (int i = 0; i < 5; i++) {
                                red[i].mouseReleased(e.getX(), e.getY());
                            }
                            for (int i = 0; i < 5; i++){
                                if (red[t].armoredCheck(blue[i])){
                                    armor = i;
                                }
                            }
                            if (armor < 0) {
                                for (int i = 0; i < 5; i++)
                                    red[t].combatDealing(blue[i]);
                            } else {
                                int d = 0;
                                for (int i = 0; i < 5; i++) {
                                    if (red[t].withinRange(blue[i]))
                                        d += blue[t].getAV();
                                }
                                blue[armor].setH(blue[armor].getH() - d);
                                if (blue[armor].getH()<=0){blue[armor].setDead(true);}
                            }
                            for (int i = 0; i < 5; i++)
                                red[t].combatTaking(blue[i]);
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
                for (int i = 0; i < 5; i++) {
                    blue[i].mouseEntered(e.getX(), e.getY());
                    red[i].mouseEntered(e.getX(), e.getY());
                }
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
        if(State==STATE.StartUp){
            int blueBoon=0, redBoon=0;
            for(int t=0;t<5;t++)
            {
                if(blue[t].isUpgraded()) {
                    blueBoon++;
                }
                if(red[t].isUpgraded()) {
                    redBoon++;
                }
                if(redBoon==2 && blueBoon==2){
                    State=STATE.Game;
                }
            }
        }

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

        int blueDead=0;
        int redDead=0;
        for(int t=0;t<5;t++)
        {
            if(blue[t].getIsDead()) {
                blueDead++;
            }
            if(red[t].getIsDead()) {
                redDead++;
            }
            if(blueDead==5)
                State=STATE.RedWin;
            if(redDead==5)
                State=STATE.BlueWin;

        }
    }

    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(map, 0, 0, null);
        if(State!=STATE.Game ||State!=STATE.StartUp)
            Menu.paint(g2d);

        if(State==STATE.Game) {
            for (int t = 0; t < 5; t++) {
                if (Turn)
                    red[t].showDanger(g2d);
                else
                    blue[t].showDanger(g2d);
            }
        }

        if(State==STATE.Game ||State==STATE.StartUp){
            for (int t = 0; t < 5; t++) {
                red[t].unitDisplay(g2d);
                blue[t].unitDisplay(g2d);
            }
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
