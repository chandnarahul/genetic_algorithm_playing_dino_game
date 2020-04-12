package dino.run;

import dino.geneticalgorithm.sensor.DinoImageSensorInteraction;
import org.openqa.selenium.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class SeleniumDino {
    private final WebDriver webDriver;
    private Date gameStartTime;

    public SeleniumDino(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void run() {
        try {
            startGame();
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
        gameStartTime = new Date();
    }

    private void processImageAndTakeAction() throws IOException, AWTException {
        switch (new DinoImageSensorInteraction(ImageIO.read(takeScreenshot(webDriver))).performAction()) {
            case CLOSER_TO_THE_GROUND:
                webDriver.findElement(By.tagName("body")).sendKeys(Keys.UP);
                break;
            case IN_THE_SKY:
                duckFromFlyingDuck();
                break;
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
