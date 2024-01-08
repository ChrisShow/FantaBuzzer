import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Board extends JFrame {

    private LinkedList<Player> players;
    private int offer;
    private boolean firstStartTimer;
    private JButton startAuction;
    private FantaTimer fantaTimer;

    public Board(String stringTeams, int teams, int credits) {

        this.offer = 0;
        this.firstStartTimer = true;

        /* GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight(); */

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();

        setLayout(null);
        setTitle("FUNtaPad");
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(new BoardKeyboardListener());
        setFocusable(true);
        requestFocus();
        //setBounds(0, 0, screenWidth, screenHeight);

        int distance = 10;
        int playerWidth = (int) ((screenWidth - (distance * ((teams / 2) + 1))) / (teams / 2));
        int playerHeight = (int) ((screenHeight - distance) / 4);

        players = new LinkedList<>();
        String[] strings = stringTeams.split("-");
        int j = 0;
        for (int i = 0; i < strings.length; i++) {
            if(!strings[i].equals("null")){
                Player p = new Player(strings[i], credits, playerWidth, playerHeight);
                int currentPlayerX = (distance * ((j % (teams / 2)) + 1)) + (playerWidth * (j % (teams / 2)));
                int currentPlayerY = (playerHeight + distance) + ((distance + playerHeight) * ((int) j / (teams / 2)));
                p.setBounds(currentPlayerX, currentPlayerY, playerWidth, playerHeight);
                p.setLayout(null);
                p.setBorder(BorderFactory.createLineBorder(Color.black, 3));
                add(p);
                p.setVisible(true);
                players.addLast(p);
                j++;
            }
            else
                players.addLast(null);
        }

        int startAuctionWidth = 200, startAuctionHeight = 100;
        startAuction = new JButton("Inizia Asta");
        startAuction.setFont(UtilityClass.caricaFont(25));
        startAuction.addActionListener(new BoardListener());
        startAuction.setFocusable(false);
        startAuction.setBounds((int)(screenWidth - startAuctionWidth) / 2, (distance * 5), startAuctionWidth, startAuctionHeight);
        startAuction.setVisible(true);
        add(startAuction);

        int fantaTimerWidth = 200, fantaTimerHeight = 100;
        fantaTimer = new FantaTimer(fantaTimerWidth, fantaTimerHeight, this);
        fantaTimer.setBounds((int)(screenWidth - fantaTimerWidth) / 2, (850), fantaTimerWidth, fantaTimerHeight);
        //fantaTimer.setLayout(null);
        fantaTimer.setVisible(true);
        add(fantaTimer);

        setVisible(true);
        
    }

    public void auctionTerminated(){
        //TODO
    }

    private void newOffer(int num){
        if(!firstStartTimer){
            int i = 0;
            for (Player player : players) {
                if(player != null){
                    if(i == num){
                        this.fantaTimer.resetTimer();
                        this.offer += 2;
                        player.newOffer(this.offer);
                    }
                    else
                        player.cancelOffer();
                    player.repaint();
                }
                i++;
            }
            repaint();
        }
    }

    public static void main(String[] args) {
        Board board = new Board("s1-s2-null-null-s3-s4-s5-s6", 6, 500);
    }

    private class BoardKeyboardListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            char keyChar = e.getKeyChar();
            if (keyChar >= '0' && keyChar <= '8') {
                int num = Character.getNumericValue(keyChar);
                System.out.println(num);
                newOffer(num);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}
        
    }

    private class BoardListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == startAuction){
                if(firstStartTimer) {
                    fantaTimer.run();
                    firstStartTimer = false;
                }
            }
        }
        
    }


}
