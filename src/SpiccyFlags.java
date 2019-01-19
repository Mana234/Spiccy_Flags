import javax.imageio.ImageIO;
import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.*;

public class SpiccyFlags extends JPanel{
    private boolean Turn=true;
    private Unit[] blue = new Unit[5];
    private Unit[] red = new Unit[5];
    private Menu Menu= new Menu();
    BufferedImage map,nextTurn=null;

    public enum STATE{Menu, Game, StartUp, BlueWin, RedWin}
    public static STATE State = STATE.Menu;

    public SpiccyFlags() {

        blue[3] = new Mage(75, 100, 400, 15, 100, 150, false, true);
        blue[1] = new Infantry(100, 100, 500, 20, 100, 220, false, true);
        blue[0] = new Armor(150, 100, 375, 20, 100, 290, false, true);
        blue[2] = new Cavalier(100, 100, 550, 20, 100, 360, false, true);
        blue[4] = new Ranger(75, 100, 400, 15, 100, 430, false, true);
        red[3] = new Mage(75, 100, 400, 15,  880, 150, false, false);
        red[1] = new Infantry(100, 100, 500, 20, 880, 220, false, false);
        red[0] = new Armor(150, 100, 375, 20, 880, 290, false, false);
        red[2] = new Cavalier(100, 100, 550, 20, 880, 360, false, false);
        red[4] = new Ranger(75, 100, 400, 15,  880, 430, false, false);


        try {
            map= ImageIO.read(new File("res\\Map.png"));
            nextTurn= ImageIO.read(new File("res\\NextTurnButton.png"));
        } catch (IOException e) {
        }


        addMouseListener(new MouseListener(){
            public void mouseExited(MouseEvent e){}
            public void mouseClicked(MouseEvent e){

            }
            public void mouseEntered(MouseEvent e){

            }

            public void mousePressed(MouseEvent e) {
                if (State == STATE.Game) {
                    for (int i = 0; i < 5; i++) {
                        blue[i].mousePressed(e.getX(), e.getY());
                        red[i].mousePressed(e.getX(), e.getY());
                    }
                    if(e.getX() > 430 && e.getX() < 590 && e.getY() > 60 && e.getY() < 100) {
                        if(Turn)
                        {
                            for(int i=0; i<5; i++) {
                                red[i].setCanAct(true);
                                blue[i].setCanAct(false);
                            }
                            Turn=false;
                        }
                        else
                        {
                            for(int i= 0; i<5; i++) {
                                blue[i].setCanAct(true);
                                red[i].setCanAct(false);
                            }
                            Turn=true;
                        }
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
                        int blueBoon=0, redBoon=0;
                        for(int i=0;i<5;i++)
                        {
                            if(blue[i].isUpgraded()) {
                                blueBoon++;
                            }
                            if(red[i].isUpgraded()) {
                                redBoon++;
                            }
                        }
                        if(blueBoon!=2)
                            blue[t].upgrade(e.getX(),e.getY());
                        if(redBoon!=2)
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
        addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                for (int i = 0; i<blue.length; i++){blue[i].mouseDragged(e.getX(), e.getY());}
                for (int i = 0; i<red.length; i++){red[i].mouseDragged(e.getX(), e.getY());}
                for (int i = 0; i < 5; i++) {
                    blue[i].mouseEntered(e.getX(), e.getY());
                    red[i].mouseEntered(e.getX(), e.getY());
                }
            }

            public void mouseMoved(MouseEvent e) {
                for (int i = 0; i < 5; i++) {
                blue[i].mouseEntered(e.getX(), e.getY());
                red[i].mouseEntered(e.getX(), e.getY());
                }
            }
        });
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

        if(State==STATE.StartUp)
        {
            Color c= new Color(102,51,0);
            Font fnt=new Font("Serif",Font.BOLD, 30);
            g.setFont(fnt);
            g.setColor(c);
            g.fillRect(235,235,220,60);
            g.fillRect(575,235,210,60);
            g.setColor(Color.cyan);
            g.drawString("# of Blue Boons:",240,260);
            g.setColor(Color.red);
            g.drawString("# of Red Boons:",580,260);
            for(int t = 0; t<5; t++)
            {
                int blueBoon=2, redBoon=2;
                for(int i=0;i<5;i++)
                {
                    if(blue[i].isUpgraded()) {
                        blueBoon--;
                    }
                    if(red[i].isUpgraded()) {
                        redBoon--;
                    }
                }

                g.setColor(Color.cyan);
                g.drawString(Integer.toString(blueBoon),240,290);
                g.setColor(Color.red);
                g.drawString(Integer.toString(redBoon),580,290);
            }
        }

        if(State==STATE.Game ||State==STATE.StartUp){
            Color c= new Color(102,51,0);
            g.setColor(c);
            g.fillRect(260,0,500,100);
            if(State==STATE.StartUp)
                g.setColor(Color.gray);
            else if(!Turn)
                g.setColor(Color.RED);
            else
                g.setColor(Color.BLUE);
            g.fillRect(485,0,50,50);
            Font fnt=new Font("Serif",Font.BOLD, 15);
            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("HP:",545,15);
            g.drawString("HP:",360,15);
            g.drawString("ATK:",545,35);
            g.drawString("ATK:",360,35);
            g.drawString("SPD:",545,55);
            g.drawString("SPD:",360,55);
            g.drawImage(nextTurn,430,60,160,40,null);
            for (int t = 0; t < 5; t++) {
                blue[t].unitDisplay(g2d);
                red[t].unitDisplay(g2d);
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
