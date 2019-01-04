package com.elbraulio.neuroevolution.snake;

import com.elbraulio.genetical.*;
import com.elbraulio.genetical.crosses.RandomCross;
import com.elbraulio.genetical.evolution.FittestEvolve;
import com.elbraulio.genetical.experiment.PrecisionChart;
import com.elbraulio.genetical.fittestseleccion.FittestByScore;
import com.elbraulio.genetical.individual.DefaultIndividual;
import com.elbraulio.genetical.population.DefaultPopulation;
import com.elbraulio.neuralnet.network.DefaultNetwork;
import com.elbraulio.neuralnet.unit.NeuralUnit;

import java.util.*;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class HelloWorld {
    private final static int x = 15, y = 15;

    public static void main(String... args) {
        // ======== Configuration ===========
        // left, right, straight bounds; isrigth, isleft, isstraight
        int[] hiddenLength = new int[]{20, 10, 10};
        int popSize = 500;
        int tournamentSize = 100;
        int minScore = 5;
        double unitMutation = 0.3;
        double biasMutation = 0.5;
        double weightMutation = 0.2;
        int printSince = 200;
        // =================================
        int outputLength = 3;
        int inputLength = 18;
        double learningRate = 0d;
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
        PrintSolution<NeuralUnit> print = new NeuralPrint(x, y, learningRate,
                outputLength, hiddenLength);
        final List<Number> scoreAverage = new LinkedList<>();
        final List<Number> fittestScore = new LinkedList<>();
        final List<Population<NeuralUnit>> offspring = new LinkedList<>();
        offspring.add(startPop);
        final Number[][] score =
                new Number[][]{offspring.get(offspring.size() - 1).scores(solution)};
        double maxScore = 0d;
        while (
                (maxScore = findMax(score[0])) < minScore
        ) {
            scoreAverage.add(scoreAverage(score[0]));
            fittestScore.add(maxScore);
            offspring.add(
                    offspring.get(offspring.size() - 1).evolve(
                            new FittestEvolve<>(
                                    list -> {
                                        List<Integer> subset =
                                                new ArrayList<>();
                                        List<Individual<NeuralUnit>> clone = new ArrayList<>(list);

                                        for (int i = 0; i < tournamentSize; ++i) {
                                            int index = (new Random()).nextInt(clone.size());
                                            subset.add(index);
                                            clone.remove(index);
                                        }

                                        int max = 0;
                                        for (int i = 0; i < subset.size(); i++) {
                                            if (score[0][subset.get(i)].intValue() > max)
                                                max = subset.get(i);
                                        }
                                        return list.get(max);
                                    },
                                    crosses,
                                    mutation
                            )
                    )
            );
            if (printSince-- <= 0) {
                print.print(
                        offspring.get(offspring.size() - 1).individuals()
                                .get(fittestIndex(score[0]))
                );
            }
            score[0] = offspring.get(offspring.size() - 1).scores(solution);
        }
        scoreAverage.add(scoreAverage(score[0]));
        fittestScore.add(maxScore);
        print.print(
                offspring.get(offspring.size() - 1).fittest(
                        selection
                )
        );
        new PrecisionChart(
                scoreAverage.toArray(new Number[scoreAverage.size()]),
                "Score average by offspring.\nScore accepted as fittest = " + minScore,
                minScore

        ).show();
        new PrecisionChart(
                fittestScore.toArray(new Number[fittestScore.size()]),
                "Fittest score by offspring.\nScore accepted as fittest = " + minScore,
                minScore
        ).show();
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

    private static int fittestIndex(Number[] score) {
        int max = 0;
        for (int i = 0; i < score.length; i++) {
            max = score[max].doubleValue() > score[i].doubleValue() ? max : i;
        }
        return max;
    }

    private static double findMax(Number[] score) {
        double max = 0;
        for (int i = 0; i < score.length; i++) {
            max = score[i].doubleValue() > max ? score[i].doubleValue() : max;
        }
        return max;
    }

    /**
     * calculate score average for a given Population
     *
     * @return score average
     */
    private static Number scoreAverage(Number[] score) {
        double average = 0d;
        for (int i = 0; i < score.length; i++) {
            average += score[i].doubleValue();
        }
        return average / score.length;
    }
}
