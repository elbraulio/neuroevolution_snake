package com.elbraulio.neuroevolution.snake;

import com.elbraulio.genetical.*;
import com.elbraulio.genetical.crosses.RandomCross;
import com.elbraulio.genetical.experiment.DefaultExperiment;
import com.elbraulio.genetical.fittestseleccion.FittestByScore;
import com.elbraulio.genetical.individual.DefaultIndividual;
import com.elbraulio.genetical.population.DefaultPopulation;
import com.elbraulio.neuralnet.network.DefaultNetwork;
import com.elbraulio.neuralnet.network.NeuralNetwork;
import com.elbraulio.neuralnet.unit.NeuralUnit;
import com.elbraulio.neuralnet.utils.Normalize;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class HelloWorld {
    private final static int x = 15, y = 15;

    public static void main(String... args) throws InterruptedException {
        double learningRate = 0d;
        // ======== Configuration ===========
        // left, right, straight bounds; isrigth, isleft, isstraight
        int inputLength = 6;
        int outputLength = 3;
        int[] hiddenLength = new int[]{10,5,10};

        int popSize = 20;
        int tournamentSize = 10;
        int minScore = 300;
        double biasMutation = 0.1;
        double weightMutation = 0.1;
        double unitMutation = 0.3;
        // =================================
        Population<NeuralUnit> startPop = new DefaultPopulation<>(
                seed(
                        popSize, learningRate, inputLength, outputLength,
                        hiddenLength
                )
        );
        Crosses<NeuralUnit> crosses = new RandomCross<>(0.5);
        CheckSolution<NeuralUnit> solution = new SnakeSolution(
                learningRate, outputLength,
                hiddenLength, x, y, minScore
        );
        FittestSelection<NeuralUnit> selection = new FittestByScore<>(solution);
        Mutation<NeuralUnit> mutation = new NeuralMutation(
                biasMutation, weightMutation, unitMutation
        );
        PrintSolution<NeuralUnit> print = new NeuralPrint(x,y, learningRate,
                outputLength, hiddenLength);
        Experiment<NeuralUnit> ex = new DefaultExperiment<>(
                solution, selection, crosses, mutation, print
        );
        ex.run(tournamentSize, minScore, startPop);
    }

    private static List<Individual<NeuralUnit>> seed(
            int popSize, double learningRate, int inputLength,
            int outputLength, int[] hiddenLength
    ) {
        List<Individual<NeuralUnit>> pop = new ArrayList<>(popSize);
        for (int i = 0; i < popSize; i++) {
            pop.add(
                    new DefaultIndividual<>(
                            Arrays.asList(
                                    new DefaultNetwork(
                                            learningRate, inputLength,
                                            outputLength, hiddenLength
                                    ).neurons()
                            )
                    )
            );
        }
        return pop;
    }
}
