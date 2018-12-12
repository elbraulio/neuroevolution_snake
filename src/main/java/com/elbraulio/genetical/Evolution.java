package com.elbraulio.genetical;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface Evolution<T> {
    Population<T> nextGeneration(List<Individual<T>> individuals);
}
