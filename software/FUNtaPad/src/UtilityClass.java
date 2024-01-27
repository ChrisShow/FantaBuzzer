import org.apache.poi.ss.usermodel.*;

import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;

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

    public static LinkedList<Footballer> initFootballers(){
        LinkedList<Footballer> footballers = new LinkedList<>();
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
                    footballers.addLast(new Footballer(id,role,mantraRole,surname,
                            team,actualValue,initialValue,valueDiff,
                            actualValueMantra,initialValueMantra,
                            valueDiffMantra,FVM,FVMMantra));
                }
                i++;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return footballers;
    }
}
