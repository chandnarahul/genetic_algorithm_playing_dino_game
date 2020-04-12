package dino.geneticalgorithm.simple_genetic_algorithm;

import dino.DinoConstants;
import dino.run.SeleniumDino;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Arrays;
import java.util.Random;

import static dino.geneticalgorithm.simple_genetic_algorithm.SimpleGAConstants.SOLUTION;

public class SimpleGAIndividual {
    private int[] chromosome = new int[SOLUTION.length];


    public SimpleGAIndividual() {
        for (int i = 0; i < SOLUTION.length; i++) {
            chromosome[i] = new Random().nextInt(2);
        }
    }

    public int getFitnessScore() {
        WebDriver webDriver = new ChromeDriver();
        try {
            webDriver.get("chrome://dino");
            webDriver.manage().window().setSize(new Dimension(500, 450));
            Thread.sleep(1000);
            DinoConstants.JUMP_SAFE_DISTANCE = chromosomeToInt();
            System.out.println(DinoConstants.JUMP_SAFE_DISTANCE);
            return new SeleniumDino(webDriver).run();
        } catch (Exception e) {
            e.printStackTrace();
            return DinoConstants.PIXEL_NOT_FOUND;
        }
    }

    private int chromosomeToInt() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < chromosome.length; i++) {
            stringBuilder.append(chromosome[i]);
        }
        return Integer.parseInt(stringBuilder.toString(), 2);
    }

    public void selectGeneAt(int i, SimpleGAIndividual fromFirstParent) {
        chromosome[i] = fromFirstParent.chromosome[i];
    }

    public SimpleGAIndividual flipGeneAt(int geneIndex) {
        chromosome[geneIndex] = (chromosome[geneIndex] == 1) ? 0 : 1;
        return this;
    }

    @Override
    public String toString() {
        return "SimpleGAIndividual {" +
                "chromosome= " + Arrays.toString(chromosome) +
                '}';
    }
}
