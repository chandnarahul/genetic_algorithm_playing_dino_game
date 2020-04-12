package dino.geneticalgorithm.simple_genetic_algorithm;

import java.util.Random;

public class SimpleGAGeneticAlgorithm {

    private SimpleGAPopulation simpleGAPopulation = new SimpleGAPopulation();
    private int iterations = 0;

    public SimpleGAGeneticAlgorithm() {
        simpleGAPopulation.recalculatePopulationFitness();
    }

    public void evaluatePopulation() {
        System.out.println("evaluatePopulation start");
        simpleGAPopulation.recalculatePopulationFitness();
        System.out.println("evaluatePopulation end");
    }

    public SimpleGAIndividual getFittest() {
        return simpleGAPopulation.fittestIndividual();
    }

    public boolean shouldContinueToEvaluate() {
        System.out.println("shouldContinueToEvaluate");
        boolean isLessThanMaxNumberOfIterations = iterations < SimpleGAConstants.NUMBER_OF_ITERATIONS;
        boolean haventFoundFittestIndividualYet = simpleGAPopulation.fittestIndividual().getFitnessScore() > SimpleGAConstants.FITTEST_SOLUTION_RUNTIME;
        if (isLessThanMaxNumberOfIterations && haventFoundFittestIndividualYet) {
            iterations += 1;
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public void applyCrossover() {
        System.out.println("applyCrossover start");
        SimpleGAPopulation crossOveredSimpleGAPopulation = new SimpleGAPopulation();
        for (int i = 0; i < SimpleGAConstants.POPULATION_SIZE; i++) {
            SimpleGAIndividual firstParent = crossOveredSimpleGAPopulation.individual(i);
            if (SimpleGAConstants.CROSSOVER_RATE > Math.random() && i >= SimpleGAConstants.NUMBER_OF_ELITE_INDIVIDUALS) {
                SimpleGAIndividual secondParent = selectViaRouletteWheelSelection();
                crossOveredSimpleGAPopulation.updateIndividualAt(i, selectGenesFrom(firstParent, secondParent));
            } else {
                crossOveredSimpleGAPopulation.updateIndividualAt(i, firstParent);
            }
        }
        this.simpleGAPopulation = crossOveredSimpleGAPopulation;
        System.out.println("applyCrossover end");
    }

    private SimpleGAIndividual selectGenesFrom(SimpleGAIndividual fromFirstParent, SimpleGAIndividual fromSecondParent) {
        SimpleGAIndividual simpleGAIndividual = new SimpleGAIndividual();
        for (int i = 0; i < SimpleGAConstants.CHROMOSOME_LENGTH; i++) {
            if (0.5 > Math.random()) {
                simpleGAIndividual.selectGeneAt(i, fromFirstParent);
            } else {
                simpleGAIndividual.selectGeneAt(i, fromSecondParent);
            }
        }
        return simpleGAIndividual;
    }

    private SimpleGAIndividual selectViaRouletteWheelSelection() {
        return simpleGAPopulation.individualThatMatchesFitness(new Random().nextInt(simpleGAPopulation.getTotalPopulationFitnessScore()));
    }

    public void applyMutation() {
        System.out.println("applyMutation start");
        SimpleGAPopulation mutatedPopulation = new SimpleGAPopulation();
        for (int i = 0; i < SimpleGAConstants.POPULATION_SIZE; i++) {
            SimpleGAIndividual simpleGAIndividual = simpleGAPopulation.individual(i);
            if (i >= SimpleGAConstants.NUMBER_OF_ELITE_INDIVIDUALS) {
                for (int geneIndex = 0; geneIndex < SimpleGAConstants.CHROMOSOME_LENGTH; geneIndex++) {
                    if (SimpleGAConstants.MUTATION_RATE > Math.random()) {
                        simpleGAIndividual.flipGeneAt(geneIndex);
                    }
                }
            }
            mutatedPopulation.updateIndividualAt(i, simpleGAIndividual);
        }
        this.simpleGAPopulation = mutatedPopulation;
        System.out.println("applyMutation end");
    }
}
