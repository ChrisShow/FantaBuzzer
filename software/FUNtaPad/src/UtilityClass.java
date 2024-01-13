import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GraphicsEnvironment;

import javax.swing.JLabel;

public final class UtilityClass {

    public static Font caricaFont(int size) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new java.io.File("software/FUNtaPad/resources/fonts/coolveticaRG.otf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            return font.deriveFont(Font.PLAIN, size);
        } catch (java.awt.FontFormatException | java.io.IOException e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, size);
        }
    }
    
}
