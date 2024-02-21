import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class SearchingFootballerFrame extends JFrame {

    private LinkedList<Footballer> footballers;
    private JLabel titleLabel, footballerSurnameTextLabel, footballerTeamTextLabel, footballerInitialCreditsTextLabel,
            footballerActualCreditsTextLabel;
    private JTextField search;
    private JPanel footballerSearchedPanel;
    private JScrollPane verticalSlider;
    private Board board;
    private final int width = 1000, height = 650, distance = 10,
            titleLabelHeight = (height - distance * 9) / 7,
            titleLabelWidth = (width - distance * 4) / 2,
            footballerSurnameTextLabelHeight = (height - distance * 9) / (7 * 2),
            footballerSurnameTextLabelWidth =  (((width - distance * 5) / 5) * 2) - distance * 2,
            footballerSurnameTextLabelY =  distance * 6 + titleLabelHeight * 2,
            footballerTeamTextLabelWidth =  ((width - distance * 5) / 5),
            footballerTeamTextLabelX =  distance * 2 + footballerSurnameTextLabelWidth,
            footballerInitialCreditsTextLabelWidth = ((width - distance * 5) / 5),
            footballerInitialCreditsTextLabelX = distance * 2 + footballerSurnameTextLabelWidth + footballerTeamTextLabelWidth,
            footballerActualCreditsTextLabelWidth = ((width - distance * 5) / 5),
            footballerActualCreditsTextLabelX = distance * 2 + footballerSurnameTextLabelWidth + footballerTeamTextLabelWidth + footballerInitialCreditsTextLabelWidth
                    ;

    public SearchingFootballerFrame(LinkedList<Footballer> footballers){

        this.footballers = footballers;

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
        footballerInitialCreditsTextLabel = new JLabel("Cred. Iniziali");
        footballerInitialCreditsTextLabel.setFont(UtilityClass.caricaFont(20));
        footballerInitialCreditsTextLabel.setBounds(footballerInitialCreditsTextLabelX, footballerSurnameTextLabelY, footballerInitialCreditsTextLabelWidth, footballerSurnameTextLabelHeight);
        footballerInitialCreditsTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footballerInitialCreditsTextLabel.setVerticalAlignment(SwingConstants.CENTER);
        footballerInitialCreditsTextLabel.setOpaque(true);
        footballerInitialCreditsTextLabel.setBackground(Color.LIGHT_GRAY);
        footballerInitialCreditsTextLabel.setForeground(Color.black);
        footballerInitialCreditsTextLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        footballerInitialCreditsTextLabel.setVisible(true);
        add(footballerInitialCreditsTextLabel);

        //Impostazione Label testo squadra giocatore
        footballerActualCreditsTextLabel = new JLabel("Cred. Attuali");
        footballerActualCreditsTextLabel.setFont(UtilityClass.caricaFont(20));
        footballerActualCreditsTextLabel.setBounds(footballerActualCreditsTextLabelX, footballerSurnameTextLabelY, footballerActualCreditsTextLabelWidth, footballerSurnameTextLabelHeight);
        footballerActualCreditsTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footballerActualCreditsTextLabel.setVerticalAlignment(SwingConstants.CENTER);
        footballerActualCreditsTextLabel.setOpaque(true);
        footballerActualCreditsTextLabel.setBackground(Color.LIGHT_GRAY);
        footballerActualCreditsTextLabel.setForeground(Color.black);
        footballerActualCreditsTextLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        footballerActualCreditsTextLabel.setVisible(true);
        add(footballerActualCreditsTextLabel);

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
        verticalSlider.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        verticalSlider.setBounds(distance, footballerSearchedPanelY, footballerSearchedPanelWidth, footballerSearchedPanelHeight);
        verticalSlider.setVisible(true);
        add(verticalSlider);

        setVisible(true);
    }

    private void updateFootballerSearchedPanel(){
        for(Component c : footballerSearchedPanel.getComponents()){
            footballerSearchedPanel.remove(c);
        }
        int cont = 0;

        for(Footballer f: footballers){
            if(f.getSurname().contains(search.getText())){
                PlayerSearchedPanel searchedPlayer = new PlayerSearchedPanel(this, f);
                searchedPlayer.setLocation(distance, distance * (cont++) + footballerSurnameTextLabelWidth * cont);
                cont++;
            }
        }
        footballerSearchedPanel.repaint();
    }

    public static void main(String[] args) {
        SearchingFootballerFrame searchingFootballerFrame = new SearchingFootballerFrame(UtilityClass.initFootballers());
    }

    private class PlayerSearchedPanel extends JPanel{

        private Footballer footballer;
        private SearchingFootballerFrame searchingFootballerFrame;
        private JLabel footballerSurnameTextLabel, footballerTeamTextLabel, footballerInitialCreditsTextLabel,
                footballerActualCreditsTextLabel;

        public PlayerSearchedPanel(SearchingFootballerFrame searchingFootballerFrame, Footballer f) {
            this.footballer = f;
            this.searchingFootballerFrame = searchingFootballerFrame;

            this.setSize(footballerSurnameTextLabelWidth + footballerTeamTextLabelWidth + footballerInitialCreditsTextLabelWidth + footballerActualCreditsTextLabelWidth, footballerSurnameTextLabelHeight);
            this.setLayout(null);
            this.setBorder(BorderFactory.createLineBorder(Color.black, 3));

            //Impostazione Label testo giocatore
            footballerSurnameTextLabel = new JLabel(f.getSurname());
            System.out.println(f.getSurname());
            footballerSurnameTextLabel.setFont(UtilityClass.caricaFont(18));
            footballerSurnameTextLabel.setBounds(0, 0, footballerSurnameTextLabelWidth, footballerSurnameTextLabelHeight);
            footballerSurnameTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
            footballerSurnameTextLabel.setVerticalAlignment(SwingConstants.CENTER);
            footballerSurnameTextLabel.setForeground(Color.black);
            footballerSurnameTextLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            footballerSurnameTextLabel.setVisible(true);
            add(footballerSurnameTextLabel);

            //Impostazione Label testo squadra giocatore
            footballerTeamTextLabel = new JLabel(f.getTeam());
            footballerTeamTextLabel.setFont(UtilityClass.caricaFont(18));
            footballerTeamTextLabel.setBounds(footballerTeamTextLabelX, 0, footballerTeamTextLabelWidth, footballerSurnameTextLabelHeight);
            footballerTeamTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
            footballerTeamTextLabel.setVerticalAlignment(SwingConstants.CENTER);
            footballerTeamTextLabel.setForeground(Color.black);
            footballerTeamTextLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            footballerTeamTextLabel.setVisible(true);
            add(footballerTeamTextLabel);

            //Impostazione Label testo squadra giocatore
            footballerInitialCreditsTextLabel = new JLabel("" + f.getInitialValue());
            footballerInitialCreditsTextLabel.setFont(UtilityClass.caricaFont(18));
            footballerInitialCreditsTextLabel.setBounds(footballerInitialCreditsTextLabelX, 0, footballerInitialCreditsTextLabelWidth, footballerSurnameTextLabelHeight);
            footballerInitialCreditsTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
            footballerInitialCreditsTextLabel.setVerticalAlignment(SwingConstants.CENTER);
            footballerInitialCreditsTextLabel.setForeground(Color.black);
            footballerInitialCreditsTextLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            footballerInitialCreditsTextLabel.setVisible(true);
            add(footballerInitialCreditsTextLabel);

            //Impostazione Label testo squadra giocatore
            footballerActualCreditsTextLabel = new JLabel("" + f.getActualValue());
            footballerActualCreditsTextLabel.setFont(UtilityClass.caricaFont(18));
            footballerActualCreditsTextLabel.setBounds(footballerActualCreditsTextLabelX, 0, footballerActualCreditsTextLabelWidth, footballerSurnameTextLabelHeight);
            footballerActualCreditsTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
            footballerActualCreditsTextLabel.setVerticalAlignment(SwingConstants.CENTER);
            footballerActualCreditsTextLabel.setForeground(Color.black);
            footballerActualCreditsTextLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            footballerActualCreditsTextLabel.setVisible(true);
            add(footballerActualCreditsTextLabel);

            this.setVisible(true);
            footballerSearchedPanel.add(this);
        }
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
