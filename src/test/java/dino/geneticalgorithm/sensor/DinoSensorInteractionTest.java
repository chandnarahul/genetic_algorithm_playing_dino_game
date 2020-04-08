package dino.geneticalgorithm.sensor;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.io.IOException;

public class DinoSensorInteractionTest {

    @Test(expected = RuntimeException.class)
    public void should_identify_that_game_is_over() throws IOException {
        for (int i = 0; i < DinoSensorInteraction.MAX_COMMON_IMAGES; i++) {
            new DinoSensorInteraction(ImageIO.read(DinoSensorTest.class.getResourceAsStream("/game_over.png")));
        }
    }
}