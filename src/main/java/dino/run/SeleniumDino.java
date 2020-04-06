package dino.run;

import dino.geneticalgorithm.sensor.DinoSensor;
import dino.geneticalgorithm.sensor.DinoSensorInteraction;
import dino.geneticalgorithm.sensor.exception.GameOverException;
import org.openqa.selenium.Point;
import org.openqa.selenium.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class SeleniumDino {
    private final WebDriver webDriver;

    public SeleniumDino(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void run() throws Exception {
        try {
            WebElement ele = webDriver.findElement(By.className("runner-canvas"));
            Point point = ele.getLocation();
            webDriver.findElement(By.tagName("body")).sendKeys(Keys.UP);
            Thread.sleep(2000);
            int i = 0;
            do {
                BufferedImage image = ImageIO.read(takeScreenshot(webDriver));
                DinoSensor dinoSensor = new DinoSensorInteraction(image).sensor();
                System.out.println(dinoSensor.distanceFromObject());
                if (dinoSensor.isObjectCloserToTheGround()) {
                    if (performAction(dinoSensor)) {
                        webDriver.findElement(By.tagName("body")).sendKeys(Keys.UP);
                    }
                } else if (dinoSensor.isObjectFlying()) {
                    if (performAction(dinoSensor)) {
                        duck();
                    }
                }
                ImageIO.write(dinoSensor.image(), "png", new File("images/game" + i + ".png"));
                i++;
            } while (Boolean.TRUE);
        } catch (Throwable e) {
            if (e instanceof GameOverException) {
                ImageIO.write(((GameOverException) e).getDinoSensor().image(), "png", new File("images/game.png"));
            }
            e.printStackTrace();
        } finally {
            webDriver.quit();
        }
    }

    private boolean performAction(DinoSensor dinoSensor) {
        return dinoSensor.distanceFromObject() <= 168;
    }

    private void duck() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.delay(500);
        robot.keyRelease(KeyEvent.VK_DOWN);
    }

    private static File takeScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    }
}
