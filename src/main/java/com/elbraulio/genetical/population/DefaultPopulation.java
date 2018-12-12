package com.elbraulio.genetical.population;

import com.elbraulio.genetical.Evolution;
import com.elbraulio.genetical.FittestSelection;
import com.elbraulio.genetical.Individual;
import com.elbraulio.genetical.Population;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class DefaultPopulation<T> implements Population<T> {
    private final List<Individual<T>> individuals;

    public DefaultPopulation(List<Individual<T>> individuals) {
        this.individuals = individuals;
    }

    @Override
    public Population<T> evolve(Evolution<T> evolution) {
        return evolution.nextGeneration(this.individuals);
    }

    @Override
    public Individual<T> fittest(FittestSelection<T> selection) {
        return selection.fittest(this.individuals);
    }

    @Override
    public List<Individual<T>> individuals() {
        return this.individuals;
    }
}
