package dino.geneticalgorithm.sensor;

import dino.DinoConstants;
import dino.geneticalgorithm.sensor.exception.GameOverException;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DinoSensorInteraction {

    public static final int MAX_COMMON_IMAGES = 5;
    private static final List<DinoSensor> imageBuffers = new ArrayList<>(MAX_COMMON_IMAGES);
    private final DinoSensor dinoSensor;

    public DinoSensorInteraction(BufferedImage image, long screenshotDelay, Date gameStartTime) {
        this.dinoSensor = new DinoSensor(image);
        this.dinoSensor.setScreenshotDelay(screenshotDelay);
        this.dinoSensor.setGameStartTime(gameStartTime);
        imageBuffers.add(dinoSensor);
        stopExecutionIfNoNewImageIsReceived();
    }

    public DinoSensorInteraction(BufferedImage bufferedImage) {
        this(bufferedImage, DinoConstants.DIRECT_CAPTURE, new Date());
    }

    public DinoSensor sensor() {
        return dinoSensor;
    }

    private void stopExecutionIfNoNewImageIsReceived() {
        if (imageBuffers.size() == MAX_COMMON_IMAGES) {
            throw new GameOverException("game over", dinoSensor);
        } else if (ifUniqueImage()) {
            imageBuffers.clear();
        }
    }

    private boolean ifUniqueImage() {
        for (DinoSensor previous : imageBuffers) {
            DataBufferByte dataBufferByte = previous.imageDataBuffer();
            for (int dataBanks = 0; dataBanks < dataBufferByte.getNumBanks(); dataBanks++) {
                if (Arrays.equals(dinoSensor.imageDataBuffer().getData(dataBanks), dataBufferByte.getData(dataBanks))) {
                    return Boolean.FALSE;
                } else {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.TRUE;
    }

}
