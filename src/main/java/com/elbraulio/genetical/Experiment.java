package com.elbraulio.genetical;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface Experiment<T> {
    void run(int tournamentSize, int minScore, Population<T> startPop);
}
