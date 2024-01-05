import javax.swing.*;

public class Player extends JPanel {

    private String nomePlayer;
    private int crediti;

    //Costruttore
    public Player(String nomePlayer, int crediti){
        this.nomePlayer=nomePlayer;
        this.crediti=crediti;
    }

    //Getter e Setter per il nome del giocatore
    public String getNomePlayer(){
        return nomePlayer;
    }
    public void setNomePlayer(String nomePlayer){
        this.nomePlayer=nomePlayer;
    }

    //Getter e Setter per i crediti del giocatore
    public int getCrediti(){
        return crediti;
    }

    public void setCrediti(int crediti) {
        this.crediti = crediti;
    }
}
