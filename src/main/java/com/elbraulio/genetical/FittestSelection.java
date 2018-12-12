package com.elbraulio.genetical;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface FittestSelection<T> {
    Individual<T> fittest(List<Individual<T>> individuals);
}
