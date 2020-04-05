package integration.tests;

import dino.geneticalgorithm.sensor.DinoSensor;
import dino.geneticalgorithm.sensor.DinoSensorInteraction;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RunChrome {

    public static void main(String[] args) throws IOException, InterruptedException {

        System.setProperty("webdriver.gecko.driver", "/usr/bin/chromedriver");
        WebDriver webDriver = new ChromeDriver();
        String resource = RunChrome.class.getResource("/T-RexGame.html").toExternalForm().replace(":/", ":///");
        webDriver.get(resource);
        Thread.sleep(1000);
        try {
            WebElement ele = webDriver.findElement(By.className("runner-canvas"));
            Point point = ele.getLocation();
            webDriver.findElement(By.id("t")).sendKeys(Keys.UP);
            int i = 0;
            do {
                BufferedImage image = ImageIO.read(takeScreenshot(webDriver)).getSubimage(point.getX(), point.getY(), ele.getSize().getWidth(), ele.getSize().getHeight());
                DinoSensor dinoSensor = new DinoSensorInteraction(image).sensor();
                if (dinoSensor.isAnyObjectFound()) {
                    if (dinoSensor.distanceFromObject() <= 90) {
                        webDriver.findElement(By.id("t")).sendKeys(Keys.UP);
                    }
                }
                ImageIO.write(dinoSensor.image(), "png", new File("images/game" + i + ".png"));
                i++;
            } while (Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            webDriver.quit();
        }
    }

    private static File takeScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    }
}