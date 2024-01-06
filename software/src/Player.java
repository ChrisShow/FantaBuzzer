import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

public class Player extends JPanel {

    private String name;
    private int credits;
    private JLabel nameLabel;
    private JLabel amount;
    private JLabel creditLabel;

    public Player(String name, int credits, int width, int height){

        this.name = name;
        this.credits = credits;

        int distance = 10;

        nameLabel = new JLabel(name);
        nameLabel.setBounds(distance, distance, width - (distance * 2), ((height - (distance * 2)) / 4));
        nameLabel.setOpaque(true);
        nameLabel.setBackground(new Color(193, 120, 23));
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setFont(UtilityClass.caricaFont(38));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setVerticalAlignment(SwingConstants.CENTER);
        nameLabel.setVisible(true);
        add(nameLabel);

        amount = new JLabel("555");
        amount.setBounds(distance, ((distance * 2) + (height - (distance * 2)) / 4), (width - (distance * 3)) / 2, 3 * ((height - (distance * 2)) / 4) - distance);
        amount.setOpaque(true);
        amount.setBackground(new Color(62, 86, 34));
        amount.setForeground(Color.WHITE);
        amount.setFont(UtilityClass.caricaFont(50));
        amount.setHorizontalAlignment(SwingConstants.CENTER);
        amount.setVerticalAlignment(SwingConstants.CENTER);
        amount.setVisible(true);
        add(amount);
    }
    
}
