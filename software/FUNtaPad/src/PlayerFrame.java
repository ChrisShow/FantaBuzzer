import javax.swing.*;
import java.awt.*;

public class PlayerFrame extends JFrame {
    private Player player;
    private JLabel backgroundLabel, nameLabel, creditsTextLabel, playersTextLabel;
    private JPanel goalkeepersPanel, defendersPanel, midfieldersPanel, attackersPanel;

    public PlayerFrame(Player player) {

        this.player = player;

        int width = 900, height = 600, distance = 10;

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimensioneSchermo = toolkit.getScreenSize();
        int screenWidth = dimensioneSchermo.width;
        int screenHeight = dimensioneSchermo.height;

        // Impostazioni JFrame
        setTitle("Informazioni Player");
        setSize(800, 800);
        setBounds((screenWidth - width) / 2, (screenHeight - height) / 2, width, height);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        //Impostazione Label del nome squadra
        int nameLabelWidth = 275, nameLabelHeight = 75;
        nameLabel = new JLabel(player.getName());
        nameLabel.setFont(UtilityClass.caricaFont(40));
        nameLabel.setBounds((width - nameLabelWidth) / 2, (distance * 2), nameLabelWidth, nameLabelHeight);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setVerticalAlignment(SwingConstants.CENTER);
        nameLabel.setOpaque(true);
        nameLabel.setBackground(UtilityClass.CUSTOM_ORANGE);
        nameLabel.setForeground(Color.black);
        nameLabel.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        nameLabel.setVisible(true);
        add(nameLabel);

        //Impostazione label della scritta "Crediti rimanenti:" e crediti rimanenti
        int creditsTextLabelWidth = 250, creditsTextLabelHeight = 50;
        creditsTextLabel = new JLabel("Crediti rimanenti: " + player.getCredits());
        creditsTextLabel.setFont(UtilityClass.caricaFont(25));
        creditsTextLabel.setBounds((width - creditsTextLabelWidth) / 2, (distance * 3) + nameLabelHeight, creditsTextLabelWidth, creditsTextLabelHeight);
        creditsTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        creditsTextLabel.setVerticalAlignment(SwingConstants.CENTER);
        creditsTextLabel.setOpaque(true);
        creditsTextLabel.setBackground(UtilityClass.CUSTOM_WHITE);
        creditsTextLabel.setForeground(Color.black);
        creditsTextLabel.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        creditsTextLabel.setVisible(true);
        add(creditsTextLabel);

        //Impostazione

        // Imposta l'immagine di sfondo utilizzando un JLabel
        backgroundLabel = new JLabel(new ImageIcon(new ImageIcon(
                "software\\FUNtaPad\\resources\\images\\playerFrameBg.jpg").getImage().
                getScaledInstance(width, height,Image.SCALE_SMOOTH)));
        backgroundLabel.setBounds(0, 0, width, height);
        backgroundLabel.setVisible(true);
        add(backgroundLabel);

        setVisible(true);
    }

    public static void main(String[] args) {
        Player samplePlayer = new Player("ChrisShow07", 500, 1, 1, 1);
        new PlayerFrame(samplePlayer);
    }
}

