import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class EndAuction extends JFrame{

    public EndAuction(String playerName, int offer, String footballerName){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();

        int width = 500, height = 300;
        setBounds((int)(screenWidth - width) / 2, (int)(screenHeight - height) / 2, width, height);
        setResizable(false);
        setTitle("Asta vinta da " + playerName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Carica un'immagine
        ImageIcon footballerIcon = new ImageIcon("software\\resources\\images\\coin.jpg");
        // Ridimensiona l'immagine
        Image img = footballerIcon.getImage();
        Image newImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Imposta le dimensioni desiderate
        footballerIcon.setImage(newImg);

        String s =  "<html><font color='yellow'>" + playerName + " </font>acquisisce le prestazioni sportive di <font color='yellow'>"
                + footballerName + "</font> per <font color='yellow'>" + offer + " </font>crediti</html>";

        JLabel winLabel = new JLabel(s,footballerIcon, JLabel.LEFT);
        winLabel.setBounds(10, 10, width - 10, height - 10);
        winLabel.setFont(UtilityClass.caricaFont(20));
        winLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winLabel.setVerticalAlignment(SwingConstants.CENTER);
        winLabel.setOpaque(true); // Per rendere il colore di sfondo visibile
        winLabel.setBackground(Color.LIGHT_GRAY); // Imposta il colore di sfondo azzurro
        winLabel.setForeground(Color.BLUE); // Imposta il colore del testo a bianco
        winLabel.setVisible(true);

        winLabel.setIconTextGap(15); // Imposta lo spazio tra l'icona e il testo
        add(winLabel);


        setVisible(true);

    }

    public static void main(String[] args) {
        EndAuction endAuction = new EndAuction("Lazio merda", 50, "Francesco Totti");
    }
    
}
