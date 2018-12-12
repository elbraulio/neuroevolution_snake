package com.elbraulio.genetical;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface Population <T> {
    Population<T> evolve(Evolution<T> evolution);
    Individual<T> fittest(FittestSelection<T> selection);
    List<Individual<T>> individuals();
}
