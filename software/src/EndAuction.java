import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class EndAuction extends JFrame{

    public EndAuction(String playerName, int offer, String footballerName) {
        // Ottieni le dimensioni dello schermo
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();

        int width = 500, height = 400;

        // Creare un pannello principale che conterrà entrambi i componenti
        JPanel mainPanel = new JPanel(null);
        mainPanel.setBounds(0, 0, width, height);

        // Creare un pannello per lo sfondo animato
        JPanel animatedPanel = new JPanel();
        animatedPanel.setBounds(0, 0, width, height);

        // Caricare l'immagine GIF come sfondo animato
        ImageIcon backgroundIcon = new ImageIcon("software\\resources\\images\\animated_background.gif");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        animatedPanel.add(backgroundLabel);
        mainPanel.add(animatedPanel);
        

        // Caricare un'immagine
        ImageIcon footballerIcon = new ImageIcon("software\\resources\\images\\coin.png");
        Image img = footballerIcon.getImage();
        Image newImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        footballerIcon.setImage(newImg);

        String s =  "<html><font color='yellow'>" + playerName + " </font>acquisisce le prestazioni sportive di <br> <font color='yellow'>"
                + footballerName + "</font> per <font color='yellow'>" + offer + " </font>crediti</html>";

        JLabel winLabel = new JLabel(s, footballerIcon, JLabel.RIGHT);
        winLabel.setBounds(10, 10, width - 10, height - 10);
        winLabel.setFont(UtilityClass.caricaFont(20));
        winLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winLabel.setVerticalAlignment(SwingConstants.CENTER);
        winLabel.setOpaque(true);
        winLabel.setForeground(Color.WHITE);
        winLabel.setIconTextGap(15);
        mainPanel.add(winLabel);

        // Impostare la finestra principale
        setBounds((int)(screenWidth - width) / 2, (int)(screenHeight - height) / 2, width, height);
        setResizable(false);
        setTitle("Asta vinta da " + playerName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Aggiungere il pannello principale al frame
        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        EndAuction endAuction = new EndAuction("ChrisShowsha", 50, "Francesco Totti");
    }
    
}
