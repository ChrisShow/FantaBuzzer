import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FantaTimer extends JPanel implements Runnable{

    private JLabel timer;
    private int secondi;
    private Board board;

    public FantaTimer(int width, int height, Board board){

        setSize(new Dimension(width, height));

        timer = new JLabel("30");
        timer.setFont(new Font("Coolvetica", Font.BOLD, 30));
        add(timer);

        secondi = 30;

    }

    private void updateTimer(){
        if(secondi>0) {
            secondi--;
            timer.setText(String.valueOf(secondi));
        }else{
            this.board.auctionTerminated();
        }
    }

    public void resetTimer(){
        Timer timerClientProperty = (Timer) timer.getClientProperty("timer");
        if (timerClientProperty != null) {
            timerClientProperty.restart();
        } else {
            // Se il timer non è stato associato, creane uno nuovo
            Timer newTimer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateTimer();
                }
            });
            newTimer.setInitialDelay(0);
            newTimer.start();

            // Associa il nuovo timer come client property
            timer.putClientProperty("timer", newTimer);
        }
    }

    @Override
    public void run() {
/*         Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimer();
            }
        });
        timer.setInitialDelay(0);
        timer.start(); */
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimer();
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    
        // Associa il timer come client property
        this.timer.putClientProperty("timer", timer);
    }
}
