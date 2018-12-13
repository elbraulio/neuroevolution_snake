package com.elbraulio.genetical.experiment;

import com.elbraulio.genetical.*;
import com.elbraulio.genetical.evolution.FittestEvolve;
import com.elbraulio.genetical.fittestseleccion.SubsetOf;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class DefaultExperiment<T> implements Experiment<T> {

    private final CheckSolution<T> solution;
    private final FittestSelection<T> fittestSelection;
    private final Crosses<T> crosses;
    private final Mutation<T> mutation;
    private final PrintSolution<T> printSolution;
    private final Logger logger = Logger.getLogger(DefaultExperiment.class);

    public DefaultExperiment(
            CheckSolution<T> solution, FittestSelection<T> fittestSelection,
            Crosses<T> crosses, Mutation<T> mutation,
            PrintSolution<T> printSolution
    ) {
        this.solution = solution;
        this.fittestSelection = fittestSelection;
        this.crosses = crosses;
        this.mutation = mutation;
        this.printSolution = printSolution;
    }

    @Override
    public void run(int tournamentSize, int minScore, Population<T> startPop) {
        final List<Number> scoreAverage = new LinkedList<>();
        final List<Number> fittestScore = new LinkedList<>();
        final List<Population<T>> offspring = new LinkedList<>();
        offspring.add(startPop);
        while (
                this.solution.score(
                        offspring.get(offspring.size() - 1).fittest(
                                this.fittestSelection
                        ).genes()
                ) < minScore
        ) {
            scoreAverage.add(scoreAverage(offspring.get(offspring.size() - 1)));
            fittestScore.add(
                    this.solution.score(
                            offspring.get(offspring.size() - 1)
                                    .fittest(this.fittestSelection).genes()
                    )
            );
            offspring.add(
                    offspring.get(offspring.size() - 1).evolve(
                            new FittestEvolve<>(
                                    new SubsetOf<>(
                                            tournamentSize,
                                            fittestSelection
                                    ),
                                    this.crosses,
                                    this.mutation
                            )
                    )
            );
            this.logger.info(
                    "Fittest individual from offspring " + offspring.size()
            );
            this.printSolution.print(
                    offspring.get(offspring.size() - 1).fittest(
                            this.fittestSelection
                    )
            );
        }
        scoreAverage.add(scoreAverage(offspring.get(offspring.size() - 1)));
        fittestScore.add(
                this.solution.score(
                        offspring.get(offspring.size() - 1)
                                .fittest(this.fittestSelection).genes()
                )
        );
        this.logger.info(
                "Solution found after " + offspring.size() + " " +
                        "offspring"
        );
        this.printSolution.print(
                offspring.get(offspring.size() - 1).fittest(
                        this.fittestSelection
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

    /**
     * calculate score average for a given Population
     *
     * @param population to calculate score average
     * @return score average
     */
    private Number scoreAverage(Population<T> population) {
        double average = 0d;
        for (Individual<T> individual : population.individuals()) {
            average += this.solution.score(individual.genes());
        }
        return average / population.individuals().size();
    }
}
