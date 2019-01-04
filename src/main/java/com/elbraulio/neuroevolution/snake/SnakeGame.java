package com.elbraulio.neuroevolution.snake;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface SnakeGame<T extends GameAction> {

    List<DefaultSnakeGame.Position> snake();

    void action(T action);

    int score();

    Number straightDistance();

    Number rightDistance();

    Number leftDistance();

    DefaultSnakeGame.Position treat();

    Number isTreatRight();

    Number isTreatLeft();

    Number isTreatStraight();

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
