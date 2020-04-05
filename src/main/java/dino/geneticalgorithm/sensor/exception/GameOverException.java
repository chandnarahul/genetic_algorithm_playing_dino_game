package dino.geneticalgorithm.sensor.exception;

import dino.geneticalgorithm.sensor.DinoSensor;

public class GameOverException extends RuntimeException {

    private final DinoSensor dinoSensor;

    public GameOverException(String game_over, DinoSensor dinoSensor) {
        super(game_over);
        this.dinoSensor = dinoSensor;
    }

    public DinoSensor getDinoSensor() {
        return dinoSensor;
    }
}
