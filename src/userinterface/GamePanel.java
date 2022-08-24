package userinterface;

import DB_Connect.DBContext;
import Model.ScoreModel;
import game2048.Game2048;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class GamePanel extends JPanel {

    int mainGameX;
    int mainGameY;
    int blockSize;

    private List<ScoreModel> listScore;
    private int minTop;

    JLabel txtScore;
    JButton btnNewGame;
    JTable table;
    JScrollPane sp;
    Game2048 g2048;
    Graphics2D g2;
    JPanel jPanel;
    JButton btnBack;

    Image img;

    ActionListener inputENTER;

    public GamePanel() {
        g2048 = new Game2048();
        createTableScore();

        mainGameX = 93;
        mainGameY = 99;
        blockSize = 85;

        btnNewGame = new JButton();
        btnBack = new JButton();

        this.add(btnNewGame);
        this.add(btnBack);

        img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/data/Frame1_failed.png"));
        try {
            if (new DBContext().getConnection() != null) {
                img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/data/Frame1.png"));
            }
        } catch (Exception ex) {
            Logger.getLogger(WelcomePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void initRank(Graphics g) {
        Font myFont = new Font("Courier New", 1, 40);
        FontMetrics fontMetrics = g2.getFontMetrics(myFont);
        try {
            if (new DBContext().getConnection() != null) {

//        HIGHSCORE_TABLE
                sp.setBounds(538, 255, 360, 151);
                sp.setBorder(BorderFactory.createEmptyBorder());
                sp.setColumnHeader(null);

                table.setEnabled(false);
                table.setShowGrid(false);
                table.setBackground(new Color(253, 236, 236));
                if (table.getColumnModel().getColumnCount() > 0) {
                    table.getColumnModel().getColumn(0).setMinWidth(180);
                }
                table.setRowHeight(30);
                table.setForeground(new Color(101, 63, 55));
                table.setFont(new Font("Segoe Print", Font.BOLD + Font.ITALIC, 17));
            }
        } catch (Exception ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g2 = (Graphics2D) g;
        this.setBounds(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
//        Background-----------------------------
//        Image img = Toolkit.getDefaultToolkit().getImage("D:\\JAVA\\Web_Application\\Game2048\\src\\data\\Frame1.png");

        g.drawImage(img, 0, 0, this);
//        ---------------------------------------

        btnNewGame.setBounds(319, 36, 128, 37);
        btnNewGame.setOpaque(false);
        btnNewGame.setContentAreaFilled(false);
        btnNewGame.setFocusPainted(false);
        btnNewGame.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNewGame.addMouseListener(btnClick);

        btnBack.setBounds(25, 36, 73, 34);
        btnBack.setOpaque(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setFocusPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Font myFont = new Font("Courier New", 1, 40);
        FontMetrics fontMetrics = g2.getFontMetrics(myFont);

//        g2048.printMap();
        FontMetrics scoreMetrics = g2.getFontMetrics(new Font("VNI-Free", 1, 35));
        int scoreX = 648 + (178 - scoreMetrics.stringWidth(g2048.getScore() + "")) / 2;
        int scoreY = 124 + ((33 - scoreMetrics.getHeight()) / 2) + scoreMetrics.getAscent();

        g2.setFont(new Font("VNI-Free", 1, 35));
        g2.setColor(new Color(101, 63, 55));
        g2.drawString(g2048.getScore() + "", 648, scoreY - 3);

        initRank(g);
        for (int i = 0; i < g2048.getSIZE(); i++) {
            for (int j = 0; j < g2048.getSIZE(); j++) {
                if (g2048.getMap()[i][j] > 0) {
                    colorMap(g2048.getMap()[i][j], g2);
                    g2.fillRoundRect(this.mainGameX + j * 90, this.mainGameY + i * 90, this.blockSize, this.blockSize, 10, 10);
                    g2.setColor(Color.white);
                    g2.setFont(myFont);
                    int strWidth = (int) g2.getFontMetrics().getStringBounds(g2048.getMap()[i][j] + "", g).getWidth();
                    int strHeight = (int) g2.getFontMetrics().getStringBounds(g2048.getMap()[i][j] + "", g).getHeight();
                    g2.drawString(g2048.getMap()[i][j] + "", (this.mainGameX + j * 90) + (90 - strWidth - 5) / 2, (this.mainGameY + i * 90) + (90 - strHeight + 10));
                } else {
                    colorMap(g2048.getMap()[i][j], g2);
                    g2.fillRoundRect(this.mainGameX + j * 90, this.mainGameY + i * 90, this.blockSize, this.blockSize, 10, 10);
                }
            }
        }

        if (g2048.isGameOver()) {
            g2.setColor(Color.red);
            g2.setFont(myFont);
            int gOverX = 360 + (93 - fontMetrics.stringWidth("Game Over")) / 2;
            int gOverY = 360 + (99 - fontMetrics.stringWidth("Game Over")) / 2;
            g2.drawString("Game Over", gOverX, gOverY);
        } else {
            keyListener();
        }
    }

    public void createTableScore() {
        try {
            if (new DBContext().getConnection() != null) {
                table = new JTable(0, 2);

                try {
                    listScore = new DBContext().listScore(10);
                    minTop = new DBContext().minTop(10);
                    System.out.println(minTop + " -----------------------------------");
                    DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                    for (int i = 0; i < (listScore.size() <= 5 ? listScore.size() : 5); i++) {
                        tableModel.addRow(new Object[]{"  " + listScore.get(i).getUsername(), "    " + listScore.get(i).getScore()});
                        System.out.println(listScore.get(i).getUsername() + " - " + listScore.get(i).getScore());
                    }
                    System.out.println("---------------------------");
                    tableModel.setColumnIdentifiers(new Object[]{"Name", "Score"});
                    setRowColor(table);
                    sp = new JScrollPane(table);
                    sp.setViewportView(table);
                    this.add(sp);
                } catch (SQLException ex) {
                    Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setRowColor(JTable table) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? new Color(253, 236, 236) : new Color(229, 210, 206));
                return c;
            }
        });
    }

    public void saveScore() {
        try {
            if (new DBContext().getConnection() != null) {
                ScoreModel scoreModel;
                if (g2048.isGameOver()) {
                    int result = JOptionPane.showConfirmDialog(null,
                            "Điểm của bạn đang nằm trong Top 10, Bạn có muốn lưu điểm",
                            "Thông báo",
                            JOptionPane.YES_NO_OPTION);
                    if (result == 0) {
                        String userName = JOptionPane.showInputDialog("Nhập họ tên");
                        scoreModel = new ScoreModel(userName, g2048.getScore());
                        try {
                            new DBContext().insert(scoreModel);
                            createTableScore();
                            repaint();
                        } catch (Exception ex) {
                            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void colorMap(int c, Graphics2D g2) {

        switch (c) {
            case 0:
                g2.setColor(new Color(233, 213, 213));
                break;
            case 2:
                g2.setColor(new Color(225, 189, 165));
                break;
            case 4:
                g2.setColor(new Color(216, 167, 208));
                break;
            case 8:
                g2.setColor(new Color(172, 155, 233));
                break;
            case 16:
                g2.setColor(new Color(218, 159, 156));
                break;
            case 32:
                g2.setColor(new Color(126, 179, 196));
                break;
            case 64:
                g2.setColor(new Color(65, 204, 231));
                break;
            case 128:
                g2.setColor(new Color(29, 159, 215));
                break;
            case 256:
                g2.setColor(new Color(91, 87, 250));
                break;
            case 512:
                g2.setColor(new Color(167, 81, 247));
                break;
            case 1024:
                g2.setColor(new Color(255, 118, 167));
                break;
            case 2048:
                g2.setColor(new Color(213, 80, 51));
                break;
            case 4096:
                g2.setColor(new Color(248, 166, 70));
                break;
            default:
                g2.setColor(new Color(63, 63, 63));
                break;
        }
    }

    MouseListener btnClick = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            keyPressed("ENTER");
        }

        @Override
        public void mousePressed(MouseEvent e) {
            btnNewGame.setContentAreaFilled(true);
            btnNewGame.setBorderPainted(false);
            btnNewGame.setIcon(new ImageIcon(getClass().getResource("/data/btnNewGame.png")));
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            btnNewGame.setContentAreaFilled(true);
            btnNewGame.setBorderPainted(false);
            btnNewGame.setIcon(new ImageIcon(getClass().getResource("/data/btnNewGame.png")));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            btnNewGame.setContentAreaFilled(false);
        }

    };

    public void keyListener() {
        ActionListener inputUP = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                keyPressed(KeyStroke.getKeyStroke("UP").toString().substring(8));
            }
        };

        ActionListener inputDOWN = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                keyPressed(KeyStroke.getKeyStroke("DOWN").toString().substring(8));
            }
        };

        ActionListener inputLEFT = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                keyPressed(KeyStroke.getKeyStroke("LEFT").toString().substring(8));
            }
        };

        ActionListener inputRIGHT = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                keyPressed(KeyStroke.getKeyStroke("RIGHT").toString().substring(8));
            }
        };

        inputENTER = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                keyPressed(KeyStroke.getKeyStroke("ENTER").toString().substring(8));
            }
        };

        this.registerKeyboardAction(inputUP, KeyStroke.getKeyStroke("UP"), GamePanel.WHEN_IN_FOCUSED_WINDOW);
        this.registerKeyboardAction(inputUP, KeyStroke.getKeyStroke("W"), GamePanel.WHEN_IN_FOCUSED_WINDOW);

        this.registerKeyboardAction(inputDOWN, KeyStroke.getKeyStroke("DOWN"), GamePanel.WHEN_IN_FOCUSED_WINDOW);
        this.registerKeyboardAction(inputDOWN, KeyStroke.getKeyStroke("S"), GamePanel.WHEN_IN_FOCUSED_WINDOW);

        this.registerKeyboardAction(inputLEFT, KeyStroke.getKeyStroke("LEFT"), GamePanel.WHEN_IN_FOCUSED_WINDOW);
        this.registerKeyboardAction(inputLEFT, KeyStroke.getKeyStroke("A"), GamePanel.WHEN_IN_FOCUSED_WINDOW);

        this.registerKeyboardAction(inputRIGHT, KeyStroke.getKeyStroke("RIGHT"), GamePanel.WHEN_IN_FOCUSED_WINDOW);
        this.registerKeyboardAction(inputRIGHT, KeyStroke.getKeyStroke("D"), GamePanel.WHEN_IN_FOCUSED_WINDOW);

        this.registerKeyboardAction(inputENTER, KeyStroke.getKeyStroke("ENTER"), GamePanel.WHEN_IN_FOCUSED_WINDOW);
    }

    public void keyPressed(String key) {
        int[][] prevMap = g2048.initPrevMap();
        //audio
        try {
            // Open an audio input stream.
            URL url = this.getClass().getResource("/data/eff_01.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        if (key.equals("UP") || key.equals("W")) {
            System.out.println("UP");
            g2048.keyUp();
        } else if (key.equals("DOWN") || key.equals("S")) {
            System.out.println("DOWN");
            g2048.keyDown();
        } else if (key.equals("LEFT") || key.equals("A")) {
            System.out.println("LEFT");
            g2048.keyLeft();
        } else if (key.equals("RIGHT") || key.equals("D")) {
            System.out.println("RIGHT");
            g2048.keyRight();
        } else if (key.equals("ENTER")) {
            System.out.println("ENTER");
            if (g2048.isGameOver() && (g2048.getScore() >= minTop || listScore.size() < 10)) {
                saveScore();
            }
            g2048.initMap();
        } else {
            System.out.println("Pressed...");
        }

        if (!key.equals("ENTER")) {
            if (!g2048.isArrSame(prevMap)) {
                g2048.createNum();
            }
        }
        repaint();
    }

}
