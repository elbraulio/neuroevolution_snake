package com.elbraulio.genetical.fittestseleccion;

import com.elbraulio.genetical.FittestSelection;
import com.elbraulio.genetical.Individual;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class FittestTwin<T> implements FittestSelection<T> {
    private final List<T> fittest;

    public FittestTwin(List<T> fittest) {
        this.fittest = fittest;
    }

    @Override
    public Individual<T> fittest(List<Individual<T>> individuals) {
        Individual<T> fittest = null;
        int maxScore = 0;
        for (Individual<T> individual : individuals) {
            int score = 0;
            for (int i = 0; i < individual.genes().size(); i++) {
                if (individual.genes().get(i).equals(this.fittest.get(i)))
                    score++;
            }
            if (score >= maxScore) {
                maxScore = score;
                fittest = individual;
            }
        }
        return fittest;
    }
}
