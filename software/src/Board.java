import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

public class Board extends JFrame {

    private LinkedList<Player> players;

    public Board(String stringTeams, int teams, int credits) {

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

        setVisible(true);
        
    }

    public void auctionTerminated(){
        //TODO
    }

    public static void main(String[] args) {
        Board board = new Board("s1-s2-null-null-s3-s4-s5-s6", 6, 500);
    }

    private class BoardKeyboardListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
            char keyChar = e.getKeyChar();
            if (keyChar >= '1' && keyChar <= '8') {
                int numeroPremuto = Character.getNumericValue(keyChar);
                //TODO
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {}
        
    }

    
}
