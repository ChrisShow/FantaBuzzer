import org.apache.poi.ss.usermodel.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;
import javax.swing.*;

public class Board extends JFrame {

    private LinkedList<Player> players;
    private int offer;
    private boolean firstStartTimer;
    private JButton newAuction, startAuction;
    private FantaTimer fantaTimer;
    private LinkedList<Footballer> footballers;
    private Player lastOfferPlayer;
    private Footballer actualFootballer;
    private JPanel controlPanel, playersPanel, timerPanel;
    private JLabel footballerTextLabel, footballerLabel;

    public Board(String stringTeams, int teams, int credits) {

        this.offer = 0;
        this.firstStartTimer = true;

        /* GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight(); */

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();

        setLayout(null);
        setTitle("FUNtaPad");
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(new BoardKeyboardListener());
        setFocusable(true);
        requestFocus();
        //setBounds(0, 0, screenWidth, screenHeight);

        int distance = 10;

        // Disposizione JPanels
        int controlPanelWidth = (int) screenWidth - distance * 2;
        int controlPanelHeight = (int) ((screenHeight - distance * 4) / 9);
        controlPanel = new JPanel(null);
        controlPanel.setBounds(distance, distance, controlPanelWidth, controlPanelHeight);
        controlPanel.setOpaque(false);

        int playersPanelHeight = (int) ((screenHeight - distance * 4) / 9) * 6;
        playersPanel = new JPanel(null);
        playersPanel.setBounds(distance, (distance * 2) + controlPanelHeight, controlPanelWidth, playersPanelHeight);
        playersPanel.setOpaque(false);

        int timerPanelHeight = (int) (screenHeight - ((distance * 6.5) + controlPanelHeight + playersPanelHeight));
        timerPanel = new JPanel(null);
        timerPanel.setBounds(distance, (distance * 3) + controlPanelHeight + playersPanelHeight, controlPanelWidth, timerPanelHeight);
        timerPanel.setOpaque(false);

        // Posizionamento Label scritta giocatore in asta
        int footballerTextLabelWidth = (int) (controlPanelWidth - distance * 5) / 7;
        int footballerTextLabelHeight = controlPanelHeight - distance - distance / 2;
        footballerTextLabel = new JLabel("Giocatore in asta:");
        footballerTextLabel.setFont(UtilityClass.caricaFont(25));
        footballerTextLabel.setBounds(distance, (distance + distance / 2), footballerTextLabelWidth, footballerTextLabelHeight);
        footballerTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footballerTextLabel.setVerticalAlignment(SwingConstants.CENTER);
        footballerTextLabel.setOpaque(true);
        footballerTextLabel.setBackground(UtilityClass.CUSTOM_BLACK);
        footballerTextLabel.setForeground(Color.white);
        footballerTextLabel.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        footballerTextLabel.setVisible(true);
        controlPanel.add(footballerTextLabel);

        // Posizionamento Label giocatore in asta
        int footballerLabelWidth = (int) ((controlPanelWidth - distance * 5) / 7) * 4;
        footballerLabel = new JLabel();
        footballerLabel.setFont(UtilityClass.caricaFont(50));
        footballerLabel.setBounds(distance * 2 + footballerTextLabelWidth, (distance + distance / 2), footballerLabelWidth, footballerTextLabelHeight);
        footballerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footballerLabel.setVerticalAlignment(SwingConstants.CENTER);
        footballerLabel.setOpaque(true);
        footballerLabel.setBackground(UtilityClass.CUSTOM_WHITE);
        footballerLabel.setForeground(Color.black);
        footballerLabel.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        footballerLabel.setVisible(true);
        controlPanel.add(footballerLabel);

        // Posizionamento bottone inizio asta
        int startAuctionX = distance * 3 + footballerTextLabelWidth + footballerLabelWidth;
        startAuction = new JButton("Inizia Asta");
        startAuction.setFont(UtilityClass.caricaFont(25));
        startAuction.addActionListener(new BoardListener(this));
        startAuction.setFocusable(false);
        startAuction.setBounds(startAuctionX, distance + distance / 2, footballerTextLabelWidth, footballerTextLabelHeight);
        startAuction.setVisible(true);
        startAuction.setEnabled(false);
        controlPanel.add(startAuction);

        // Posizionamento bottone nuova asta
        int newAuctionX = distance * 4 + footballerTextLabelWidth * 2 + footballerLabelWidth;
        newAuction = new JButton("Nuova Asta");
        newAuction.setFont(UtilityClass.caricaFont(25));
        newAuction.addActionListener(new BoardListener(this));
        newAuction.setFocusable(false);
        newAuction.setBounds(newAuctionX, distance + distance / 2, footballerTextLabelWidth, footballerTextLabelHeight);
        newAuction.setVisible(true);
        controlPanel.add(newAuction);

        int playerWidth = (int) ((controlPanelWidth - (distance * ((teams / 2) + 1))) / (teams / 2));
        int playerHeight = (int) ((playersPanelHeight - (distance * 3)) / 2);

        players = new LinkedList<>();
        String[] strings = stringTeams.split("-");
        int j = 0;
        for (int i = 0; i < strings.length; i++) {
            if(!strings[i].equals("null")){
                Player p = new Player(strings[i], credits, i, playerWidth, playerHeight, this);
                int currentPlayerX = (distance * ((j % (teams / 2)) + 1)) + (playerWidth * (j % (teams / 2)));
                int currentPlayerY = distance + ((distance + playerHeight) * ((int) j / (teams / 2)));
                p.setBounds(currentPlayerX, currentPlayerY, playerWidth, playerHeight);
                playersPanel.add(p);
                p.setVisible(true);
                players.addLast(p);
                j++;
            }
            else
                players.addLast(null);
        }

        int fantaTimerWidth = (int) (timerPanelHeight - distance * 2);
        int fantaTimerHeight = fantaTimerWidth;
        fantaTimer = new FantaTimer(fantaTimerWidth, fantaTimerHeight, this);
        fantaTimer.setBounds((int)(controlPanelWidth - fantaTimerWidth) / 2, (timerPanelHeight - fantaTimerHeight) / 2, fantaTimerWidth, fantaTimerHeight);
        fantaTimer.setVisible(true);
        timerPanel.add(fantaTimer);

        //Preleva i gicatori dal listone e li salva come oggetti
        initFootballers();
        this.actualFootballer = footballers.removeFirst();

        // setVisible di tutti i panel e frame
        controlPanel.setVisible(true);
        playersPanel.setVisible(true);
        timerPanel.setVisible(true);
        add(controlPanel);
        add(playersPanel);
        add(timerPanel);
        setVisible(true);
        
    }

    public void auctionTerminated(){
        new EndAuction(lastOfferPlayer, offer, actualFootballer, this);
    }

    private void newOffer(int num){
        if(!firstStartTimer && validOffer(num)){
            int i = 0;
            for (Player player : players) {
                if(i == num){
                    lastOfferPlayer = player;
                    this.fantaTimer.resetTimer();
                    this.offer += 2;
                    player.newOffer(this.offer);
                }
                else {
                    if(players.get(num) != null)
                        if(player != null)
                            player.cancelOffer();
                }
                i++;
            }
        }
    }

    private boolean validOffer(int num) {
        if(players.get(num) == null || players.get(num) == lastOfferPlayer || players.get(num).getCredits() < (this.offer+2)) return false;
        if(actualFootballer.isGoalkeeper() && players.get(num).howManyGK() > 2) return false;
        else if (actualFootballer.isDefender() && players.get(num).howManyDef() > 7) return false;
        else if (actualFootballer.isMidfielder() && players.get(num).howManyMid() > 7) return false;
        else if(actualFootballer.isAttacker() && players.get(num).howManyAtt() > 5) return false;
        return true;
    }

    public void resetAuction(Player player, int offer, Footballer footballer) {
        this.firstStartTimer = !this.firstStartTimer;
        player.newFootballerBougth(footballer, offer);
        lastOfferPlayer = null;
        footballers.remove(actualFootballer);
        actualFootballer = null;
        footballerLabel.setText("");
        firstStartTimer = true;
        startAuction.setEnabled(false);
        newAuction.setEnabled(true);
        for(Player p: players)
            if(p != null)
                p.cancelOffer();
        this.offer = 0;
    }

    private void initFootballers(){
        footballers = new LinkedList<>();
        try {
            String filePath = "software/FUNtaPad/resources/files/Quotazioni_Fantacalcio_Stagione_2023_24.xlsx";
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
            Workbook workbook = WorkbookFactory.create(fileInputStream);

            // Assume che ci sia solo un foglio di lavoro (worksheet)
            Sheet sheet = workbook.getSheetAt(0);

            int i = 0;
            for (Row row : sheet) {
                boolean nextRow = false;
                if(i > 1) {
                    double id = 0.0, actualValue = 0.0, initialValue = 0.0, valueDiff = 0.0,
                            actualValueMantra = 0.0, initialValueMantra = 0.0,
                            valueDiffMantra = 0.0, FVM = 0.0, FVMMantra = 0.0;
                    String role = "", mantraRole = "", surname = "", team = "";
                    int j = 0;
                    for (Cell cell : row) {
                        switch (cell.getCellType()) {
                            case STRING:
                                if(j == 1) role = cell.getStringCellValue();
                                if(j == 2) mantraRole = cell.getStringCellValue();
                                if(j == 3) surname = cell.getStringCellValue();
                                if(j == 4) team = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                if(j == 0) id = cell.getNumericCellValue();
                                if(j == 5) actualValue = cell.getNumericCellValue();
                                if(j == 6) initialValue = cell.getNumericCellValue();
                                if(j == 7) valueDiff = cell.getNumericCellValue();
                                if(j == 8) actualValueMantra = cell.getNumericCellValue();
                                if(j == 9) initialValueMantra = cell.getNumericCellValue();
                                if(j == 10) valueDiffMantra = cell.getNumericCellValue();
                                if(j == 11) FVM = cell.getNumericCellValue();
                                if(j == 12) FVMMantra = cell.getNumericCellValue();
                                break;
                            case BLANK:
                                nextRow = true;
                                break;
                            default:
                                System.out.print("Unknown\t");
                        }
                        j++;
                        if(nextRow) break;
                    }
                    this.footballers.addLast(new Footballer(id,role,mantraRole,surname,
                                                    team,actualValue,initialValue,valueDiff,
                                                    actualValueMantra,initialValueMantra,
                                                    valueDiffMantra,FVM,FVMMantra));
                }
                i++;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addFootballerToAuction(Footballer f){
        this.actualFootballer = f;
        this.footballerLabel.setText(f.getSurname());
        this.startAuction.setEnabled(true);
    }

    public LinkedList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(LinkedList<Player> players) {
        this.players = players;
    }

    public int getOffer() {
        return offer;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }

    public boolean isFirstStartTimer() {
        return firstStartTimer;
    }

    public void setFirstStartTimer(boolean firstStartTimer) {
        this.firstStartTimer = firstStartTimer;
    }

    public JButton getStartAuction() {
        return startAuction;
    }

    public void setStartAuction(JButton startAuction) {
        this.startAuction = startAuction;
    }

    public FantaTimer getFantaTimer() {
        return fantaTimer;
    }

    public void setFantaTimer(FantaTimer fantaTimer) {
        this.fantaTimer = fantaTimer;
    }

    public LinkedList<Footballer> getFootballers() {
        return footballers;
    }

    public void setFootballers(LinkedList<Footballer> footballers) {
        this.footballers = footballers;
    }

    public Player getLastOfferPlayer() {
        return lastOfferPlayer;
    }

    public void setLastOfferPlayer(Player lastOfferPlayer) {
        this.lastOfferPlayer = lastOfferPlayer;
    }

    public Footballer getActualFootballer() {
        return actualFootballer;
    }

    public void setActualFootballer(Footballer actualFootballer) {
        this.actualFootballer = actualFootballer;
    }

    public static void main(String[] args) {
        Board board = new Board("s1-s2-s3-s4-s5-s6-null-null", 6, 500);
    }

    private class BoardKeyboardListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            char keyChar = e.getKeyChar();
            if (keyChar >= '0' && keyChar < '8') {
                int num = Character.getNumericValue(keyChar);
                newOffer(num);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}
        
    }

    private class BoardListener implements ActionListener{

        Board b;

        public BoardListener(Board board) {
            b = board;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == startAuction){
                if(firstStartTimer) {
                    fantaTimer.resetTimer();
                    firstStartTimer = false;
                    startAuction.setEnabled(false);
                    newAuction.setEnabled(false);
                }
            }
            if(e.getSource() == newAuction){
                new SearchingFootballerFrame(footballers, b);
            }
        }
        
    }

}
