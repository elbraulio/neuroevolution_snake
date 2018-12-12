package com.elbraulio.genetical.fittestseleccion;

import com.elbraulio.genetical.CheckSolution;
import com.elbraulio.genetical.FittestSelection;
import com.elbraulio.genetical.Individual;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class FittestByScore<T> implements FittestSelection<T> {

    private final CheckSolution<T> check;

    public FittestByScore(CheckSolution<T> check) {

        this.check = check;
    }

    @Override
    public Individual<T> fittest(List<Individual<T>> individuals) {
        Individual<T> fittest = null;
        int maxScore = 0;
        for (Individual<T> individual : individuals) {
            int score = this.check.score(individual.genes());
            if (score >= maxScore) {
                maxScore = score;
                fittest = individual;
            }
        }
        return fittest;
    }
}
