package userinterface;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GameFrame extends JFrame {

    public static final int SCREEN_WIDTH = 1000; // trừ lại còn 982 (18)
    public static final int SCREEN_HEIGHT = 600; // trừ thanh title còn 553 (47)
    GamePanel gamePanel;
    WelcomePanel welcomePanel;

    public GameFrame() {
        Toolkit toolkit = this.getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds((dimension.width - SCREEN_WIDTH) / 2, (dimension.height - SCREEN_HEIGHT) / 2, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setTitle(" 2048");

//        ICON--------------------------
        Image image = toolkit.getImage("D:\\JAVA\\Web_Application\\Game2048\\src\\data\\icon-game.png");
        this.setIconImage(image);
//        ------------------------------

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        Panel-------------------------
        welcomePanel = new WelcomePanel();
        welcomePanel.setVisible(true);

        gamePanel = new GamePanel();
        gamePanel.setVisible(false);

        this.add(welcomePanel);
        this.add(gamePanel);

//        ------------------------------
        welcomePanel.btnPlay.addMouseListener(btnActPlay);
        gamePanel.btnBack.addMouseListener(btnActBack);
        
        
        
        //audio
        try {
         // Open an audio input stream.
         URL url = this.getClass().getResource("/data/background_audio.wav");
         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
         // Get a sound clip resource.
         Clip clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioIn);
         clip.loop(999);
         clip.start();
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
    }

    public void setVisiblePanel() {
        gamePanel.setVisible(true);
        welcomePanel.setVisible(false);
    }
    
    public void setVisibleWelc() {
        gamePanel.setVisible(false);
        welcomePanel.setVisible(true);
        welcomePanel.createTableScore();
    }

    MouseListener btnActPlay = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            setVisiblePanel();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            welcomePanel.btnPlay.setContentAreaFilled(true);
            welcomePanel.btnPlay.setBorderPainted(false);
            welcomePanel.btnPlay.setIcon(new ImageIcon(getClass().getResource("/data/btnPlayGame.png")));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            welcomePanel.btnPlay.setContentAreaFilled(false);
        }

    };
    
    MouseListener btnActBack = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            setVisibleWelc();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            gamePanel.btnBack.setContentAreaFilled(true);
            gamePanel.btnBack.setBorderPainted(false);
            gamePanel.btnBack.setIcon(new ImageIcon(getClass().getResource("/data/btnBackGp.png")));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            gamePanel.btnBack.setContentAreaFilled(false);
        }
    };

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public WelcomePanel getWelcomePanel() {
        return welcomePanel;
    }

    public void setWelcomePanel(WelcomePanel welcomePanel) {
        this.welcomePanel = welcomePanel;
    }

    public static void main(String args[]) {
        GameFrame gameFrame = new GameFrame();
        gameFrame.setVisible(true);
    }
}
