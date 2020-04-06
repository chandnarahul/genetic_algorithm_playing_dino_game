package dino.geneticalgorithm.sensor;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class DinoSensorTest {

    @Test
    public void should_identify_flyingObject() throws IOException {
        DinoSensor dinoSensor = new DinoSensor(ImageIO.read(new File("src/test/resources/bird_flying_high_and_other_objects.png")));
        assertTrue(dinoSensor.isObjectFlying());
    }

    @Test
    public void should_return_flyingObject_distance() throws IOException {
        DinoSensor dinoSensor = new DinoSensor(ImageIO.read(new File("src/test/resources/bird_flying_high_and_other_objects.png")));
        assertTrue(dinoSensor.distanceFromObject() > 0);
    }

    @Test
    public void should_identify_object_closer_to_the_ground() throws IOException {
        DinoSensor dinoSensor = new DinoSensor(ImageIO.read(new File("src/test/resources/all_objects_closer_to_the_ground.png")));
        assertTrue(dinoSensor.distanceFromObject() > 0);
    }

    @Test
    public void should_identify_mini_object_closer_to_the_ground_1() throws IOException {
        DinoSensor dinoSensor = new DinoSensor(ImageIO.read(new File("src/test/resources/mini_objects.png")));
        assertTrue(dinoSensor.isObjectCloserToTheGround());
    }

    @Test
    public void should_identify_mini_object_closer_to_the_ground_2() throws IOException {
        DinoSensor dinoSensor = new DinoSensor(ImageIO.read(new File("src/test/resources/game67.png")));
        assertTrue(dinoSensor.isObjectCloserToTheGround());
    }

    @Test
    public void sensor_distance_should_reduce_as_object_moves_closer_to_dino() throws IOException {
        int distance = Integer.MAX_VALUE;
        for (int i = 65; i <= 74; i++) {
            String pathname = "src/test/resources/movingobject/game" + i + ".png";
            DinoSensor dinoSensor = new DinoSensor(ImageIO.read(new File(pathname)));
            assertTrue(dinoSensor.distanceFromObject() < distance);
            distance = dinoSensor.distanceFromObject();
        }
    }

    @Test
    public void should_return_groundObject_distance() throws IOException {
        DinoSensor dinoSensor = new DinoSensor(ImageIO.read(new File("src/test/resources/all_objects_closer_to_the_ground.png")));
        assertTrue(dinoSensor.distanceFromObject() > 0);
    }
}