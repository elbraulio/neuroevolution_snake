package com.elbraulio.genetical.crosses;

import com.elbraulio.genetical.Crosses;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class RandomCross<T> implements Crosses<T> {
    private final double threshold;

    public RandomCross(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public List<T> genes(List<T> genesA, List<T> genesB) {
        if (this.threshold >= 1 || this.threshold < 0)
            throw new IllegalArgumentException(
                    "mutation threshold must be in range [0, 1)"
            );
        final List<T> newGenes = new ArrayList<>(genesA.size());
        for (int i = 0; i < genesA.size(); i++) {
            newGenes.add(
                    Math.random() < this.threshold ?
                            genesA.get(i) : genesB.get(i)
            );
        }
        return newGenes;
    }
}
