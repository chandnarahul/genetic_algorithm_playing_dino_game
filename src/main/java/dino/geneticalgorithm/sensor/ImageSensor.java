package dino.geneticalgorithm.sensor;

import java.awt.image.BufferedImage;

public class ImageSensor {
    private final BufferedImage image;

    private boolean isObjectFlying = Boolean.FALSE;
    private boolean isObjectCloserToTheGround = Boolean.FALSE;
    private int distanceFromObject = 0;

    public ImageSensor(BufferedImage image) {
        this.image = image;
    }

    protected void findObjects() {
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
            for (int yAxisBottomUp = image.getHeight() - 1; yAxisBottomUp > image.getHeight() - 10; yAxisBottomUp--) {
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
            distanceFromObject = objectPixel;
        } else if (isObjectCloserToTheGround) {
            distanceFromObject = objectPixel;
        }
    }

    private boolean isGrayPixel(int xAxis, int yAxisBottomUp) {
        final int grayScale = 90;
        int pixel = image.getRGB(xAxis, yAxisBottomUp) & 0xFF;
        return pixel < grayScale;
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean isObjectFlying() {
        return isObjectFlying;
    }

    public boolean isObjectCloserToTheGround() {
        return isObjectCloserToTheGround;
    }

    public int getDistanceFromObject() {
        return distanceFromObject;
    }

    public boolean isAnyObjectFound() {
        return isObjectCloserToTheGround || isObjectFlying;
    }

}
