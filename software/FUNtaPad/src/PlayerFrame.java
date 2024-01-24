import javax.swing.*;
import java.awt.*;

public class PlayerFrame extends JFrame {
    private Player player;
    private JLabel backgroundLabel, nameLabel, creditsLabel, playersTextLabel;
    private JPanel[] playersPanel;
    private JLabel[] rolePlayersLabel;
    private Color[] playerRoleColors;
    private JLabel[] footballerLabels;

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
        int creditsLabelWidth = 250, creditsLabelHeight = 50;
        creditsLabel = new JLabel("Crediti rimanenti: " + player.getCredits());
        creditsLabel.setFont(UtilityClass.caricaFont(25));
        creditsLabel.setBounds((width - creditsLabelWidth) / 2, (distance * 3) + nameLabelHeight, creditsLabelWidth, creditsLabelHeight);
        creditsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        creditsLabel.setVerticalAlignment(SwingConstants.CENTER);
        creditsLabel.setOpaque(true);
        creditsLabel.setBackground(UtilityClass.CUSTOM_WHITE);
        creditsLabel.setForeground(Color.black);
        creditsLabel.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        creditsLabel.setVisible(true);
        add(creditsLabel);

        // Impostazione pannelli giocatori
        int playersPanelWidth = (width - (distance * 6)) / 4;
        int playersPanelHeight = height - (distance * 10 + nameLabelHeight + creditsLabelHeight);
        int playersPanelY = distance * 5 + nameLabelHeight + creditsLabelHeight;
        playersPanel = new JPanel[5];
        for (int i=0; i<playersPanel.length; i++){
            playersPanel[i] = new JPanel(null);
            playersPanel[i].setBorder(BorderFactory.createLineBorder(Color.black, 3));
            playersPanel[i].setBounds((distance * (i+1)) + (playersPanelWidth * i), playersPanelY, playersPanelWidth, playersPanelHeight);
            playersPanel[i].setBackground(new Color(50, 41, 47, 128));
            playersPanel[i].setVisible(true);
            add(playersPanel[i]);
        }

        //Impostazione JLabel titoli ruoli giocatori
        rolePlayersLabel = new JLabel[4];
        rolePlayersLabel[0] = new JLabel("PORTIERI");
        rolePlayersLabel[1] = new JLabel("DIFENSORI");
        rolePlayersLabel[2] = new JLabel("CENTROCAMPISTI");
        rolePlayersLabel[3] = new JLabel("ATTACCANTI");
        playerRoleColors = new Color[4];
        playerRoleColors[0] = UtilityClass.CUSTOM_ORANGE;
        playerRoleColors[1] = UtilityClass.CUSTOM_GREEN;
        playerRoleColors[2] = UtilityClass.CUSTOM_LIGHT_BLUE;
        playerRoleColors[3] = UtilityClass.CUSTOM_RED;
        int rolePlayerLabelWidth = playersPanelWidth - (distance * 2), rolePlayerLabelHeight = (playersPanelHeight - (distance * 9)) / 8;
        for (int i = 0; i < rolePlayersLabel.length; i++) {
            rolePlayersLabel[i].setBounds(distance, distance, rolePlayerLabelWidth, rolePlayerLabelHeight);
            rolePlayersLabel[i].setFont(UtilityClass.caricaFont(20));
            rolePlayersLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
            rolePlayersLabel[i].setVerticalAlignment(SwingConstants.CENTER);
            rolePlayersLabel[i].setBorder(BorderFactory.createLineBorder(Color.black, 3));
            rolePlayersLabel[i].setOpaque(true);
            rolePlayersLabel[i].setBackground(playerRoleColors[i]);
            rolePlayersLabel[i].setForeground(Color.black);
            rolePlayersLabel[i].setVisible(true);
            playersPanel[i].add(rolePlayersLabel[i]);
        }

        //Impostazione JLabel con nomi giocatori acquistati
        footballerLabels = new JLabel[25];
        int cGK = 0, cDef = 0, cMid = 0, cAtt = 0;
        int c = 0;
        for(Footballer footballer : this.player.getFootballerList()){
            footballerLabels[c] = new JLabel(footballer.getSurname() + " - " + player.getOfferList().get(c));
            int offset;
            if(footballer.isGoalkeeper()){
                offset = 0;
                cGK++;
            }
            else if(footballer.isDefender()){
                offset = 1;
                cDef++;
            }
            else if(footballer.isMidfielder()){
                offset = 2;
                cMid++;
            }
            else {
                offset = 3;
                cAtt++;
            }
            //TODO
            footballerLabels[c].setBounds(distance, distance, rolePlayerLabelWidth, rolePlayerLabelHeight);
            footballerLabels[i].setFont(UtilityClass.caricaFont(20));
            footballerLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            footballerLabels[i].setVerticalAlignment(SwingConstants.CENTER);
            footballerLabels[i].setBorder(BorderFactory.createLineBorder(Color.black, 3));
            footballerLabels[i].setOpaque(true);
            footballerLabels[i].setBackground(playerRoleColors[i]);
            footballerLabels[i].setForeground(Color.black);
            footballerLabels[i].setVisible(true);
            playersPanel[i].add(footballerLabels[c]);
        }

        // Imposta l'immagine di sfondo utilizzando un JLabel
        backgroundLabel = new JLabel(new ImageIcon(new ImageIcon(
                "software\\FUNtaPad\\resources\\images\\playerFrameBg.jpg").getImage().
                getScaledInstance(width, height,Image.SCALE_SMOOTH)));
        backgroundLabel.setBounds(0, 0, width, height);
        backgroundLabel.setVisible(true);
        add(backgroundLabel);

        setVisible(true);
    }
}

