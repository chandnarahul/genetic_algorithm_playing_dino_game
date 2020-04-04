package dino.geneticalgorithm.sensor;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DinoSensorTest {

    @Test
    public void should_identify_flyingObject() throws IOException {
        DinoSensor dinoSensor = new DinoSensor(ImageIO.read(DinoSensorTest.class.getResourceAsStream("/bird_flying_high_and_other_objects.png")));
        assertTrue(dinoSensor.isObjectFlying());
    }

    @Test
    public void should_return_flyingObject_distance() throws IOException {
        DinoSensor dinoSensor = new DinoSensor(ImageIO.read(DinoSensorTest.class.getResourceAsStream("/bird_flying_high_and_other_objects.png")));
        assertEquals(96, dinoSensor.distanceFromObject());
    }

    @Test
    public void should_identify_object_closer_to_the_ground() throws IOException {
        DinoSensor dinoSensor = new DinoSensor(ImageIO.read(DinoSensorTest.class.getResourceAsStream("/all_objects_closer_to_the_ground.png")));
        assertTrue(dinoSensor.isObjectCloserToTheGround());
    }

    @Test
    public void should_return_groundObject_distance() throws IOException {
        DinoSensor dinoSensor = new DinoSensor(ImageIO.read(DinoSensorTest.class.getResourceAsStream("/all_objects_closer_to_the_ground.png")));
        assertEquals(145, dinoSensor.distanceFromObject());
    }
}