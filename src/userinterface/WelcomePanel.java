package userinterface;

import DB_Connect.DBContext;
import Model.ScoreModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class WelcomePanel extends JPanel {

    Graphics2D g2;
    JButton btnPlay;
    JButton btnRank;
    JButton btnBack;
    JPanel rankPanel;
    Image img;
    Image imgRank;

    JTable table;
    JScrollPane sp;
    int count = 0;

    private List<ScoreModel> listScore;

    public WelcomePanel() {
        this.setBounds(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
        

        rankPanel = new JPanel();
        rankPanel.setVisible(false);
        createTableScore();
        btnBack = new JButton();
        rankPanel.add(btnBack);
        btnBack.addMouseListener(btnActBack);
        btnBack.setIcon(new ImageIcon(getClass().getResource("/data/btnBack.png")));
        btnBack.setBorder(BorderFactory.createEmptyBorder());
        this.add(rankPanel);

        btnPlay = new JButton();
        btnRank = new JButton();

        this.add(btnPlay);
        this.add(btnRank);
        
        btnRank.setVisible(false);
        img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/data/WelcomeFrm_failed.png"));
        
        try {
            if (new DBContext().getConnection() != null) {
                btnRank.setVisible(true);
                img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/data/WelcomeFrm.png"));
            }
        } catch (Exception ex) {
            Logger.getLogger(WelcomePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        imgRank = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/data/Rank.png"));

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g2 = (Graphics2D) g;

        g2.drawImage(imgRank, 289, 0, rankPanel);
        this.setBounds(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
//        Background-----------------------------
        this.setBackground(new Color(229, 210, 206));
        if (img != null) {
            g2.drawImage(img, 0, 0, this);
        }
//        btnRank.setVisible(false);

        try {
            if (new DBContext().getConnection() != null) {
//                btnRank.setVisible(true);
//        ---------------------------------------
                rankPanel.setBounds(289, 0, 400, 554);
                sp.setVisible(false);

//        HIGHSCORE_TABLE
                if (rankPanel.isVisible()) {
                    sp.setVisible(true);
                    sp.setBounds(20, 135, 360, 301);
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

                    btnBack.setBounds(25, 25, 73, 34);

                    btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(WelcomePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
//        -------------------------------------------------
        btnPlay.setBounds(344, 211, 218, 63);
        btnPlay.setOpaque(false);
        btnPlay.setContentAreaFilled(false);
        btnPlay.setFocusPainted(false);
        btnPlay.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnRank.setBounds(344, 310, 218, 63);
        btnRank.setOpaque(false);
        btnRank.setContentAreaFilled(false);
        btnRank.setFocusPainted(false);
        btnRank.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRank.addMouseListener(btnActRank);

    }

    public void createRankPanel() {
        rankPanel.setVisible(true);
        img = null;

        btnPlay.setVisible(false);
        btnRank.setVisible(false);

    }

    public void createTableScore() {
        try {
            table = new JTable(0, 2);
            if (new DBContext().getConnection() != null) {
                try {
                    listScore = new DBContext().listScore(10);
                    DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                    for (int a = 0; a < (listScore.size() <= 10 ? listScore.size() : 10); a++) {
                        tableModel.addRow(new Object[]{"  " + listScore.get(a).getUsername(), "    " + listScore.get(a).getScore()});
                        System.out.println(listScore.get(a).getUsername() + " - " + listScore.get(a).getScore());
                    }
                    tableModel.setColumnIdentifiers(new Object[]{"Name", "Score"});
                    setRowColor(table);
                    sp = new JScrollPane(table);
                    sp.setViewportView(table);
                    rankPanel.add(sp);
                } catch (SQLException ex) {
                    Logger.getLogger(WelcomePanel.class.getName()).log(Level.SEVERE, null, ex);

                }
            } else {
                System.out.println("LOIII");
            }
        } catch (Exception ex) {
            Logger.getLogger(WelcomePanel.class.getName()).log(Level.SEVERE, null, ex);
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

    MouseListener btnActRank = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            createRankPanel();
//            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            btnRank.setContentAreaFilled(true);
            btnRank.setBorderPainted(false);
            btnRank.setIcon(new ImageIcon(getClass().getResource("/data/btnRank.png")));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            btnRank.setContentAreaFilled(false);
        }

    };

    MouseListener btnActBack = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            rankPanel.setVisible(false);
            img = Toolkit.getDefaultToolkit().getImage("D:\\JAVA\\Web_Application\\Game2048\\src\\data\\WelcomeFrm.png");
            btnPlay.setVisible(true);
            btnRank.setVisible(true);
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            btnBack.setIcon(new ImageIcon(getClass().getResource("/data/btnBack.png")));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            btnBack.setIcon(new ImageIcon(getClass().getResource("/data/btnBackOrg.png")));
        }

    };
}
