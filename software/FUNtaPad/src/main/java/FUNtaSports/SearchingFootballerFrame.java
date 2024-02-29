package FUNtaSports;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
            footballerSurnameTextLabelWidth =  (((width - distance * 11) / 5) * 2),
            footballerSurnameTextLabelX = distance * 2,
            footballerSurnameTextLabelY =  distance * 6 + titleLabelHeight * 2,
            footballerTeamTextLabelWidth =  ((width - distance * 5) / 5),
            footballerTeamTextLabelX =  footballerSurnameTextLabelX + footballerSurnameTextLabelWidth,
            footballerInitialCreditsTextLabelWidth = ((width - distance * 5) / 5),
            footballerInitialCreditsTextLabelX = footballerSurnameTextLabelX + footballerSurnameTextLabelWidth + footballerTeamTextLabelWidth,
            footballerActualCreditsTextLabelWidth = ((width - distance * 5) / 5),
            footballerActualCreditsTextLabelX = footballerSurnameTextLabelX + footballerSurnameTextLabelWidth + footballerTeamTextLabelWidth + footballerInitialCreditsTextLabelWidth,
            footballerSearchedPanelY = distance * 7 + titleLabelHeight * 2 + footballerSurnameTextLabelHeight,
            footballerSearchedPanelHeight = height - footballerSearchedPanelY - distance * 6;

    public SearchingFootballerFrame(LinkedList<Footballer> footballers, Board board){

        this.footballers = footballers;
        this.board = board;

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
        titleLabel.setBackground(UtilityClass.CUSTOM_BLACK);
        titleLabel.setForeground(UtilityClass.CUSTOM_WHITE);
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
        footballerSurnameTextLabel.setFont(UtilityClass.caricaFont(23));
        footballerSurnameTextLabel.setBounds(footballerSurnameTextLabelX, footballerSurnameTextLabelY, footballerSurnameTextLabelWidth, footballerSurnameTextLabelHeight);
        footballerSurnameTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footballerSurnameTextLabel.setVerticalAlignment(SwingConstants.CENTER);
        footballerSurnameTextLabel.setOpaque(true);
        footballerSurnameTextLabel.setBackground(UtilityClass.CUSTOM_WHITE);
        footballerSurnameTextLabel.setForeground(UtilityClass.CUSTOM_BLACK);
        footballerSurnameTextLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        footballerSurnameTextLabel.setVisible(true);
        add(footballerSurnameTextLabel);

        //Impostazione Label testo squadra giocatore
        footballerTeamTextLabel = new JLabel("SQUADRA");
        footballerTeamTextLabel.setFont(UtilityClass.caricaFont(23));
        footballerTeamTextLabel.setBounds(footballerTeamTextLabelX, footballerSurnameTextLabelY, footballerTeamTextLabelWidth, footballerSurnameTextLabelHeight);
        footballerTeamTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footballerTeamTextLabel.setVerticalAlignment(SwingConstants.CENTER);
        footballerTeamTextLabel.setOpaque(true);
        footballerTeamTextLabel.setBackground(UtilityClass.CUSTOM_WHITE);
        footballerTeamTextLabel.setForeground(UtilityClass.CUSTOM_BLACK);
        footballerTeamTextLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        footballerTeamTextLabel.setVisible(true);
        add(footballerTeamTextLabel);

        //Impostazione Label testo squadra giocatore
        footballerInitialCreditsTextLabel = new JLabel("Cred. Iniziali");
        footballerInitialCreditsTextLabel.setFont(UtilityClass.caricaFont(23));
        footballerInitialCreditsTextLabel.setBounds(footballerInitialCreditsTextLabelX, footballerSurnameTextLabelY, footballerInitialCreditsTextLabelWidth, footballerSurnameTextLabelHeight);
        footballerInitialCreditsTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footballerInitialCreditsTextLabel.setVerticalAlignment(SwingConstants.CENTER);
        footballerInitialCreditsTextLabel.setOpaque(true);
        footballerInitialCreditsTextLabel.setBackground(UtilityClass.CUSTOM_WHITE);
        footballerInitialCreditsTextLabel.setForeground(UtilityClass.CUSTOM_BLACK);
        footballerInitialCreditsTextLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        footballerInitialCreditsTextLabel.setVisible(true);
        add(footballerInitialCreditsTextLabel);

        //Impostazione Label testo squadra giocatore
        footballerActualCreditsTextLabel = new JLabel("Cred. Attuali");
        footballerActualCreditsTextLabel.setFont(UtilityClass.caricaFont(23));
        footballerActualCreditsTextLabel.setBounds(footballerActualCreditsTextLabelX, footballerSurnameTextLabelY, footballerActualCreditsTextLabelWidth, footballerSurnameTextLabelHeight);
        footballerActualCreditsTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footballerActualCreditsTextLabel.setVerticalAlignment(SwingConstants.CENTER);
        footballerActualCreditsTextLabel.setOpaque(true);
        footballerActualCreditsTextLabel.setBackground(UtilityClass.CUSTOM_WHITE);
        footballerActualCreditsTextLabel.setForeground(UtilityClass.CUSTOM_BLACK);
        footballerActualCreditsTextLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        footballerActualCreditsTextLabel.setVisible(true);
        add(footballerActualCreditsTextLabel);

        // Impostazione panel giocatori cercati
        footballerSearchedPanel = new JPanel(null);
        //footballerSearchedPanel.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        footballerSearchedPanel.setLocation(distance, footballerSearchedPanelY);
        footballerSearchedPanel.setPreferredSize(new Dimension(fullWidthWithoutDistances - distance * 2, footballerSearchedPanelHeight));
        footballerSearchedPanel.setVisible(true);
        add(footballerSearchedPanel);

        // Impostazione slider
        verticalSlider = new JScrollPane(footballerSearchedPanel);
        verticalSlider.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        verticalSlider.setBounds(distance, footballerSearchedPanelY, fullWidthWithoutDistances, footballerSearchedPanelHeight);
        verticalSlider.setVisible(true);
        add(verticalSlider);

        setVisible(true);
    }

    private void updateFootballerSearchedPanel(){
        if(this.search.getText().isEmpty()){
            footballerSearchedPanel.setPreferredSize(new Dimension(footballerSearchedPanel.getWidth(), footballerSearchedPanelHeight));
            for(Component c : footballerSearchedPanel.getComponents()){
                footballerSearchedPanel.remove(c);
            }
            footballerSearchedPanel.repaint();
            return;
        }
        for(Component c : footballerSearchedPanel.getComponents()){
            footballerSearchedPanel.remove(c);
        }
        int cont = 0;
        for(Footballer f: footballers){
            if(f.getSurname().toLowerCase().contains(search.getText().toLowerCase())){
                PlayerSearchedPanel searchedPlayer = new PlayerSearchedPanel(this, f, cont);
                searchedPlayer.setBounds(distance, distance + footballerSurnameTextLabelHeight * cont,
                        footballerSurnameTextLabelWidth + footballerTeamTextLabelWidth + footballerInitialCreditsTextLabelWidth + footballerActualCreditsTextLabelWidth,
                        footballerSurnameTextLabelHeight + 2);
                searchedPlayer.setLayout(null);
                searchedPlayer.setBorder(BorderFactory.createLineBorder(Color.black, 3));
                searchedPlayer.setVisible(true);
                footballerSearchedPanel.add(searchedPlayer);
                cont++;
            }
        }
        if((distance * 2 + footballerSurnameTextLabelHeight * cont) < footballerSearchedPanelHeight)
            footballerSearchedPanel.setPreferredSize(new Dimension(footballerSearchedPanel.getWidth(), footballerSearchedPanelHeight));
        else
            footballerSearchedPanel.setPreferredSize(new Dimension(footballerSearchedPanel.getWidth(), distance * 2 + footballerSurnameTextLabelHeight * cont));
        footballerSearchedPanel.repaint();
    }

    private class PlayerSearchedPanel extends JPanel{

        private Footballer footballer;
        private SearchingFootballerFrame searchingFootballerFrame;
        private JLabel footballerSurnameTextLabel, footballerTeamTextLabel, footballerInitialCreditsTextLabel,
                footballerActualCreditsTextLabel;
        protected Color bg, fg;

        public PlayerSearchedPanel(SearchingFootballerFrame searchingFootballerFrame, Footballer f, int cont) {

            this.footballer = f;
            this.searchingFootballerFrame = searchingFootballerFrame;
            this.addMouseListener(new PlayerSearcherPanelMouseListener());

            if(cont%2 == 0){
                bg = UtilityClass.CUSTOM_ORANGE;
                fg = UtilityClass.CUSTOM_BLACK;
            }
            else{
                bg = UtilityClass.CUSTOM_GREEN;
                fg = UtilityClass.CUSTOM_WHITE;
            }
            this.setBackground(bg);

            int newFootballerTeamTeaxtLabelX = footballerSurnameTextLabelX + footballerSurnameTextLabelWidth - distance * 2,
                newFootballerInitialCreditsTextLabelX = newFootballerTeamTeaxtLabelX + footballerTeamTextLabelWidth,
                newFootballerActualCreditsTextLabelX = newFootballerInitialCreditsTextLabelX + footballerInitialCreditsTextLabelWidth;

            //Impostazione Label testo giocatore
            footballerSurnameTextLabel = new JLabel(f.getSurname());
            footballerSurnameTextLabel.setFont(UtilityClass.caricaFont(20));
            footballerSurnameTextLabel.setBounds(0, 0, footballerSurnameTextLabelWidth, footballerSurnameTextLabelHeight);
            footballerSurnameTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
            footballerSurnameTextLabel.setVerticalAlignment(SwingConstants.CENTER);
            footballerSurnameTextLabel.setForeground(fg);
            footballerSurnameTextLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            footballerSurnameTextLabel.setVisible(true);
            add(footballerSurnameTextLabel);

            //Impostazione Label testo squadra giocatore
            footballerTeamTextLabel = new JLabel(f.getTeam());
            footballerTeamTextLabel.setFont(UtilityClass.caricaFont(20));
            footballerTeamTextLabel.setBounds(newFootballerTeamTeaxtLabelX, 0, footballerTeamTextLabelWidth, footballerSurnameTextLabelHeight);
            footballerTeamTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
            footballerTeamTextLabel.setVerticalAlignment(SwingConstants.CENTER);
            footballerTeamTextLabel.setForeground(fg);
            footballerTeamTextLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            footballerTeamTextLabel.setVisible(true);
            add(footballerTeamTextLabel);

            //Impostazione Label testo squadra giocatore
            footballerInitialCreditsTextLabel = new JLabel("" + f.getInitialValue());
            footballerInitialCreditsTextLabel.setFont(UtilityClass.caricaFont(20));
            footballerInitialCreditsTextLabel.setBounds(newFootballerInitialCreditsTextLabelX, 0, footballerInitialCreditsTextLabelWidth, footballerSurnameTextLabelHeight);
            footballerInitialCreditsTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
            footballerInitialCreditsTextLabel.setVerticalAlignment(SwingConstants.CENTER);
            footballerInitialCreditsTextLabel.setForeground(fg);
            footballerInitialCreditsTextLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            footballerInitialCreditsTextLabel.setVisible(true);
            add(footballerInitialCreditsTextLabel);

            //Impostazione Label testo squadra giocatore
            footballerActualCreditsTextLabel = new JLabel("" + f.getActualValue());
            footballerActualCreditsTextLabel.setFont(UtilityClass.caricaFont(20));
            footballerActualCreditsTextLabel.setBounds(newFootballerActualCreditsTextLabelX, 0, footballerActualCreditsTextLabelWidth, footballerSurnameTextLabelHeight);
            footballerActualCreditsTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
            footballerActualCreditsTextLabel.setVerticalAlignment(SwingConstants.CENTER);
            footballerActualCreditsTextLabel.setForeground(fg);
            footballerActualCreditsTextLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            footballerActualCreditsTextLabel.setVisible(true);
            add(footballerActualCreditsTextLabel);

            this.setVisible(true);
        }

        private class PlayerSearcherPanelMouseListener implements MouseListener{

            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                board.addFootballerToAuction(footballer);
                searchingFootballerFrame.dispose();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(UtilityClass.CUSTOM_LIGHT_CYAN);
                for(Component c: getComponents()){
                    if(c instanceof JLabel)
                        ((JLabel) c).setForeground(UtilityClass.CUSTOM_BLACK);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(bg);
                for(Component c: getComponents()){
                    if(c instanceof JLabel)
                        ((JLabel) c).setForeground(fg);
                }
            }
        }
    }

    private class SearchingFootballerFrameKeyboardListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {
            updateFootballerSearchedPanel();
        }
    }
}
