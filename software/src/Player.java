import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

public class Player extends JPanel {

    private String name;
    private int credits, id;
    private JLabel nameLabel;
    private JLabel amount;
    private JLabel creditLabel;
    private JLabel amountTextLabel;
    private JLabel creditTextLabel;

    public Player(String name, int credits, int id, int width, int height){

        this.id = id;
        this.name = name;
        this.credits = credits;

        setBackground(new Color(50, 41, 47));

        int distance = 10;
        int nameLabelX = distance, nameLabelY = distance;
        int nameLabelWidth = width - (distance * 2), nameLabelHeight = ((height - (distance * 2)) / 4);

        nameLabel = new JLabel(name);
        nameLabel.setBounds(nameLabelX, nameLabelY, nameLabelWidth, nameLabelHeight);
        nameLabel.setOpaque(true);
        nameLabel.setBackground(new Color(193, 120, 23));
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setFont(UtilityClass.caricaFont(38));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setVerticalAlignment(SwingConstants.CENTER);
        nameLabel.setVisible(true);
        add(nameLabel);

        int amountY = nameLabelY + nameLabelHeight + distance;
        int amountWidth = (width - (distance * 3)) / 2, amountHeight = nameLabelHeight * 2;

        amount = new JLabel("");
        amount.setBounds(nameLabelX, amountY, amountWidth, amountHeight);
        amount.setOpaque(false);
        amount.setBackground(new Color(62, 86, 34));
        amount.setForeground(Color.WHITE);
        amount.setFont(UtilityClass.caricaFont(50));
        amount.setHorizontalAlignment(SwingConstants.CENTER);
        amount.setVerticalAlignment(SwingConstants.CENTER);
        amount.setVisible(true);
        add(amount);

        int amountTextLabelY = nameLabelY + nameLabelHeight + amountHeight;
        int amountTextLabelHeight = height - distance - amountTextLabelY;

        amountTextLabel = new JLabel("OFFERTA");
        amountTextLabel.setBounds(nameLabelX, amountTextLabelY, amountWidth, amountTextLabelHeight);
        amountTextLabel.setOpaque(true);
        amountTextLabel.setBackground(new Color(62, 86, 34));
        amountTextLabel.setForeground(Color.WHITE);
        amountTextLabel.setFont(UtilityClass.caricaFont(20));
        amountTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        amountTextLabel.setVerticalAlignment(SwingConstants.CENTER);
        amountTextLabel.setVisible(true);
        add(amountTextLabel);

        int creditLabelX = (distance * 2) + amountWidth;

        creditLabel = new JLabel("" + this.credits);
        creditLabel.setBounds(creditLabelX, amountY, amountWidth, amountHeight);
        creditLabel.setOpaque(true);
        creditLabel.setBackground(new Color(230, 225, 197));
        creditLabel.setForeground(Color.BLACK);
        creditLabel.setFont(UtilityClass.caricaFont(50));
        creditLabel.setHorizontalAlignment(SwingConstants.CENTER);
        creditLabel.setVerticalAlignment(SwingConstants.CENTER);
        creditLabel.setVisible(true);
        add(creditLabel);

        creditTextLabel = new JLabel("CREDITI");
        creditTextLabel.setBounds(creditLabelX, amountTextLabelY, amountWidth, amountTextLabelHeight);
        creditTextLabel.setOpaque(true);
        creditTextLabel.setBackground(new Color(230, 225, 197));
        creditTextLabel.setForeground(Color.BLACK);
        creditTextLabel.setFont(UtilityClass.caricaFont(20));
        creditTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        creditTextLabel.setVerticalAlignment(SwingConstants.CENTER);
        creditTextLabel.setVisible(true);
        add(creditTextLabel);
    }

    public void newOffer(int offer) {
        this.amount.setText("" + offer);
        this.amount.setOpaque(true);
    }

    public void cancelOffer() {
        this.amount.setText("");
        this.amount.setOpaque(false);
    }

    @Override
    public boolean equals(Object o){
        if(o == null) return false;
        if(!(o instanceof Player)) return false;
        return this.id == ((Player) o).getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(JLabel nameLabel) {
        this.nameLabel = nameLabel;
    }

    public JLabel getAmount() {
        return amount;
    }

    public void setAmount(JLabel amount) {
        this.amount = amount;
    }

    public JLabel getCreditLabel() {
        return creditLabel;
    }

    public void setCreditLabel(JLabel creditLabel) {
        this.creditLabel = creditLabel;
    }

    public JLabel getAmountTextLabel() {
        return amountTextLabel;
    }

    public void setAmountTextLabel(JLabel amountTextLabel) {
        this.amountTextLabel = amountTextLabel;
    }

    public JLabel getCreditTextLabel() {
        return creditTextLabel;
    }

    public void setCreditTextLabel(JLabel creditTextLabel) {
        this.creditTextLabel = creditTextLabel;
    }
    
    
}
