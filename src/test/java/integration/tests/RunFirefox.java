package integration.tests;

import dino.run.SeleniumDino;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class RunFirefox {

    public static void main(String[] args) throws Exception {
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        //firefoxBinary.addCommandLineOptions("--headless");
        firefoxBinary.addCommandLineOptions("--no-sandbox");
        System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);
        WebDriver webDriver = new FirefoxDriver(firefoxOptions);
        String resource = RunFirefox.class.getResource("/T-RexGame.html").toExternalForm().replace(":/", ":///");
        webDriver.get(resource);
        Thread.sleep(1000);
        new SeleniumDino(webDriver).run();

    }

}