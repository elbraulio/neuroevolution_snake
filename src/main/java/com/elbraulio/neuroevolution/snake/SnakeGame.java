package com.elbraulio.neuroevolution.snake;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface SnakeGame<T extends GameAction> {

    List<DefaultSnakeGame.Position> snake();

    void action(T action);
}
