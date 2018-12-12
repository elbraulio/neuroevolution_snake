package com.elbraulio.genetical.fittestseleccion;

import com.elbraulio.genetical.FittestSelection;
import com.elbraulio.genetical.Individual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class SubsetOf<T> implements FittestSelection<T> {

    private final int participants;
    private final FittestSelection<T> origin;

    public SubsetOf(int participants, FittestSelection<T> origin) {

        this.participants = participants;
        this.origin = origin;
    }

    @Override
    public Individual<T> fittest(List<Individual<T>> individuals) {
        if (this.participants > individuals.size())
            throw new IllegalArgumentException(
                    "tournament can not be greater than population"
            );
        final List<Individual<T>> subset = new ArrayList<>(this.participants);
        final List<Individual<T>> clone = new ArrayList<>(individuals);
        for (int i = 0; i < this.participants; i++) {
            final int index = new Random().nextInt(clone.size());
            subset.add(
                    clone.get(index)
            );
            clone.remove(index);
        }
        return this.origin.fittest(subset);
    }
}
