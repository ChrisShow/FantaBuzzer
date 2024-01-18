import java.awt.*;

public final class UtilityClass {

    public static final Color CUSTOM_BLACK = new Color(50, 41, 47);
    public static final Color CUSTOM_ORANGE = new Color(193, 120, 23);
    public static final Color CUSTOM_GREEN = new Color(62, 86, 34);
    public static final Color CUSTOM_WHITE = new Color(230, 225, 197);
    public static final Color CUSTOM_RED = new Color(200, 62, 77);
    public static final Color CUSTOM_LIGHT_BLUE = new Color(67, 124, 144);

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
