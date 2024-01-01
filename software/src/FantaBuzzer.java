import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class FantaBuzzer extends JFrame{

    private JLabel[] labels;
    private JLabel title;
    private JLabel subtitle;
    private JTextField[] fields;
    private JButton start;

    public FantaBuzzer(){

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimensioneSchermo = toolkit.getScreenSize();
        int screenWidth = dimensioneSchermo.width;
        int screenHeight = dimensioneSchermo.height;

        int width = 600, height = 800;

        setBounds((screenWidth - width) / 2, (screenHeight - height) / 2, width, height);
        setLayout(null);
        setTitle("FantaBuzzer Launcher");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int distance = 10;
        int titleWidth = 300, titleHeight = 100, titleFontSize = 50;

        title = new JLabel("FantaBuzzer");
        title.setFont(caricaFont(titleFontSize));
        title.setBounds((width - titleWidth) / 2, distance, titleWidth, titleHeight);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVisible(true);
        add(title);

        int subtitleWidth = 500, subtitleHeight = 50;

        subtitle = new JLabel("Inserisci i nomi delle squadre facendoli corrispondere ai numeri di pad");
        subtitle.setBounds((width - subtitleWidth) / 2, (distance * 2) + titleHeight, subtitleWidth, subtitleHeight);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        subtitle.setVisible(true);
        add(subtitle);

        int labelWidth = 100, labelHeight = 50;
        int fieldWidth = 200, fieldHeight = 50;
        
        labels = new JLabel[8];
        fields = new JTextField[8];
        for (int i = 0; i < fields.length; i++) {
            labels[i] = new JLabel("Squadra " + (i+1) + ":");
            labels[i].setBounds((width - labelWidth - fieldWidth - (distance * 3)) / 2, (distance * (i+4) + titleHeight + subtitleHeight + (labelHeight * i)), labelWidth, labelHeight);
            labels[i].setVisible(true);
            labels[i].setHorizontalAlignment(SwingConstants.CENTER);
            add(labels[i]);

            fields[i] = new JTextField();
        }

        this.setVisible(true);
        
    }

    public static void main(String[] args) {
        FantaBuzzer fantaBuzzer = new FantaBuzzer();
    }

    private static Font caricaFont(int dimensione) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new java.io.File("software/resources/coolveticaRG.otf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            return font.deriveFont(Font.PLAIN, dimensione);
        } catch (java.awt.FontFormatException | java.io.IOException e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, 14); // Fallback a un font predefinito in caso di errore
        }
    }
}