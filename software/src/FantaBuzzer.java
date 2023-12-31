import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Dimension;

public class FantaBuzzer extends JFrame{

    public FantaBuzzer(){

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimensioneSchermo = toolkit.getScreenSize();
        int screenWidth = dimensioneSchermo.width;
        int screenHeight = dimensioneSchermo.height;
    }
}