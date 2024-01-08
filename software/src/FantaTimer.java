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
        secondi=30;
        timer.setText(String.valueOf(secondi));
        ((Timer) (timer.getClientProperty("timer"))).restart();
    }

    @Override
    public void run() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimer();
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }
}
