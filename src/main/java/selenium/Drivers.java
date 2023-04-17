package selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;

public class Drivers {

    static ThreadLocal<WebDriver> driver = new ThreadLocal();
    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver driver) {
        Drivers.driver.set(driver);
    }

    public static void setConfigurationDownload() {
        WebDriverManager.chromedriver().setup();
    }

}
