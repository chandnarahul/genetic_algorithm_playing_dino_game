package integration.tests;

import org.openqa.selenium.Keys;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class SeleniumFirefoxKeyCodeCheck {

    public static void main(String[] args) throws Exception {
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        //firefoxBinary.addCommandLineOptions("--headless");
        firefoxBinary.addCommandLineOptions("--no-sandbox");
        System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);
        FirefoxDriver driver = new FirefoxDriver(firefoxOptions);
        String resource = RunFirefox.class.getResource("/JavaScriptEventKeyCodes.html").toExternalForm().replace(":/", ":///");
        try {
            driver.get(resource);
            Thread.sleep(1000);
            driver.findElementByTagName("body").sendKeys(Keys.SPACE);
            Thread.sleep(2000);
            driver.findElementByTagName("body").sendKeys(Keys.UP);
            Thread.sleep(2000);
            driver.findElementByTagName("body").sendKeys(Keys.DOWN);
            Thread.sleep(10000);
        } finally {
            driver.quit();
        }
    }
}
