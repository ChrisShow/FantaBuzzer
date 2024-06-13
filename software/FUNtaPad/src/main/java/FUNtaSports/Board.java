package FUNtaSports;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import javax.swing.*;
import com.pi4j.Pi4J;
import com.pi4j.boardinfo.util.BoardInfoHelper;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalInputConfigBuilder;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;
import com.pi4j.util.Console;


public class Board extends JFrame {

    private LinkedList<Player> players;
    private int offer;
    private boolean firstStartTimer;
    private JButton newAuction, startAuction, skipTime;
    private FantaTimer fantaTimer;
    private LinkedList<Footballer> footballers;
    private Player lastOfferPlayer;
    private Footballer actualFootballer;
    private JPanel controlPanel, playersPanel, timerPanel;
    private JLabel footballerTextLabel, footballerLabel;
    
    private DigitalInput gpio00, gpio01, gpio02, gpio03, gpio04, gpio05, gpio06, gpio07, gpio08, gpio09,
                         gpio10, gpio11, gpio12, gpio13, gpio14, gpio15, gpio16, gpio21, gpio22, gpio23,
                         gpio24, gpio25, gpio26, gpio27, gpio28, gpio29;

    private static final int PIN00 = 11; // PIN00 = BCM 11
    private static final int PIN01 = 12; // PIN01 = BCM 12
    private static final int PIN02 = 13; // PIN02 = BCM 13
    private static final int PIN03 = 15; // PIN03 = BCM 15
    private static final int PIN04 = 16; // PIN04 = BCM 16
    private static final int PIN05 = 18; // PIN05 = BCM 18
    private static final int PIN06 = 22; // PIN06 = BCM 22
    private static final int PIN07 = 7; // PIN07 = BCM 7
    private static final int PIN08 = 3; // PIN01 = BCM 3
    private static final int PIN09 = 5; // PIN09 = BCM 5
    private static final int PIN10 = 24; // PIN10 = BCM 24
    private static final int PIN11 = 26; // PIN11 = BCM 26
    private static final int PIN12 = 19; // PIN12 = BCM 19
    private static final int PIN13 = 21; // PIN13 = BCM 21
    private static final int PIN14 = 23; // PIN14 = BCM 23
    private static final int PIN15 = 8; // PIN15 = BCM 8
    private static final int PIN16 = 10; // PIN16 = BCM 10
    private static final int PIN21 = 29; // PIN021 = BCM 29
    private static final int PIN22 = 31; // PIN022 = BCM 31
    private static final int PIN23 = 33; // PIN023 = BCM 33
    private static final int PIN24 = 35; // PIN24 = BCM 35
    private static final int PIN25 = 37; // PIN25 = BCM 37
    private static final int PIN26 = 32; // PIN26 = BCM 32
    private static final int PIN27 = 36; // PIN27 = BCM 36
    private static final int PIN28 = 38; // PIN28 = BCM 38
    private static final int PIN29 = 40; // PIN29 = BCM 40
    private Context pi4j;

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
        //addKeyListener(new BoardKeyboardListener());
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

        int skipTimeWidth = fantaTimerWidth, skipTimeHeight = fantaTimerHeight / 2;
        skipTime = new JButton("Skip »");
        skipTime.setFont(UtilityClass.caricaFont(25));
        skipTime.addActionListener(new BoardListener(this));
        skipTime.setFocusable(false);
        skipTime.setBounds(controlPanelWidth - fantaTimerWidth - distance, (timerPanelHeight - skipTimeHeight) / 2, skipTimeWidth, skipTimeHeight);
        skipTime.setVisible(true);
        skipTime.setEnabled(false);
        timerPanel.add(skipTime);

        //Preleva i gicatori dal listone e li salva come oggetti
        this.footballers = UtilityClass.initFootballers();
        this.actualFootballer = footballers.removeFirst();

        // Impostazione pin RaspBerry
        gpioInit();

        // setVisible di tutti i panel e frame
        controlPanel.setVisible(true);
        playersPanel.setVisible(true);
        timerPanel.setVisible(true);
        add(controlPanel);
        add(playersPanel);
        add(timerPanel);
        setVisible(true);
        
    }
    
    private void gpioInit(){
        // Inizializzo il contesto della libreria PI4J
        pi4j = Pi4J.newAutoContext();

        // Creo oggetto pin00
        DigitalInputConfigBuilder digitalInputConfigBuilder00 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin00")
            .name("GPIO00")
            .address(PIN00)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio00 = pi4j.create(digitalInputConfigBuilder00);

        // Creo oggetto pin01
        DigitalInputConfigBuilder digitalInputConfigBuilder01 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin01")
            .name("GPIO01")
            .address(PIN01)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio01 = pi4j.create(digitalInputConfigBuilder01);
        
        // Creo oggetto pin02
        DigitalInputConfigBuilder digitalInputConfigBuilder02 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin02")
            .name("GPIO02")
            .address(PIN02)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio02 = pi4j.create(digitalInputConfigBuilder02);

        // Creo oggetto pin03
        DigitalInputConfigBuilder digitalInputConfigBuilder03 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin03")
            .name("GPIO03")
            .address(PIN03)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio03 = pi4j.create(digitalInputConfigBuilder03);

        // Creo oggetto pin04
        DigitalInputConfigBuilder digitalInputConfigBuilder04 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin04")
            .name("GPIO04")
            .address(PIN04)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio04 = pi4j.create(digitalInputConfigBuilder04);

        // Creo oggetto pin05
        DigitalInputConfigBuilder digitalInputConfigBuilder05 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin05")
            .name("GPIO05")
            .address(PIN05)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio05 = pi4j.create(digitalInputConfigBuilder05);

        // Creo oggetto pin06
        DigitalInputConfigBuilder digitalInputConfigBuilder06 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin06")
            .name("GPIO06")
            .address(PIN06)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio06 = pi4j.create(digitalInputConfigBuilder06);

        // Creo oggetto pin07
        DigitalInputConfigBuilder digitalInputConfigBuilder07 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin07")
            .name("GPIO07")
            .address(PIN07)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio07 = pi4j.create(digitalInputConfigBuilder07);

        // Creo oggetto pin08
        DigitalInputConfigBuilder digitalInputConfigBuilder08 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin08")
            .name("GPIO08")
            .address(PIN08)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio08 = pi4j.create(digitalInputConfigBuilder08);

        // Creo oggetto pin09
        DigitalInputConfigBuilder digitalInputConfigBuilder09 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin09")
            .name("GPIO09")
            .address(PIN09)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio09 = pi4j.create(digitalInputConfigBuilder09);

        // Creo oggetto pin10
        DigitalInputConfigBuilder digitalInputConfigBuilder10 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin10")
            .name("GPIO10")
            .address(PIN10)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio10 = pi4j.create(digitalInputConfigBuilder10);

        // Creo oggetto pin11
        DigitalInputConfigBuilder digitalInputConfigBuilder11 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin11")
            .name("GPIO11")
            .address(PIN11)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio11 = pi4j.create(digitalInputConfigBuilder11);

        // Creo oggetto pin12
        DigitalInputConfigBuilder digitalInputConfigBuilder12 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin12")
            .name("GPIO12")
            .address(PIN12)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio12 = pi4j.create(digitalInputConfigBuilder12);

        // Creo oggetto pin13
        DigitalInputConfigBuilder digitalInputConfigBuilder13 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin13")
            .name("GPIO13")
            .address(PIN13)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio13 = pi4j.create(digitalInputConfigBuilder13);

        // Creo oggetto pin14
        DigitalInputConfigBuilder digitalInputConfigBuilder14 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin14")
            .name("GPIO14")
            .address(PIN14)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio14 = pi4j.create(digitalInputConfigBuilder14);

        // Creo oggetto pin15
        DigitalInputConfigBuilder digitalInputConfigBuilder15 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin15")
            .name("GPIO15")
            .address(PIN15)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio15 = pi4j.create(digitalInputConfigBuilder15);

        // Creo oggetto pin16
        DigitalInputConfigBuilder digitalInputConfigBuilder16 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin16")
            .name("GPIO16")
            .address(PIN16)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio16 = pi4j.create(digitalInputConfigBuilder16);

        // Creo oggetto pin21
        DigitalInputConfigBuilder digitalInputConfigBuilder21 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin21")
            .name("GPIO21")
            .address(PIN21)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio21 = pi4j.create(digitalInputConfigBuilder21);

        // Creo oggetto pin22
        DigitalInputConfigBuilder digitalInputConfigBuilder22 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin22")
            .name("GPIO22")
            .address(PIN22)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio22 = pi4j.create(digitalInputConfigBuilder22);

        // Creo oggetto pin23
        DigitalInputConfigBuilder digitalInputConfigBuilder23 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin23")
            .name("GPIO23")
            .address(PIN23)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio23 = pi4j.create(digitalInputConfigBuilder23);

        // Creo oggetto pin24
        DigitalInputConfigBuilder digitalInputConfigBuilder24 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin24")
            .name("GPIO24")
            .address(PIN24)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio24 = pi4j.create(digitalInputConfigBuilder24);

        // Creo oggetto pin25
        DigitalInputConfigBuilder digitalInputConfigBuilder25 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin25")
            .name("GPIO25")
            .address(PIN25)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio25 = pi4j.create(digitalInputConfigBuilder25);

        // Creo oggetto pin26
        DigitalInputConfigBuilder digitalInputConfigBuilder26 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin26")
            .name("GPIO26")
            .address(PIN26)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio26 = pi4j.create(digitalInputConfigBuilder26);

        // Creo oggetto pin27
        DigitalInputConfigBuilder digitalInputConfigBuilder27 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin27")
            .name("GPIO27")
            .address(PIN27)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio27 = pi4j.create(digitalInputConfigBuilder27);

        // Creo oggetto pin28
        DigitalInputConfigBuilder digitalInputConfigBuilder28 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin28")
            .name("GPIO28")
            .address(PIN28)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio28 = pi4j.create(digitalInputConfigBuilder28);

        // Creo oggetto pin29
        DigitalInputConfigBuilder digitalInputConfigBuilder29 = DigitalInput.newConfigBuilder(pi4j)
            .id("pin29")
            .name("GPIO29")
            .address(PIN29)
            .pull(PullResistance.PULL_DOWN)
            .debounce(1000L);
        gpio29 = pi4j.create(digitalInputConfigBuilder29);


        /* 
        gpioController = GpioFactory.getInstance();
        gpio00 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_DOWN);
        gpio00.addListener(new GpioListener());
        */
    }
    public void auctionTerminated(){
        new EndAuction(lastOfferPlayer, offer, actualFootballer, this);
    }

    private void newOffer(int newOfferValue, int playerIndex){
        if(!firstStartTimer && validOffer(newOfferValue, playerIndex)){
            int i = 0;
            for (Player player : players) {
                if(i == playerIndex){
                    lastOfferPlayer = player;
                    this.fantaTimer.resetTimer();
                    this.offer += newOfferValue;
                    player.newOffer(this.offer);
                }
                else {
                    if(player != null)
                        player.cancelOffer();
                }
                i++;
            }
        }
    }

    private boolean validOffer(int newOfferValue, int playerIndex) {
        if(players.get(playerIndex) == null || players.get(playerIndex) == lastOfferPlayer || players.get(playerIndex).getCredits() < newOfferValue) return false;
        if(actualFootballer.isGoalkeeper() && players.get(playerIndex).howManyGK() > 2) return false;
        else if (actualFootballer.isDefender() && players.get(playerIndex).howManyDef() > 7) return false;
        else if (actualFootballer.isMidfielder() && players.get(playerIndex).howManyMid() > 7) return false;
        else if(actualFootballer.isAttacker() && players.get(playerIndex).howManyAtt() > 5) return false;
        return true;
    }

    public void resetAuction(Player player, int offer, Footballer footballer) {
        this.firstStartTimer = !this.firstStartTimer;
        if(player != null){
            player.newFootballerBougth(footballer, offer);
            footballers.remove(actualFootballer);
        }
        lastOfferPlayer = null;
        actualFootballer = null;
        footballerLabel.setText("");
        firstStartTimer = true;
        startAuction.setEnabled(false);
        skipTime.setEnabled(false);
        newAuction.setEnabled(true);
        for(Player p: players)
            if(p != null)
                p.cancelOffer();
        this.offer = 0;
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

//    public static void main(String[] args) {
//        Board board = new Board("s1-s2-s3-s4-s5-s6-null-null", 6, 500);
//    }

    /*
    private class BoardKeyboardListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            char keyChar = e.getKeyChar();
            if (keyChar >= '0' && keyChar < '8') {
                int num = Character.getNumericValue(keyChar);
                newOffer(num,2);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}
        
    }
    */

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
                    skipTime.setEnabled(true);
                    newAuction.setEnabled(false);
                }
            }
            if(e.getSource() == newAuction){
                new SearchingFootballerFrame(footballers, b);
            }
            if(e.getSource() == skipTime){
                skipTime.setEnabled(false);
                fantaTimer.setSecondi(1);
                firstStartTimer = true;
            }
        }
        
    }

    /*private class GpioListener implements GpioPinListenerDigital{

        @Override
        public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent gpioPinDigitalStateChangeEvent) {
            if(gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH && gpioPinDigitalStateChangeEvent.getSource() == gpio00)
                newOffer(1,6);
            if(gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH && gpioPinDigitalStateChangeEvent.getSource() == gpio01)
                newOffer(10,5);
            if(gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH && gpioPinDigitalStateChangeEvent.getSource() == gpio02)
                newOffer(1,0);
            if(gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH && gpioPinDigitalStateChangeEvent.getSource() == gpio03)
                newOffer(5,0);
            if(gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH && gpioPinDigitalStateChangeEvent.getSource() == gpio04)
                newOffer(10,0);
            if(gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH && gpioPinDigitalStateChangeEvent.getSource() == gpio05)
                newOffer(5,6);
            if(gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH && gpioPinDigitalStateChangeEvent.getSource() == gpio06)
                newOffer(10,6);
            if(gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH && gpioPinDigitalStateChangeEvent.getSource() == gpio07)
                newOffer(5,5);
            if(gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH && gpioPinDigitalStateChangeEvent.getSource() == gpio08)
                newOffer(1,5);
            if(gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH && gpioPinDigitalStateChangeEvent.getSource() == gpio09)
                newOffer(5,2);
            if(gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH && gpioPinDigitalStateChangeEvent.getSource() == gpio10)
                newOffer(1,2);
            if(gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH && gpioPinDigitalStateChangeEvent.getSource() == gpio11)
                newOffer(10,2);
            if(gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH && gpioPinDigitalStateChangeEvent.getSource() == gpio13)
                newOffer(1,7);
            if(gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH && gpioPinDigitalStateChangeEvent.getSource() == gpio14)
                newOffer(1,3);
            if(gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH && gpioPinDigitalStateChangeEvent.getSource() == gpio15)
                newOffer(5,3);
            if(gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH && gpioPinDigitalStateChangeEvent.getSource() == gpio17)
                newOffer(1,1);
            if(gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH && gpioPinDigitalStateChangeEvent.getSource() == gpio18)
                newOffer(10,3);
            if(gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH && gpioPinDigitalStateChangeEvent.getSource() == gpio19)
                newOffer(5,7);
            if(gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH && gpioPinDigitalStateChangeEvent.getSource() == gpio22)
                newOffer(10,1);
            if(gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH && gpioPinDigitalStateChangeEvent.getSource() == gpio23)
                newOffer(1,4);
            if(gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH && gpioPinDigitalStateChangeEvent.getSource() == gpio24)
                newOffer(5,4);
            if(gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH && gpioPinDigitalStateChangeEvent.getSource() == gpio25)
                newOffer(10,4);
            if(gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH && gpioPinDigitalStateChangeEvent.getSource() == gpio26)
                newOffer(10,7);
            if(gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH && gpioPinDigitalStateChangeEvent.getSource() == gpio27)
                newOffer(5,1);

        }

    }*/
}
