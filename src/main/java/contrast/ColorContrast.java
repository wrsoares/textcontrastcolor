package contrast;

import com.opencsv.CSVReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import selenium.Elements;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static selenium.Drivers.getDriver;

public class ColorContrast {

    WebDriver driver;
    Elements elements;
    public ColorContrast () {
        this.driver = getDriver();
        this.elements = new Elements();
    }

    public float contrastRatio(String[] color1, String[] color2) {
        float tempColor1 = luminosityCalculation(color1);
        float tempColor2 = luminosityCalculation(color2);
        float dark, light;
        if (tempColor1 > tempColor2) {
            dark = tempColor1;
            light = tempColor2;
        } else {
            dark = tempColor2;
            light = tempColor1;
        }
        return (dark + 0.05f) / (light + 0.05f);
    }

    public float luminosityCalculation(String[] color) {
        List<Float> rgb = new ArrayList();
        rgb.add(((float) Integer.parseInt(color[0].trim())/255));
        rgb.add(((float) Integer.parseInt(color[1].trim())/255));
        rgb.add(((float) Integer.parseInt(color[2].trim())/255));

        List<Float> colors = new ArrayList<Float>();
        for (float rgbColor: rgb
        ) {
            if (rgbColor <= 0.03928) {
                colors.add((float) (rgbColor/12.92));
            } else {
                colors.add((float) Math.pow(((rgbColor + 0.055)/1.055), 2.4));
            }
        }
        float r = (colors.get(0) * 0.2126f);
        float g = (colors.get(1) * 0.7152f);
        float b = (colors.get(2) * 0.0722f);

        float luminosity = r + g + b;
        return luminosity;
    }

    public boolean compatibleFontWeight(By by) {
        boolean isCompatible = false;
        String fontWeight = elements.getFontWeight(by);
        if (elements.getFontSize(by) < 19) {
            if (elements.isBold(by)) {
                if (fontWeight.equalsIgnoreCase("700")) {
                    isCompatible = true;
                } else {
                    System.err.println("Font-weight must be 700");
                }
            } else {
                if (fontWeight.equalsIgnoreCase("400")) {
                    isCompatible = true;
                } else {
                    System.err.println("Font-weight must be 400");
                }
            }
        }
        return isCompatible;
    }

    public boolean textColorContrast(By byBackground, String backgroundProperty,
                                     By byText, String textProperty) {
        boolean flag =false;
        float contrastRatio = contrastRatio(elements.getElementColor(byBackground,
                        backgroundProperty),
                elements.getElementColor(byText, textProperty));
        System.out.println("RATIO: " + contrastRatio);
        int fontSize = elements.getFontSize(byText);
        if (fontSize >= 24) {
            if (contrastRatio >= 3.0) {
                flag = true;
            } else {
                System.err.println("Contrast ratio must be bigger than or equal to 3.0");
            }
        } else if (fontSize >= 19 && elements.isBold(byText)) {
            if (contrastRatio >= 3.0) {
                flag = true;
            } else {
                System.err.println("Contrast ratio must be bigger than or equal to 3.0");
            }
        } else if (fontSize < 19) {
            if (contrastRatio >= 4.5 && compatibleFontWeight(byText)) {
                flag = true;
            } else {
                System.err.println("Contrast ratio must be bigger than 4.5");
            }
        }
        return flag;
    }

    public int executeByCsv(String csvPath) throws Exception {
        int count = 0;
        try (CSVReader reader = new CSVReader(new FileReader(csvPath))) {
            List<String[]> r = reader.readAll();
            for (String[] selectors:r
                 ) {
                String backgroundSelector = selectors[0];
                String textSelector = selectors[1];
                System.out.println("-------------------------------------------------");
                System.out.println("Background selector: " + backgroundSelector);
                System.out.println("Text selector: " + textSelector);
                System.out.println("Text description: " + elements.getDescription(
                        By.cssSelector(textSelector)));
                if (!textColorContrast(By.cssSelector(backgroundSelector), "background-color",
                        By.cssSelector(textSelector), "color")) {
                    count++;
                }
            }
        }
        return count;
    }
}
