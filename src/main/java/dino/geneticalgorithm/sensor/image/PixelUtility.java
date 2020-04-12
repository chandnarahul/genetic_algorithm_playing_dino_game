package dino.geneticalgorithm.sensor.image;

import java.awt.image.BufferedImage;

public class PixelUtility {

    private final BufferedImage image;

    public PixelUtility(BufferedImage image) {
        this.image = image;
    }

    public boolean isAnyPixelFoundAtTop(int i) {
        return isGrayPixel(i, 0);
    }

    public boolean isGrayPixel(int xAxis, int yAxisBottomUp) {
        final int grayScale = 90;
        int pixel = image.getRGB(xAxis, yAxisBottomUp) & 0xFF;
        return pixel < grayScale;
    }
}
