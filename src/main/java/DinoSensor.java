import java.awt.image.BufferedImage;

public class DinoSensor {
    public static final int MINIMUM_CONNECTED_PIXELS = 5;
    private final BufferedImage image;

    private boolean isObjectFlying = Boolean.FALSE;
    private boolean isObjectCloserToTheGround = Boolean.FALSE;
    private long distanceFromObject = 0;
    private final int DINO_X_AXIS = 62;
    private final int DINO_Y_AXIS = 95;

    public DinoSensor(BufferedImage bufferedImage) {
        this.image = removeDinoFloorAndSkyFromImage(bufferedImage);
        findObjects();
    }

    private BufferedImage removeDinoFloorAndSkyFromImage(BufferedImage image) {
        int buffer = 4;
        image = image.getSubimage(DINO_X_AXIS + buffer, DINO_Y_AXIS, image.getWidth() / 2 - 50, image.getHeight() - 120);
        return image;
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

    public long getDistanceFromObject() {
        return distanceFromObject;
    }

    private void findObjects() {
        for (int i = 0; i < image.getWidth(); i++) {
            if (isGrayPixel(i, 0)) {
                int skyConnectedPixelsCount = 0;
                for (int j = 0; j < 5; j++) {
                    if (isGrayPixel(i, j)) {
                        skyConnectedPixelsCount += 1;
                        if (isObjectTouchingTheSky(skyConnectedPixelsCount)) {
                            if (isObjectTouchingTheGroundAsWell(i)) {
                                isObjectCloserToTheGround = Boolean.TRUE;
                            } else {
                                isObjectFlying = Boolean.TRUE;
                            }
                            distanceFromObject = Math.round(Math.sqrt(Math.pow(i - DINO_X_AXIS, 2) + Math.pow(j - DINO_Y_AXIS, 2)));
                            return;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
    }

    private boolean isObjectTouchingTheSky(int skyConnectedPixelsCount) {
        return skyConnectedPixelsCount == MINIMUM_CONNECTED_PIXELS;
    }

    private boolean isObjectTouchingTheGroundAsWell(int xAxis) {
        int groundConnectedPixelsCount = 0;
        for (int yAxisBottomUp = image.getHeight() - 1; yAxisBottomUp > image.getHeight() - 1 - MINIMUM_CONNECTED_PIXELS; yAxisBottomUp--) {
            if (isGrayPixel(xAxis, yAxisBottomUp)) {
                groundConnectedPixelsCount += 1;
                if (isObjectTouchingTheGround(groundConnectedPixelsCount)) {
                    return Boolean.TRUE;
                }
            } else {
                return Boolean.FALSE;
            }
        }
        return Boolean.FALSE;
    }

    private boolean isObjectTouchingTheGround(int groundConnectedPixelsCount) {
        return groundConnectedPixelsCount == MINIMUM_CONNECTED_PIXELS;
    }

    private boolean isGrayPixel(int xAxis, int yAxisBottomUp) {
        final int grayScale = 90;
        return (image.getRGB(xAxis, yAxisBottomUp) & 0xFF) < grayScale;
    }
}
