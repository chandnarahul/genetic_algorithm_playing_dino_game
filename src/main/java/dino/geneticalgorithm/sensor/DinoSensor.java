package dino.geneticalgorithm.sensor;

import dino.DinoConstants;
import dino.geneticalgorithm.sensor.image.PixelUtility;
import dino.geneticalgorithm.sensor.object.ObjectLocation;
import dino.geneticalgorithm.sensor.object.ObjectWidth;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class DinoSensor {
    private ObjectLocation objectLocation = ObjectLocation.NO_OBJECT_DETECTED;
    private int groundObjectWidth = 0;
    private final BufferedImage image;
    private int objectXAxisPoint;

    public DinoSensor(BufferedImage image) {
        if (image.getWidth() == DinoConstants.GAME_CANVAS_WIDTH) {
            this.image = removeDinoFloorAndSkyFromImage(image);
        } else {
            this.image = image;
        }
        this.findObject();
    }

    private BufferedImage removeDinoFloorAndSkyFromImage(BufferedImage image) {
        return image.getSubimage(DinoConstants.DINO_X_AXIS, DinoConstants.DINO_Y_AXIS, image.getWidth() / 2, image.getHeight() - 291);
    }

    public DataBufferByte imageDataBuffer() {
        return (DataBufferByte) image.getRaster().getDataBuffer();
    }


    private boolean isAnyPixelFoundAtBottom(int currentXAxisLocation) {
        for (int traverseYAxis = image.getHeight() - 1; traverseYAxis >= 0; traverseYAxis--) {
            if (new PixelUtility(image).isGrayPixel(currentXAxisLocation, traverseYAxis)) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        }
        return Boolean.FALSE;
    }

    private void findObject() {
        int continueForFewPixels = 15;
        int firstPixelFound = -1;
        for (int i = 0; i < image.getWidth(); i++) {
            if (new PixelUtility(image).isAnyPixelFoundAtTop(i)) {
                if (firstPixelFound == -1) {
                    firstPixelFound = i;
                    this.objectXAxisPoint = firstPixelFound;
                }
                objectLocation = ObjectLocation.IN_THE_SKY;
            }
            if (isAnyPixelFoundAtBottom(i)) {
                if (firstPixelFound == -1) {
                    firstPixelFound = i;
                    this.objectXAxisPoint = firstPixelFound;
                }
                objectLocation = ObjectLocation.CLOSER_TO_THE_GROUND;
                this.groundObjectWidth = new ObjectWidth(this.objectXAxisPoint, image).determineWidthOfTheGroundObject();
            }
            if (firstPixelFound != -1 && (i - firstPixelFound) > continueForFewPixels) {
                break;
            }
        }
    }

    public ObjectLocation objectLocation() {
        return this.objectLocation;
    }

    protected boolean isLongGroundObject() {
        return groundObjectWidth >= DinoConstants.CLUSTERED_CACTUS_SIZE;
    }

    public int getGroundObjectWidth() {
        return groundObjectWidth;
    }

    public int distanceFromObject() {
        if (isLongGroundObject()) {
            return this.objectXAxisPoint + getGroundObjectWidth() / 2;
        } else {
            return this.objectXAxisPoint;
        }
    }

    public ObjectLocation performGroundAction() {
        return distanceFromObject() < DinoConstants.JUMP_SAFE_DISTANCE ? ObjectLocation.CLOSER_TO_THE_GROUND : ObjectLocation.NO_OBJECT_DETECTED;
    }

    public ObjectLocation performFlyingAction() {
        return distanceFromObject() <= DinoConstants.JUMP_SAFE_DISTANCE ? ObjectLocation.IN_THE_SKY : ObjectLocation.NO_OBJECT_DETECTED;
    }

    public ObjectLocation performAction() {
        if (objectLocation() == ObjectLocation.CLOSER_TO_THE_GROUND) {
            return performGroundAction();
        } else if (objectLocation() == ObjectLocation.IN_THE_SKY) {
            return performFlyingAction();
        } else {
            return ObjectLocation.NO_OBJECT_DETECTED;
        }
    }
}
