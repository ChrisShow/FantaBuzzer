import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class EndAuction extends JFrame{

    public EndAuction(Player p, int offer, Footballer f){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();

        int width = 400, height = 600;
        setBounds((int)(screenWidth - width) / 2, (int)(screenHeight - height) / 2, width, height);
        setResizable(false);
        setTitle("Asta vinta da " + p.getName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String s = p.getName() + " acquisisce le prestazioni sportive di " + f.getName() + " per " + offer + " crediti";
        JLabel winLabel = new JLabel(s);
        winLabel.setBounds(10, 10, width - 10, height - 10);
        winLabel.setFont(UtilityClass.caricaFont(30));
        winLabel.setVisible(true);
        add(winLabel);


        setVisible(true);

    }
    
}
