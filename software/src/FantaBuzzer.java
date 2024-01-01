import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Toolkit;
import java.awt.Dimension;

public class FantaBuzzer extends JFrame{

    private JLabel[] labels;
    private JLabel title;
    private JTextField[] fields;
    private JButton start;

    public FantaBuzzer(){

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimensioneSchermo = toolkit.getScreenSize();
        int screenWidth = dimensioneSchermo.width;
        int screenHeight = dimensioneSchermo.height;

        int width = 600, height = 800;

        setBounds((screenWidth - width) / 2, (screenHeight - height) / 2, width, height);
        setTitle("FantaBuzzer Launcher");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int distance = 10;
        int titleWidth = 400, titleHeight = 100;

        title = new JLabel("FantaBuzzer");
        title.setBounds((width - titleWidth) / 2, distance, titleWidth, titleHeight);
        title.setVisible(true);
        add(title);

        

        int labelWidth = 100, labelHeight = 50;
        int fieldWidth = 200, fieldHeight = 50;
        
        labels = new JLabel[8];
        fields = new JTextField[8];
        for (int i = 0; i < fields.length; i++) {
            labels[i] = new JLabel("Squadra " + (i+1) + ":");
            labels[i].setBounds((width - labelHeight - fieldHeight - (distance * 3) / 2), i, labelWidth, labelHeight);
            fields[i] = new JTextField();
        }

        this.setVisible(true);
        
    }

    public static void main(String[] args) {
        FantaBuzzer fantaBuzzer = new FantaBuzzer();
    }
}