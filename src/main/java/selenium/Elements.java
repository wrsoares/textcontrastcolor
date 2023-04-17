package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.Color;

import static selenium.Drivers.getDriver;

public class Elements {

    WebDriver driver;

    public Elements() {
        driver = getDriver();
    }

    public boolean isBold(By by) {
        return getFontWeight(by).equalsIgnoreCase("700");
    }

    public String getFontWeight(By by) {
        return driver.findElement(by).getCssValue("font-weight");
    }

    public int getFontSize(By by) {
        return Integer.parseInt(driver.findElement(by).getCssValue("font-size")
                .replaceAll("[a-zA-Z]", ""));
    }

    public String[] getElementColor(By by, String property) {
        String rgb = Color.fromString(driver.findElement(by).getCssValue(property)).asRgb();
        return rgb.substring(rgb.indexOf("(")+1, rgb.indexOf(")")).split(",");
    }
    public String getDescription(By by) {
        return driver.findElement(by).getText();
    }
}
