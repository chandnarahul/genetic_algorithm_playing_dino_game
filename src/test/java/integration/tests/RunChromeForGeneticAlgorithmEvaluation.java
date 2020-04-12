package integration.tests;

import dino.geneticalgorithm.simple_genetic_algorithm.SimpleGAGeneticAlgorithm;

public class RunChromeForGeneticAlgorithmEvaluation {
    static {
        System.setProperty("webdriver.gecko.driver", "/usr/bin/chromedriver");
    }

    public static void main(String[] args) {

        SimpleGAGeneticAlgorithm simpleGAGeneticAlgorithm = new SimpleGAGeneticAlgorithm();
        int generation = 0;
        while (simpleGAGeneticAlgorithm.shouldContinueToEvaluate()) {
            simpleGAGeneticAlgorithm.applyCrossover();
            simpleGAGeneticAlgorithm.applyMutation();
            simpleGAGeneticAlgorithm.evaluatePopulation();
            System.out.println("For generation [" + generation + "] current fittest is " + simpleGAGeneticAlgorithm.getFittest());
            generation += 1;
        }

        System.out.println("Final fittest " + simpleGAGeneticAlgorithm.getFittest());
    }
}