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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Board extends JFrame {

    private LinkedList<Player> players;
    private int offer;
    private boolean firstStartTimer;
    private JButton startAuction;
    private FantaTimer fantaTimer;
    private LinkedList<Footballer> footballers;
    private Player lastOfferPlayer;
    private Footballer actualFootballer;

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
        int playerWidth = (int) ((screenWidth - (distance * ((teams / 2) + 1))) / (teams / 2));
        int playerHeight = (int) ((screenHeight - distance) / 4);

        players = new LinkedList<>();
        String[] strings = stringTeams.split("-");
        int j = 0;
        for (int i = 0; i < strings.length; i++) {
            if(!strings[i].equals("null")){
                Player p = new Player(strings[i], credits, i, playerWidth, playerHeight, this);
                int currentPlayerX = (distance * ((j % (teams / 2)) + 1)) + (playerWidth * (j % (teams / 2)));
                int currentPlayerY = (playerHeight + distance) + ((distance + playerHeight) * ((int) j / (teams / 2)));
                p.setBounds(currentPlayerX, currentPlayerY, playerWidth, playerHeight);
                add(p);
                p.setVisible(true);
                players.addLast(p);
                j++;
            }
            else
                players.addLast(null);
        }

        int startAuctionWidth = 200, startAuctionHeight = 100;
        startAuction = new JButton("Inizia Asta");
        startAuction.setFont(UtilityClass.caricaFont(25));
        startAuction.addActionListener(new BoardListener());
        startAuction.setFocusable(false);
        startAuction.setBounds((int)(screenWidth - startAuctionWidth) / 2, (distance * 5), startAuctionWidth, startAuctionHeight);
        startAuction.setVisible(true);
        add(startAuction);

        int fantaTimerWidth = 150, fantaTimerHeight = 150;
        fantaTimer = new FantaTimer(fantaTimerWidth, fantaTimerHeight, this);
        fantaTimer.setBounds((int)(screenWidth - fantaTimerWidth) / 2, (760), fantaTimerWidth, fantaTimerHeight);
        fantaTimer.setVisible(true);
        add(fantaTimer);

        //Preleva i gicatori dal listone e li salva come oggetti
        initFootballers();
        this.actualFootballer = footballers.removeFirst();
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
        actualFootballer = footballers.removeFirst();
        firstStartTimer = true;
        startAuction.setEnabled(true);
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
//                System.out.println(num);
                newOffer(num);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}
        
    }

    private class BoardListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == startAuction){
                if(firstStartTimer) {
                    fantaTimer.resetTimer();
                    firstStartTimer = false;
                    startAuction.setEnabled(false);
                }
            }
        }
        
    }

}
