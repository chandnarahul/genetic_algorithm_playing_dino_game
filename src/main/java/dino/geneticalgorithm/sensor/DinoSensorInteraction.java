package dino.geneticalgorithm.sensor;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DinoSensorInteraction {

    public static final int MAX_COMMON_IMAGES = 5;
    private static final List<DataBufferByte> imageBuffers = new ArrayList<>(MAX_COMMON_IMAGES);
    private final DinoSensor dinoSensor;

    public DinoSensorInteraction(BufferedImage image) {
        this.dinoSensor = new DinoSensor(image);
        imageBuffers.add(dinoSensor.imageDataBuffer());
        stopExecutionIfNoNewImageIsReceived();
    }

    public DinoSensor sensor() {
        return dinoSensor;
    }

    private void stopExecutionIfNoNewImageIsReceived() {
        if (imageBuffers.size() == MAX_COMMON_IMAGES) {
            throw new RuntimeException("game over");
        }
        else if (ifUniqueImage()) {
            imageBuffers.clear();
        }
    }

    private boolean ifUniqueImage() {
        for (DataBufferByte dataBufferByte : imageBuffers) {
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
