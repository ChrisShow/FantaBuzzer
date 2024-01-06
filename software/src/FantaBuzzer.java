import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class FantaBuzzer extends JFrame{

    private JLabel[] labels;
    private JLabel title;
    private JLabel subtitle;
    private JTextField[] fields;
    private JButton start;
    private JButton exit;
    private JLabel creditLabel;
    private JTextField creditField;
    private int teams = 0, credits = 0;
    private String stringTeams = "";

    public FantaBuzzer(){

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimensioneSchermo = toolkit.getScreenSize();
        int screenWidth = dimensioneSchermo.width;
        int screenHeight = dimensioneSchermo.height;

        int width = 600, height = 800;

        setBounds((screenWidth - width) / 2, (screenHeight - height) / 2, width, height);
        setLayout(null);
        setTitle("FantaBuzzer Launcher");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int distance = 10;
        int titleWidth = 350, titleHeight = 90, titleFontSize = 65;

        title = new JLabel("FantaBuzzer");
        title.setFont(UtilityClass.caricaFont(titleFontSize));
        title.setBounds((width - titleWidth) / 2, (distance*2), titleWidth, titleHeight);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setVisible(true);
        add(title);

        int subtitleWidth = 550, subtitleHeight = 50;

        subtitle = new JLabel("Inserisci le squadre corrispondenti ai numeri di pad");
        subtitle.setFont(UtilityClass.caricaFont(24));
        subtitle.setBounds((width - subtitleWidth) / 2, (distance * 2) + titleHeight, subtitleWidth, subtitleHeight);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        subtitle.setVerticalAlignment(SwingConstants.CENTER);
        subtitle.setVisible(true);
        add(subtitle);

        int labelWidth = 100, labelHeight = 45;
        int fieldWidth = 300, fieldHeight = 45;
        
        labels = new JLabel[8];
        fields = new JTextField[8];
        for (int i = 0; i < fields.length; i++) {
            labels[i] = new JLabel("Squadra " + (i+1) + ":");
            labels[i].setFont(UtilityClass.caricaFont(18));
            labels[i].setBounds((width / 2) - (distance + labelWidth + fieldWidth) / 2, (distance * (i+4) + titleHeight + subtitleHeight + (labelHeight * i)), labelWidth, labelHeight);
            labels[i].setVisible(true);
            labels[i].setVerticalAlignment(SwingConstants.CENTER);
            add(labels[i]);

            fields[i] = new JTextField();
            fields[i].setFont(UtilityClass.caricaFont(18));
            fields[i].setBounds(((width / 2) - (distance + labelWidth + fieldWidth) / 2) + labelWidth + distance, (distance * (i+4) + titleHeight + subtitleHeight + (fieldHeight * i)), fieldWidth, fieldHeight);
            fields[i].setVisible(true);
            fields[i].setHorizontalAlignment(SwingConstants.CENTER);
            add(fields[i]);
        }

        creditLabel = new JLabel("Crediti:");
        creditLabel.setFont(UtilityClass.caricaFont(18));
        creditLabel.setBounds((width / 2) - (distance + labelWidth + fieldWidth) / 2, (distance * 12 + titleHeight + subtitleHeight + (labelHeight * 8)), labelWidth, labelHeight);
        creditLabel.setVisible(true);
        creditLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(creditLabel);

        creditField = new JTextField();
        creditField.setFont(UtilityClass.caricaFont(18));
        creditField.setBounds((width / 2) - ((distance + labelWidth + fieldWidth) / 2) + labelWidth + distance, (distance * 12 + titleHeight + subtitleHeight + (labelHeight * 8)), fieldWidth, fieldHeight);
        creditField.setVisible(true);
        creditField.setHorizontalAlignment(SwingConstants.CENTER);
        add(creditField);

        int buttonWidth = 100, buttonHeight = 50;

        start = new JButton("Inizia");
        start.setFont(UtilityClass.caricaFont(20));
        start.setBounds((width - (distance / 2) - (buttonWidth * 2)) / 2, (distance*14) + titleHeight + subtitleHeight + (labelHeight * 9) , buttonWidth, buttonHeight);
        start.setVisible(true);
        start.setFocusable(false);
        start.addActionListener(new FantaBuzzerListener());
        add(start);

        exit = new JButton("Esci");
        exit.setFont(UtilityClass.caricaFont(20));
        exit.setBounds((width) / 2 + (distance / 2), (distance*14) + titleHeight + subtitleHeight + (labelHeight * 9) , buttonWidth, buttonHeight);
        exit.setVisible(true);
        exit.setFocusable(false);
        exit.addActionListener(new FantaBuzzerListener());
        add(exit);

        this.setVisible(true);
        
    }

    public static void main(String[] args) {
        FantaBuzzer fantaBuzzer = new FantaBuzzer();
    }

    private boolean checkValues() {
        for (int i = 0; i < fields.length; i++) {
            if(!fields[i].getText().equals("")){
                this.stringTeams += fields[i].getText() + "-";
                this.teams++;
            } else {
                this.stringTeams += "null-";
            }               
        }
        if(this.teams < 6){
            JOptionPane.showMessageDialog(null, "Minimo di squadre non raggiunto: le squadre sono meno di 6", "Minimo di squadre non raggiunto", JOptionPane.DEFAULT_OPTION);
            return false;
        }
        if(this.teams % 2 == 1){
            JOptionPane.showMessageDialog(null, "Le squadre sono dispari", "Numero di squadre dispari", JOptionPane.DEFAULT_OPTION);
            return false;
        }
        this.stringTeams = this.stringTeams.substring(0, this.stringTeams.length() - 1);

        try{
            this.credits = Integer.parseInt(creditField.getText());
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Controlla il valore dei crediti", "Errore sul valore dei crediti", JOptionPane.DEFAULT_OPTION);
            return false;
        }
        if(this.credits <= 0){
            JOptionPane.showMessageDialog(null, "Crediti insufficienti", "Errore sul valore dei crediti", JOptionPane.DEFAULT_OPTION);
            return false;
        }

        return true;
    }

    private class FantaBuzzerListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == exit) System.exit(1);

            if(e.getSource() == start)
                if(checkValues()){
                    dispose();
                    new Board(stringTeams, teams, credits);
                }        
        }

    }
}