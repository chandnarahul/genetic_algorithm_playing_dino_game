package dino.geneticalgorithm.sensor;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;

public class DinoImageSensorInteractionTest {

    @Test(expected = RuntimeException.class)
    public void should_identify_that_game_is_over() throws IOException {
        for (int i = 0; i < DinoImageSensorInteraction.MAX_COMMON_IMAGES; i++) {
            new DinoImageSensorInteraction(ImageIO.read(DinoSensorTest.class.getResourceAsStream("/game_over.png")), new ArrayList<>());
        }
    }
}