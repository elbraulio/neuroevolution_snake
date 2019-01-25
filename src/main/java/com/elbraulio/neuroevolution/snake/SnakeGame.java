package com.elbraulio.neuroevolution.snake;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface SnakeGame<T extends GameAction> {

    List<DefaultSnakeGame.Position> snake();

    void action(T action);

    int score();

    Number upDistance();

    Number rightDistance();

    Number leftDistance();

    Number downDistance();

    DefaultSnakeGame.Position treat();

    Number isTreatRight();

    Number isTreatLeft();

    Number isTreatUp();

    Number isTreatDown();

    boolean isOver();

    Number eastDistance();

    Number westDistance();

    Number northDistance();

    Number southDistance();

    Number northEastDistance();

    Number northWestDistance();

    Number southEastDistance();

    Number southWestDistance();

    Number left();

    Number right();

    Number down();

    Number up();
}
