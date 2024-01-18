import jdk.jshell.execution.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FantaTimer extends JPanel implements Runnable{

    private JLabel timer;
    private int secondi;
    private Board board;
    private Timer timerInstance;
    private final Color initialBackgroundColor = UtilityClass.CUSTOM_GREEN;
    private final Color targetBackgroundColor = UtilityClass.CUSTOM_RED;
    private final Color intermediateBackgroundColor = UtilityClass.CUSTOM_ORANGE;

    public FantaTimer(int width, int height, Board board) {

        this.board = board;

        setSize(new Dimension(width, height));
        setBorder(BorderFactory.createLineBorder(Color.black, 3));
        setBackground(UtilityClass.CUSTOM_GREEN);
        setLayout(null);

        timer = new JLabel("30");
        timer.setBounds(0, 0, width, height);
        timer.setHorizontalAlignment(SwingConstants.CENTER);
        timer.setVerticalAlignment(SwingConstants.CENTER);
        timer.setFont(UtilityClass.caricaFont(70));
        timer.setForeground(Color.WHITE);
        add(timer);

        secondi = 30;

        // Crea il timer nel costruttore e associalo come client property
        timerInstance = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimer();
            }
        });
        timerInstance.setInitialDelay(0);
        timer.putClientProperty("timer", timerInstance);
    }

    private void updateTimer() {
        if (secondi > 0) {
            secondi--;
            timer.setText(String.valueOf(secondi));
            Color newBG;
            if(secondi > 15){
                double progress = (double) (secondi - 15) / 15;
                int r = (int) (intermediateBackgroundColor.getRed() + progress * (initialBackgroundColor.getRed() - intermediateBackgroundColor.getRed()));
                int g = (int) (intermediateBackgroundColor.getGreen() + progress * (initialBackgroundColor.getGreen() - intermediateBackgroundColor.getGreen()));
                int b = (int) (intermediateBackgroundColor.getBlue() + progress * (initialBackgroundColor.getBlue() - intermediateBackgroundColor.getBlue()));
                newBG = new Color(r, g, b);
            }
            else if(secondi == 15)
                newBG = intermediateBackgroundColor;
            else{
                double progress = (double) secondi / 15;
                int r = (int) (targetBackgroundColor.getRed() + progress * (intermediateBackgroundColor.getRed() - targetBackgroundColor.getRed()));
                int g = (int) (targetBackgroundColor.getGreen() + progress * (intermediateBackgroundColor.getGreen() - targetBackgroundColor.getGreen()));
                int b = (int) (targetBackgroundColor.getBlue() + progress * (intermediateBackgroundColor.getBlue() - targetBackgroundColor.getBlue()));
                newBG = new Color(r, g, b);
            }
            // Cambia gradualmente il colore del background
            setBackground(newBG);
        } else {
            timerInstance.stop();
            this.board.auctionTerminated();
        }
    }

    public void resetTimer() {
        if (timerInstance != null) {
            secondi = 30;
            timerInstance.restart();
        }
    }

    @Override
    public void run() {}
}
