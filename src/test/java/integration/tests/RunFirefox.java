package integration.tests;

import dino.geneticalgorithm.sensor.DinoSensor;
import dino.geneticalgorithm.sensor.DinoSensorInteraction;
import dino.geneticalgorithm.sensor.exception.GameOverException;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
            driver.findElementByTagName("body").sendKeys(Keys.UP);
            int i = 0;
            do {
                BufferedImage image = ImageIO.read(takeScreenshot(driver)).getSubimage(point.getX(), point.getY(), ele.getSize().getWidth(), ele.getSize().getHeight());
                DinoSensor dinoSensor = new DinoSensorInteraction(image).sensor();
                if (dinoSensor.isAnyObjectFound()) {
                    if (dinoSensor.distanceFromObject() <= 90) {
                        driver.findElementByTagName("body").sendKeys(Keys.UP);
                    }
                }else{
                    driver.findElementByTagName("body").sendKeys(Keys.DOWN);
                }
                //ImageIO.write(dinoSensor.image(), "png", new File("images/game" + i + ".png"));
                //i++;
            } while (Boolean.TRUE);
        } catch (Throwable e) {
            if (e instanceof GameOverException) {
                ImageIO.write(((GameOverException) e).getDinoSensor().image(), "png", new File("images/game.png"));
            }
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private static File takeScreenshot(TakesScreenshot driver) {
        return driver.getScreenshotAs(OutputType.FILE);
    }
}