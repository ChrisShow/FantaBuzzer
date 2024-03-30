package FUNtaSports;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.*;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.*;
import com.pi4j.io.gpio.event.GpioPinListener;
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
    private GpioController gpioController;
    private GpioPinDigitalInput gpio00, gpio01, gpio02, gpio03, gpio04, gpio05, gpio06, gpio07, gpio08, gpio09,
                                gpio10, gpio11, gpio13, gpio14, gpio15, gpio17, gpio18, gpio19, gpio22, gpio23,
                                gpio24, gpio25, gpio26, gpio27;

    private Semaphore semaphore;

    public Board(String stringTeams, int teams, int credits) {

        this.offer = 0;
        this.firstStartTimer = true;
        this.semaphore = new Semaphore(1);

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
        //gpioInit();

        // setVisible di tutti i panel e frame
        controlPanel.setVisible(true);
        playersPanel.setVisible(true);
        timerPanel.setVisible(true);
        add(controlPanel);
        add(playersPanel);
        add(timerPanel);
        setVisible(true);
        
    }
    public void gpioInit(){
        gpioController = GpioFactory.getInstance();
        gpio00 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_DOWN);
        gpio01 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_DOWN);
        gpio02 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);
        gpio03 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_DOWN);
        gpio04 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);
        gpio05 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_DOWN);
        gpio06 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_06, PinPullResistance.PULL_DOWN);
        gpio07 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_07, PinPullResistance.PULL_DOWN);
        gpio08 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_08, PinPullResistance.PULL_DOWN);
        gpio09 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_09, PinPullResistance.PULL_DOWN);
        gpio10 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_10, PinPullResistance.PULL_DOWN);
        gpio11 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_11, PinPullResistance.PULL_DOWN);
        gpio13 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_13, PinPullResistance.PULL_DOWN);
        gpio14 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_14, PinPullResistance.PULL_DOWN);
        gpio15 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_15, PinPullResistance.PULL_DOWN);
        gpio17 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_17, PinPullResistance.PULL_DOWN);
        gpio18 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_18, PinPullResistance.PULL_DOWN);
        gpio19 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_19, PinPullResistance.PULL_DOWN);
        gpio22 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_22, PinPullResistance.PULL_DOWN);
        gpio23 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_23, PinPullResistance.PULL_DOWN);
        gpio24 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_24, PinPullResistance.PULL_DOWN);
        gpio25 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_25, PinPullResistance.PULL_DOWN);
        gpio26 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_26, PinPullResistance.PULL_DOWN);
        gpio27 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_27, PinPullResistance.PULL_DOWN);
        gpio00.addListener(new GpioListener());
        gpio01.addListener(new GpioListener());
        gpio02.addListener(new GpioListener());
        gpio03.addListener(new GpioListener());
        gpio04.addListener(new GpioListener());
        gpio05.addListener(new GpioListener());
        gpio06.addListener(new GpioListener());
        gpio07.addListener(new GpioListener());
        gpio08.addListener(new GpioListener());
        gpio09.addListener(new GpioListener());
        gpio10.addListener(new GpioListener());
        gpio11.addListener(new GpioListener());
        gpio13.addListener(new GpioListener());
        gpio14.addListener(new GpioListener());
        gpio15.addListener(new GpioListener());
        gpio17.addListener(new GpioListener());
        gpio18.addListener(new GpioListener());
        gpio19.addListener(new GpioListener());
        gpio22.addListener(new GpioListener());
        gpio23.addListener(new GpioListener());
        gpio24.addListener(new GpioListener());
        gpio25.addListener(new GpioListener());
        gpio26.addListener(new GpioListener());
        gpio27.addListener(new GpioListener());
    }
    public void auctionTerminated(){
        new EndAuction(lastOfferPlayer, offer, actualFootballer, this);
    }

    private void newOffer(int newOfferValue, int playerIndex){
        if(!firstStartTimer && validOffer(newOfferValue, playerIndex)){
            try{
                this.semaphore.acquire();
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
                this.semaphore.release();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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
                newOffer(num);
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

    private class GpioListener implements GpioPinListenerDigital{

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

    }
}
