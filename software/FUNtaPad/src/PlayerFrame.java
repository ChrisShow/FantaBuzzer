import javax.swing.*;
import java.awt.*;

public class PlayerFrame extends JFrame {
    private Player player;

    public PlayerFrame(Player player) {
        this.player = player;
        setTitle("Informazioni Player");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Imposta l'immagine di sfondo utilizzando un JLabel
        JLabel backgroundLabel = new JLabel(new ImageIcon("software\\FUNtaPad\\resources\\images\\playerFrameBg.jpg"));
        setContentPane(backgroundLabel);
        setLayout(new BorderLayout());

        initComponents();
        setLocationRelativeTo(null); // posiziono il frame al centro dello schermo
        setVisible(true);
    }

    private void initComponents() {

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(3, 2));
    
        add(createLabel("Nome Squadra:"));
        add(createValueLabel(player.getName()));

        add(createLabel("Crediti residui:"));
        add(createValueLabel(String.valueOf(player.getCredits())));

        add(createLabel("Giocatori acquistati:"));
        add(createValueLabel(String.valueOf(player.getFootballerList())));

        // Aggiungi il pannello dei componenti al centro del frame
        //add(contentPanel, BorderLayout.CENTER);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UtilityClass.caricaFont(20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        return label;
    }

    private JLabel createValueLabel(String text) {
        JLabel valueLabel = new JLabel(text);
        valueLabel.setFont(UtilityClass.caricaFont(15));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        valueLabel.setVerticalAlignment(SwingConstants.CENTER);
        valueLabel.setForeground(Color.WHITE);
        return valueLabel;
    }

    public static void main(String[] args) {
        Player samplePlayer = new Player("ChriShow", 50, 1, 1, 1);
        new PlayerFrame(samplePlayer);
    }
}

