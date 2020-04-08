package dino.geneticalgorithm.sensor;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class DinoSensor {
    private double speed;
    private final int DINO_X_AXIS = 78;
    private final int DINO_Y_AXIS = 154;
    private final ImageSensor imageSensor;

    protected DinoSensor(BufferedImage bufferedImage) {
        this.speed = 0;
        if (bufferedImage.getWidth() > 250) {
            this.imageSensor = new ImageSensor(removeDinoFloorAndSkyFromImage(bufferedImage));
        } else {
            this.imageSensor = new ImageSensor(bufferedImage);
        }
        this.imageSensor.findObjects();
    }

    private BufferedImage removeDinoFloorAndSkyFromImage(BufferedImage image) {
        return image.getSubimage(DINO_X_AXIS, DINO_Y_AXIS, image.getWidth() / 2, image.getHeight() - 291);
    }

    public DataBufferByte imageDataBuffer() {
        return (DataBufferByte) image().getRaster().getDataBuffer();
    }

    public BufferedImage image() {
        return imageSensor.getImage();
    }

    public boolean isObjectFlying() {
        return imageSensor.isObjectFlying();
    }

    public boolean isObjectCloserToTheGround() {
        return imageSensor.isObjectCloserToTheGround();
    }

    public boolean isAnyObjectFound() {
        return imageSensor.isAnyObjectFound();
    }

    public int distanceFromObject() {
        return imageSensor.getDistanceFromObject();
    }


    public double speed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
