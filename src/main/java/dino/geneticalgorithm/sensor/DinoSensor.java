package dino.geneticalgorithm.sensor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

public class DinoSensor {
    public static final int MINIMUM_CONNECTED_PIXELS = 5;
    private final BufferedImage image;

    private boolean isObjectFlying = Boolean.FALSE;
    private boolean isObjectCloserToTheGround = Boolean.FALSE;
    private int distanceFromObject = 0;
    private double speed;
    private final int DINO_X_AXIS = 62;
    private final int DINO_Y_AXIS = 95;

    protected DinoSensor(BufferedImage bufferedImage) {
        this(bufferedImage, 0);
    }

    protected DinoSensor(BufferedImage bufferedImage, double speed) {
        this.image = removeDinoFloorAndSkyFromImage(bufferedImage);
        this.speed = speed;
        findObjects();
    }

    protected DinoSensor(BufferedImage bufferedImage, boolean removeDino) {
        this.speed = 0;
        if (removeDino) {
            this.image = removeDinoFloorAndSkyFromImage(bufferedImage);
        } else {
            this.image = bufferedImage;
        }
        try {
            ImageIO.write(image, "png", new File("test.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        findObjects();
    }

    private BufferedImage removeDinoFloorAndSkyFromImage(BufferedImage image) {
        int buffer = 4;
        return image.getSubimage(DINO_X_AXIS + buffer, DINO_Y_AXIS, image.getWidth() / 2, image.getHeight() - 120);
    }

    public DataBufferByte imageDataBuffer() {
        return (DataBufferByte) image().getRaster().getDataBuffer();
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

    public boolean isAnyObjectFound() {
        return isObjectCloserToTheGround || isObjectFlying;
    }

    public int distanceFromObject() {
        return distanceFromObject;
    }

    private void findObjects() {
        boolean up = false;
        boolean bottom = false;
        int objectPixel = 0;
        for (int i = 0; i < image.getWidth(); i++) {
            if (up || bottom) {
                break;
            }
            if (isGrayPixel(i, 0)) {
                up = true;
                objectPixel = i;
            }
            for (int yAxisBottomUp = image.getHeight() - 1; yAxisBottomUp > 0; yAxisBottomUp--) {
                if (isGrayPixel(i, yAxisBottomUp)) {
                    bottom = true;
                    objectPixel = i;
                }
            }
        }
        if (up && bottom) {
            this.isObjectCloserToTheGround = Boolean.TRUE;
        } else if (bottom) {
            this.isObjectCloserToTheGround = Boolean.TRUE;
        } else if (up) {
            this.isObjectFlying = Boolean.TRUE;
        }
        if (isObjectFlying) {
            distanceFromObject = objectPixel - DINO_X_AXIS;
        } else if (isObjectCloserToTheGround) {
            distanceFromObject = objectPixel - DINO_X_AXIS;
        }
    }

    private boolean isGrayPixel(int xAxis, int yAxisBottomUp) {
        final int grayScale = 90;
        int pixel = image.getRGB(xAxis, yAxisBottomUp) & 0xFF;
        return pixel < grayScale;
    }

    public double speed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
