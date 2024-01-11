import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class EndAuction extends JFrame{

    private JButton back;
    private JLabel backgroundLabel;
    private JLabel winLabel;
    private Board board;
    private Player player;
    private int offer;
    private Footballer footballer;

    public EndAuction(Player player, int offer, Footballer footballer, Board board) {

        this.player = player;
        this.offer = offer;
        this.footballer = footballer;
        this.board = board;

        // Ottieni le dimensioni dello schermo
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();

        int width = 600, height = 600;    
        int backWidth = 150, backHeight = 50, distanceFromLabel = 100; 
        int winLabelHeight = 300, winLabelY = 50;

        // Impostare la finestra principale
        setBounds((int)(screenWidth - width) / 2, (int)(screenHeight - height) / 2, width, height);
        setResizable(false);
        setTitle("Asta vinta da " + this.player.getName());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(null);

        // Caricare l'immagine GIF come sfondo animato
        ImageIcon backgroundIcon = new ImageIcon("software\\resources\\images\\animated_background.gif");
        backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, width, height);

        String s =  "<html><center><font color='#C17817'>" + this.player.getName() + " </font><br>" +
                    "acquisisce le prestazioni<br>sportive di<br><font color='#C17817'>" +
                    this.footballer.getName() + "</font><br>per <font color='#C17817'>" + offer + 
                    " </font>crediti</center></html>";

        winLabel = new JLabel(s);
        winLabel.setBounds(0, winLabelY, width, winLabelHeight);
        winLabel.setFont(UtilityClass.caricaFont(50));
        winLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winLabel.setVerticalAlignment(SwingConstants.CENTER);
        winLabel.setForeground(new Color(230, 225, 197));

        back = new JButton("Chiudi");
        back.setBounds((width - backWidth) / 2, (winLabelY + winLabelHeight + distanceFromLabel), backWidth, backHeight);
        back.setFont(UtilityClass.caricaFont(30));
        back.setForeground(Color.BLACK);
        back.setBackground(new Color(62, 86, 34));
        back.setFocusable(false);
        back.setBorder(BorderFactory.createEtchedBorder());
        back.setVisible(true);

        // Aggiungere al frame
        add(back);
        add(winLabel);
        add(backgroundLabel);
        setVisible(true);
    }

    private class EndAuctionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == back){
                dispose();
                board.resetAuction(player, offer, footballer);
            }
        }
        
    }
    
}
