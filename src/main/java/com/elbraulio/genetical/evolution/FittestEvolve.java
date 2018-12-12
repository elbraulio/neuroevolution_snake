package com.elbraulio.genetical.evolution;

import com.elbraulio.genetical.*;
import com.elbraulio.genetical.individual.DefaultIndividual;
import com.elbraulio.genetical.population.DefaultPopulation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class FittestEvolve<T> implements Evolution<T> {

    private final FittestSelection<T> tournament;
    private final Crosses<T> crosses;
    private final Mutation<T> mutation;

    public FittestEvolve(
            FittestSelection<T> tournament, Crosses<T> crosses, Mutation<T> mutation
    ) {
        this.tournament = tournament;
        this.crosses = crosses;
        this.mutation = mutation;
    }

    @Override
    public Population<T> nextGeneration(List<Individual<T>> individuals) {
        final List<Individual<T>> offspring =
                new ArrayList<>(individuals.size());
        for (int i = 0; i < individuals.size(); i++) {
            offspring.add(
                    new DefaultIndividual<>(
                            this.mutation.genes(
                                    this.crosses.genes(
                                            this.tournament.fittest(
                                                    individuals
                                            ).genes(),
                                            this.tournament.fittest(
                                                    individuals
                                            ).genes()
                                    )
                            )
                    )
            );
        }
        return new DefaultPopulation<>(offspring);
    }
}
