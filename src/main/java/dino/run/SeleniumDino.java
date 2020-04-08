package dino.run;

import dino.DinoConstants;
import dino.geneticalgorithm.sensor.DinoSensor;
import dino.geneticalgorithm.sensor.DinoSensorInteraction;
import org.openqa.selenium.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SeleniumDino {
    private final WebDriver webDriver;

    public SeleniumDino(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void run() throws Exception {
        try {
            WebElement ele = webDriver.findElement(By.className("runner-canvas"));
            webDriver.findElement(By.tagName("body")).sendKeys(Keys.UP);
            Thread.sleep(2000);
            int i = 0;
            do {
                BufferedImage image = ImageIO.read(takeScreenshot(webDriver));
                DinoSensor dinoSensor = new DinoSensorInteraction(image).sensor();
                if (dinoSensor.isObjectCloserToTheGround()) {
                    if (performGroundAction(dinoSensor)) {
                        webDriver.findElement(By.tagName("body")).sendKeys(Keys.UP);
                    }
                    writeDebugImages(i, dinoSensor, "images/game");
                } else if (dinoSensor.isObjectFlying()) {
                    if (performFlyingAction(dinoSensor)) {
                        duckFromFlyingDuck();
                    }
                    writeDebugImages(i, dinoSensor, "images/duck_game");
                }
                i++;
            } while (Boolean.TRUE);
        } catch (Throwable e) {
        } finally {
            webDriver.quit();
        }
    }

    private void writeDebugImages(int i, DinoSensor dinoSensor, String fileName) throws IOException {
        if (DinoConstants.IN_DEBUG_MODE) {
            ImageIO.write(dinoSensor.image(), "png", new File(fileName + i + ".png"));
        }
    }

    private boolean performFlyingAction(DinoSensor dinoSensor) {
        return dinoSensor.distanceFromObject() <= 180;
    }

    private boolean performGroundAction(DinoSensor dinoSensor) {
        return dinoSensor.distanceFromObject() <= 174;
    }

    private void duckFromFlyingDuck() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.delay(500);
        robot.keyRelease(KeyEvent.VK_DOWN);
    }

    private static File takeScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    }
}
