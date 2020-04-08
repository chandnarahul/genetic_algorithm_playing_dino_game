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
import java.util.Date;

public class SeleniumDino {
    private final WebDriver webDriver;
    private int screenshotImageIndex = 0;
    private Date gameStartTime;

    public SeleniumDino(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void run() {
        try {
            startGame();
            gameStartTime = new Date();
            for (; ; ) {
                processImageAndTakeAction();
            }
        } catch (Throwable e) {
        } finally {
            System.out.println("game ran for " + (new Date().getTime() - gameStartTime.getTime()));
            webDriver.quit();
        }
    }

    private void startGame() throws InterruptedException {
        webDriver.findElement(By.tagName("body")).sendKeys(Keys.UP);
        Thread.sleep(2000);
    }

    private void processImageAndTakeAction() throws IOException, AWTException {
        long start = new Date().getTime();
        BufferedImage bufferedImage = ImageIO.read(takeScreenshot(webDriver));
        long screenshotDelay = new Date().getTime() - start;
        DinoSensor dinoSensor = new DinoSensorInteraction(bufferedImage, screenshotDelay).sensor();
        if (dinoSensor.isObjectCloserToTheGround()) {
            if (performGroundAction(dinoSensor)) {
                webDriver.findElement(By.tagName("body")).sendKeys(Keys.UP);
                writeDebugImages(dinoSensor, "images/game");
            }
        } else if (dinoSensor.isObjectFlying()) {
            if (performFlyingAction(dinoSensor)) {
                duckFromFlyingDuck();
                writeDebugImages(dinoSensor, "images/game");
            }
        }
    }

    private double dinoSpeed() {
        return Double.parseDouble(((JavascriptExecutor) webDriver).executeScript("return Runner.instance_.currentSpeed;").toString());
    }

    private void writeDebugImages(DinoSensor dinoSensor, String fileName) throws IOException {
        if (DinoConstants.IN_DEBUG_MODE) {
            ImageIO.write(dinoSensor.image(), "png", new File(fileName + screenshotImageIndex + ".png"));
            screenshotImageIndex++;
        }
    }

    private boolean performFlyingAction(DinoSensor dinoSensor) {
        return dinoSensor.distanceFromObject() <= 200;
    }

    private boolean performGroundAction(DinoSensor dinoSensor) {
        if (dinoSensor.isLongGroundObject()) {
            return dinoSensor.distanceFromObject() - dinoSensor.screenshotDelay() <= 10;
        } else {
            return dinoSensor.distanceFromObject() - dinoSensor.screenshotDelay() <= 80;
        }
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
