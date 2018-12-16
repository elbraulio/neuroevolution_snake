package com.elbraulio.neuroevolution.snake;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface SnakeGame<T extends GameAction> {

    void action(T action);
}
