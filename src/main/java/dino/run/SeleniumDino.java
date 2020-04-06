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
                if (dinoSensor.isObjectCloserToTheGround()) {
                    dinoSensor.setSpeed(getSpeed());
                    System.out.println(dinoSensor.distanceFromObject() + " " + dinoSensor.speed() + " " + (dinoSensor.distanceFromObject() / dinoSensor.speed()));
                    if (dinoSensor.distanceFromObject() <= 90) {
                        webDriver.findElement(By.tagName("body")).sendKeys(Keys.UP);
                    }
                } else if (dinoSensor.isObjectFlying()) {
                    dinoSensor.setSpeed(getSpeed());
                    System.out.println(dinoSensor.distanceFromObject() + " " + dinoSensor.speed() + " " + (dinoSensor.distanceFromObject() / dinoSensor.speed()));
                    if (dinoSensor.distanceFromObject() <= 90) {
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

    private void duck() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.delay(500);
        robot.keyRelease(KeyEvent.VK_DOWN);
    }

    private double getSpeed() {
        try {
            String speed = webDriver.findElement(By.id("speed")).getAttribute("value");
            return Double.parseDouble(speed);
        } catch (Exception e) {
            return 0;
        }
    }

    private static File takeScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    }
}
