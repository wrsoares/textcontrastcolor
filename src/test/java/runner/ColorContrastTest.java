package runner;


import contrast.ColorContrast;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import selenium.AppWeb;

import static selenium.Drivers.getDriver;

public class ColorContrastTest {

    @AfterTest
    public void tearDown() {
        getDriver().quit();
    }

    @Test
    public void contrastTest() throws Exception {
        new AppWeb().setUpDriver();
        getDriver().get("https://www.cesar.school");
        Assert.assertEquals(new ColorContrast().
                executeByCsv("src/test/java/files/cesarschool.csv"), 0);
    }

}
