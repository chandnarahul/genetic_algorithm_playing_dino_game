import dino.geneticalgorithm.sensor.DinoSensor;
import dino.geneticalgorithm.sensor.DinoSensorInteraction;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

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
            driver.findElementById("t").sendKeys(Keys.UP);
            int i = 0;
            do {
                BufferedImage image = ImageIO.read(takeScreenshot(driver)).getSubimage(point.getX(), point.getY(), ele.getSize().getWidth(), ele.getSize().getHeight());
                DinoSensor dinoSensor = new DinoSensorInteraction(image).sensor();
                if (dinoSensor.isObjectCloserToTheGround()) {
                    if (dinoSensor.distanceFromObject() <= 80) {
                        driver.findElementById("t").sendKeys(Keys.SPACE, Keys.SPACE, Keys.SPACE, Keys.SPACE);
                    }
                }else if(dinoSensor.isObjectFlying()){
                }
                //ImageIO.write(dinoSensor.image(), "png", new File("images/game" + i + ".png"));
                //i++;
            } while (Boolean.TRUE);
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