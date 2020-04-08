package dino.geneticalgorithm.sensor;

import dino.DinoConstants;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

public class DinoSensor {
    private final int DINO_X_AXIS = 78;
    private final int DINO_Y_AXIS = 154;
    private boolean isObjectFlying = Boolean.FALSE;
    private boolean isObjectCloserToTheGround = Boolean.FALSE;
    private int distanceFromFirstObjectXAxis = 0;
    private int groundObjectWidth = 0;
    private final BufferedImage image;
    private long screenshotDelay;

    public DinoSensor(BufferedImage image) {
        int gameCanvasWidth = 500;
        if (image.getWidth() == gameCanvasWidth) {
            this.image = removeDinoFloorAndSkyFromImage(image);
        } else {
            this.image = image;
        }
        this.findObject();
    }

    protected void setScreenshotDelay(long screenshotDelay) {
        this.screenshotDelay = screenshotDelay;
    }

    private BufferedImage removeDinoFloorAndSkyFromImage(BufferedImage image) {
        return image.getSubimage(DINO_X_AXIS, DINO_Y_AXIS, image.getWidth() / 2, image.getHeight() - 291);
    }

    public DataBufferByte imageDataBuffer() {
        return (DataBufferByte) image.getRaster().getDataBuffer();
    }


    private boolean isAnyPixelFoundAtBottom(int firstPixelFoundAt) {
        int fewPixelsTowardsRight = 10;
        int pixelsFound = 0;
        for (int traverseXAxis = firstPixelFoundAt; traverseXAxis < image.getWidth(); traverseXAxis++) {
            if (isGrayPixel(traverseXAxis, image.getHeight() - 1)) {
                return Boolean.TRUE;
            } else {
                pixelsFound++;
                if (pixelsFound == fewPixelsTowardsRight) {
                    return Boolean.FALSE;
                }
            }
        }
        return Boolean.FALSE;
    }

    private void findObject() {
        for (int i = 0; i < image.getWidth(); i++) {
            if (isAnyPixelFoundAtTop(i)) {
                distanceFromFirstObjectXAxis = i;
                this.isObjectFlying = Boolean.TRUE;
                this.isObjectCloserToTheGround = Boolean.FALSE;
            }
            if (isAnyPixelFoundAtBottom(i)) {
                this.isObjectCloserToTheGround = Boolean.TRUE;
                this.isObjectFlying = Boolean.FALSE;
            }
            if (this.isObjectCloserToTheGround || this.isObjectFlying) {
                distanceFromFirstObjectXAxis = i;
                break;
            }
        }
        if (this.isObjectCloserToTheGround) {
            this.groundObjectWidth = determineWidthOfTheGroundObject();
        }
    }

    private boolean isAnyPixelFoundAtTop(int i) {
        return isGrayPixel(i, 0);
    }

    private int determineWidthOfTheGroundObject() {
        int pixelNotFound = 0;
        int lastPixelFound = 0;
        for (int i = distanceFromFirstObjectXAxis; i < image().getWidth(); i++) {
            if (isGrayPixel(i, image().getHeight() - 1)) {
                lastPixelFound = i;
                pixelNotFound = 0;
            } else {
                pixelNotFound++;
            }
            if (pixelNotFound > 10) {
                return calculateObjectWidth(lastPixelFound);
            }
        }
        return calculateObjectWidth(lastPixelFound);
    }

    private int calculateObjectWidth(int lastPixelFound) {
        extractObjectAsImage(lastPixelFound);
        return lastPixelFound - distanceFromFirstObjectXAxis;
    }

    private void extractObjectAsImage(int lastPixelFound) {
        try {
            if (DinoConstants.IN_DEBUG_MODE && lastPixelFound != 0) {
                ImageIO.write(image.getSubimage(distanceFromFirstObjectXAxis, 0, lastPixelFound - distanceFromFirstObjectXAxis, 30), "png", new File("images/block.png"));
            }
        } catch (Exception e) {

        }
    }

    private boolean isGrayPixel(int xAxis, int yAxisBottomUp) {
        final int grayScale = 90;
        int pixel = image.getRGB(xAxis, yAxisBottomUp) & 0xFF;
        return pixel < grayScale;
    }

    public BufferedImage image() {
        return image;
    }

    public boolean isObjectFlying() {
        return isObjectFlying;
    }

    public boolean isObjectCloserToTheGround() {
        return isObjectCloserToTheGround;
    }

    public int distanceFromObject() {
        return distanceFromFirstObjectXAxis;
    }

    public long screenshotDelay() {
        return screenshotDelay;
    }

    public boolean isLongGroundObject() {
        int clusteredCactusSize = 60;
        return groundObjectWidth > clusteredCactusSize;
    }
}
