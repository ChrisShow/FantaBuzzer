import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class SearchingFootballerFrame extends JFrame {

    private LinkedList<Footballer> footballers;
    private JLabel titleLabel, footballerSurnameTextLabel, footballerTeamTextLabel;
    //TODO ID
    private JLabel footballerIDTextLabel, footballerInitialCreditsTextLabel, footballerActualCreditsTextLabel;
    private JTextField search;
    private JPanel footballerSearchedPanel;
    private JScrollPane verticalSlider;
    private Board board;

    public SearchingFootballerFrame(LinkedList<Footballer> footballers){

        this.footballers = footballers;

        int width = 1000, height = 650, distance = 10;

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimensioneSchermo = toolkit.getScreenSize();
        int screenWidth = dimensioneSchermo.width;
        int screenHeight = dimensioneSchermo.height;
        int fullWidthWithoutDistances =  (width - distance * 4);

        // Impostazioni JFrame
        setTitle("Cerca giocatore");
        setSize(width, height);
        setBounds((screenWidth - width) / 2, (screenHeight - height) / 2, width, height);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        //Impostazione Label titolo
        int titleLabelHeight = (height - distance * 9) / 7;
        int titleLabelWidth = (width - distance * 4) / 2;
        titleLabel = new JLabel("Cerca un giocatore");
        titleLabel.setFont(UtilityClass.caricaFont(40));
        titleLabel.setBounds((width - titleLabelWidth) / 2, distance * 2, titleLabelWidth, titleLabelHeight);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(UtilityClass.CUSTOM_ORANGE);
        titleLabel.setForeground(Color.black);
        titleLabel.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        titleLabel.setVisible(true);
        add(titleLabel);

        //Impostazione TextField ricerca giocatore
        search = new JTextField();
        search.setFont(UtilityClass.caricaFont(40));
        search.setBounds(distance, distance * 4 + titleLabelHeight, fullWidthWithoutDistances, titleLabelHeight);
        search.setHorizontalAlignment(SwingConstants.CENTER);
        search.setForeground(Color.black);
        search.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        search.addKeyListener(new SearchingFootballerFrameKeyboardListener());
        search.setVisible(true);
        add(search);

        //Impostazione Label testo giocatore
        int footballerSurnameTextLabelHeight = (height - distance * 9) / (7 * 2);
        int footballerSurnameTextLabelWidth =  (((width - distance * 5) / 5) * 2) - distance * 2;
        int footballerSurnameTextLabelY =  distance * 6 + titleLabelHeight * 2;
        footballerSurnameTextLabel = new JLabel("GIOCATORE");
        footballerSurnameTextLabel.setFont(UtilityClass.caricaFont(20));
        footballerSurnameTextLabel.setBounds(distance * 2, footballerSurnameTextLabelY, footballerSurnameTextLabelWidth, footballerSurnameTextLabelHeight);
        footballerSurnameTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footballerSurnameTextLabel.setVerticalAlignment(SwingConstants.CENTER);
        footballerSurnameTextLabel.setOpaque(true);
        footballerSurnameTextLabel.setBackground(Color.LIGHT_GRAY);
        footballerSurnameTextLabel.setForeground(Color.black);
        footballerSurnameTextLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        footballerSurnameTextLabel.setVisible(true);
        add(footballerSurnameTextLabel);

        //Impostazione Label testo squadra giocatore
        int footballerTeamTextLabelWidth =  ((width - distance * 5) / 5);
        int footballerTeamTextLabelX =  distance * 2 + footballerSurnameTextLabelWidth;
        footballerTeamTextLabel = new JLabel("SQUADRA");
        footballerTeamTextLabel.setFont(UtilityClass.caricaFont(20));
        footballerTeamTextLabel.setBounds(footballerTeamTextLabelX, footballerSurnameTextLabelY, footballerTeamTextLabelWidth, footballerSurnameTextLabelHeight);
        footballerTeamTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footballerTeamTextLabel.setVerticalAlignment(SwingConstants.CENTER);
        footballerTeamTextLabel.setOpaque(true);
        footballerTeamTextLabel.setBackground(Color.LIGHT_GRAY);
        footballerTeamTextLabel.setForeground(Color.black);
        footballerTeamTextLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        footballerTeamTextLabel.setVisible(true);
        add(footballerTeamTextLabel);

        //Impostazione Label testo squadra giocatore
        int footballerTeamTextLabelWidth =  ((width - distance * 5) / 3);
        int footballerTeamTextLabelX =  distance * 2 + footballerSurnameTextLabelWidth;
        footballerTeamTextLabel = new JLabel("SQUADRA");
        footballerTeamTextLabel.setFont(UtilityClass.caricaFont(20));
        footballerTeamTextLabel.setBounds(footballerTeamTextLabelX, footballerSurnameTextLabelY, footballerTeamTextLabelWidth, footballerSurnameTextLabelHeight);
        footballerTeamTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footballerTeamTextLabel.setVerticalAlignment(SwingConstants.CENTER);
        footballerTeamTextLabel.setOpaque(true);
        footballerTeamTextLabel.setBackground(Color.LIGHT_GRAY);
        footballerTeamTextLabel.setForeground(Color.black);
        footballerTeamTextLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        footballerTeamTextLabel.setVisible(true);
        add(footballerTeamTextLabel);

        // Impostazione panel giocatori cercati
        int footballerSearchedPanelY = distance * 7 + titleLabelHeight * 2 + footballerSurnameTextLabelHeight;
        int footballerSearchedPanelHeight = height - footballerSearchedPanelY - distance * 6;
        int footballerSearchedPanelWidth = fullWidthWithoutDistances;
        footballerSearchedPanel = new JPanel(null);
        footballerSearchedPanel.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        footballerSearchedPanel.setLocation(distance, footballerSearchedPanelY);
        footballerSearchedPanel.setPreferredSize(new Dimension(footballerSearchedPanelWidth, footballerSearchedPanelHeight));
        footballerSearchedPanel.setVisible(true);
        add(footballerSearchedPanel);

        // Impostazione slider
        verticalSlider = new JScrollPane(footballerSearchedPanel);
        verticalSlider.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        verticalSlider.setBounds(distance, footballerSearchedPanelY, footballerSearchedPanelWidth, footballerSearchedPanelHeight);
        verticalSlider.setVisible(true);
        add(verticalSlider);

        setVisible(true);
    }

    private void updateFootballerSearchedPanel(){
        System.out.println("Suka");
        for(Component c : footballerSearchedPanel.getComponents()){
            footballerSearchedPanel.remove(c);
        }
        for(Footballer f: footballers){
            if(f.getSurname().contains(search.getText())){
                JLabel label = new JLabel("");
            }
        }
    }

    public static void main(String[] args) {
        SearchingFootballerFrame searchingFootballerFrame = new SearchingFootballerFrame(UtilityClass.initFootballers());
    }

    private class SearchingFootballerFrameKeyboardListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
            updateFootballerSearchedPanel();
        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
