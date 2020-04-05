package dino.run;

import dino.geneticalgorithm.sensor.DinoSensor;
import dino.geneticalgorithm.sensor.DinoSensorInteraction;
import dino.geneticalgorithm.sensor.exception.GameOverException;
import org.openqa.selenium.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class SeleniumDino {
    private final WebDriver webDriver;

    public SeleniumDino(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void run() throws Exception {
        try {
            WebElement ele = webDriver.findElement(By.id("gamecanvas"));
            Point point = ele.getLocation();
            webDriver.findElement(By.tagName("body")).sendKeys(Keys.UP);
            //int i = 0;
            do {
                BufferedImage image = ImageIO.read(takeScreenshot(webDriver)).getSubimage(point.getX(), point.getY(), ele.getSize().getWidth(), ele.getSize().getHeight());
                DinoSensor dinoSensor = new DinoSensorInteraction(image).sensor();
                if (dinoSensor.isAnyObjectFound()) {
                    dinoSensor.setSpeed(getSpeed());
                    System.out.println(dinoSensor.distanceFromObject() + " " + dinoSensor.speed() + " " + (dinoSensor.distanceFromObject() / dinoSensor.speed()));
                    if (dinoSensor.distanceFromObject() <= 90) {
                        webDriver.findElement(By.tagName("body")).sendKeys(Keys.UP);
                    }
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
            webDriver.quit();
        }
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
