import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FantaTimer extends JPanel implements Runnable{

    private JLabel timer;
    private int secondi;
    private Board board;
    private Timer timerInstance;

    public FantaTimer(int width, int height, Board board) {

        this.board = board;

        setSize(new Dimension(width, height));

        timer = new JLabel("30");
        timer.setFont(new Font("Coolvetica", Font.BOLD, 30));
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
