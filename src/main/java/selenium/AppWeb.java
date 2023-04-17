package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static selenium.Drivers.setConfigurationDownload;
import static selenium.Drivers.setDriver;

public class AppWeb {

    WebDriver driver;
    public void setUpDriver() {
        setConfigurationDownload();
        initChrome();
    }

    public void initChrome() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        setDriver(driver);
    }

}
