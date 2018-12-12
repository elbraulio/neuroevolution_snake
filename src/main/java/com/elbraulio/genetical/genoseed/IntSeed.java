package com.elbraulio.genetical.genoseed;

import com.elbraulio.genetical.GenotypeSeed;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class IntSeed implements GenotypeSeed<Integer> {
    private final int bound;
    private final Random randomness;

    public IntSeed(int bound, Random randomness) {

        this.bound = bound;
        this.randomness = randomness;
    }

    @Override
    public List<Integer> genes(int size) {
        final List<Integer> genes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            genes.add(this.randomness.nextInt(this.bound));
        }
        return genes;
    }
}
