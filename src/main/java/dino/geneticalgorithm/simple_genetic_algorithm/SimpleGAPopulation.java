package dino.geneticalgorithm.simple_genetic_algorithm;

import java.util.ArrayList;
import java.util.List;

import static dino.geneticalgorithm.simple_genetic_algorithm.SimpleGAConstants.POPULATION_SIZE;

public class SimpleGAPopulation {
    private List<SimpleGAIndividual> simpleGAIndividuals;
    private int totalPopulationFitnessScore;

    public SimpleGAPopulation() {
        simpleGAIndividuals = new ArrayList<>(POPULATION_SIZE);
        for (int i = 0; i < POPULATION_SIZE; i++) {
            simpleGAIndividuals.add(new SimpleGAIndividual());
        }
        System.out.println("Population initialised");
        sortByFittestIndividual();
        System.out.println("sortByFittestIndividual done");
        recalculatePopulationFitness();
        System.out.println("recalculatePopulationFitness done");
    }

    public void updateIndividualAt(int location, SimpleGAIndividual toIndividual) {
        simpleGAIndividuals.set(location, toIndividual);
        sortByFittestIndividual();
    }

    private void sortByFittestIndividual() {
        simpleGAIndividuals.sort((i1, i2) -> {
            if (i1.getFitnessScore() > i2.getFitnessScore()) {
                return -1;
            } else if (i1.getFitnessScore() < i2.getFitnessScore()) {
                return 1;
            } else {
                return 0;
            }
        });
    }

    public void recalculatePopulationFitness() {
        simpleGAIndividuals.forEach(individual -> this.totalPopulationFitnessScore = +individual.getFitnessScore());
    }

    public int getTotalPopulationFitnessScore() {
        return totalPopulationFitnessScore;
    }

    public SimpleGAIndividual individual(int location) {
        return simpleGAIndividuals.get(location);
    }

    public SimpleGAIndividual individualThatMatchesFitness(int nextInt) {
        return simpleGAIndividuals
                .stream()
                .filter(indi -> indi.getFitnessScore() >= nextInt)
                .findFirst()
                .orElse(simpleGAIndividuals.get(POPULATION_SIZE - 1));
    }

    public SimpleGAIndividual fittestIndividual() {
        return simpleGAIndividuals.get(0);
    }

}
