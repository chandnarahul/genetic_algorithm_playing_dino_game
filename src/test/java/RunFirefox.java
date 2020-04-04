import dino.geneticalgorithm.sensor.DinoSensorInteraction;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

public class RunFirefox {

    public static void main(String[] args) throws IOException, InterruptedException {

        FirefoxBinary firefoxBinary = new FirefoxBinary();
        //firefoxBinary.addCommandLineOptions("--headless");
        firefoxBinary.addCommandLineOptions("--no-sandbox");
        System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);
        FirefoxDriver driver = new FirefoxDriver(firefoxOptions);
        String resource = RunFirefox.class.getResource("/T-RexGame.html").toExternalForm().replace(":/", ":///");
        driver.get(resource);
        Thread.sleep(1000);
        try {
            WebElement ele = driver.findElement(By.id("gamecanvas"));
            Point point = ele.getLocation();
            driver.findElementById("t").sendKeys(Keys.SPACE);
            for (int i = 0; i < 100; i++) {
                BufferedImage image = ImageIO.read(takeScreenshot(driver)).getSubimage(point.getX(), point.getY(), ele.getSize().getWidth(), ele.getSize().getHeight());
                new DinoSensorInteraction(image).sensor();
                final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
                ImageIO.write(image, "png", new File("images/game" + i + ".png"));
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private static File takeScreenshot(TakesScreenshot driver) {
        return driver.getScreenshotAs(OutputType.FILE);
    }
}