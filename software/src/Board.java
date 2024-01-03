import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Board extends JFrame {



    public Board(String stringTeams, int teams, int credits) {
        
        setLayout(null);
        setTitle("FantaBuzzer Launcher");
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        int boardWidth = this.getWidth();
        int boardHeight = this.getHeight();

        int distance = 10;

        setVisible(true);
        
    }

    public static void main(String[] args) {
        Board board = new Board("s1-s2-null-null-s3-s4-s5-s6", 6, 500);
    }

    
}
