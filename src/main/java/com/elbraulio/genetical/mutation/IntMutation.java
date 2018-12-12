package com.elbraulio.genetical.mutation;

import com.elbraulio.genetical.Mutation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class IntMutation implements Mutation<Integer> {
    private final double threshold;
    private final Random randomness;
    private final int bound;

    public IntMutation(double threshold, Random randomness, int bound) {
        this.threshold = threshold;
        this.randomness = randomness;
        this.bound = bound;
    }

    @Override
    public List<Integer> genes(List<Integer> origin) {
        if (this.threshold >= 1)
            throw new IllegalArgumentException(
                    "mutation threshold must be in range [0, 1)"
            );
        final List<Integer> mutation = new ArrayList<>(origin.size());
        for (Integer gene : origin) {
            if (this.threshold > Math.random()) {
                mutation.add(
                        this.randomness.nextInt(this.bound)
                );
            } else {
                mutation.add(gene);
            }
        }
        return mutation;
    }
}
