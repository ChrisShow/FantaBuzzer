import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class EndAuction extends JFrame{

    public EndAuction(String playerName, int offer, String footballerName){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();

        int width = 400, height = 600;
        setBounds((int)(screenWidth - width) / 2, (int)(screenHeight - height) / 2, width, height);
        setResizable(false);
        setTitle("Asta vinta da " + playerName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String s = playerName + " acquisisce le prestazioni sportive di " + footballerName + " per " + offer + " crediti";
        JLabel winLabel = new JLabel(s);
        winLabel.setBounds(10, 10, width - 10, height - 10);
        winLabel.setFont(UtilityClass.caricaFont(30));
        winLabel.setVisible(true);
        add(winLabel);


        setVisible(true);

    }

    public static void main(String[] args) {
        EndAuction endAuction = new EndAuction("Lazio merda", 50, "Francesco Totti");
    }
    
}
