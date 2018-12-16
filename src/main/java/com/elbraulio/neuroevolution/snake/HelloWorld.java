package com.elbraulio.neuroevolution.snake;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class HelloWorld {
    public static void main(String... args) throws InterruptedException {
        SnakeGame<SnakeAction> game = new DefaultSnakeGame(5,5);
        Thread.sleep(2000);
        game.action(SnakeAction.GO_STRAIGHT);
        Thread.sleep(2000);
        game.action(SnakeAction.TURN_RIGHT);
        Thread.sleep(2000);
        game.action(SnakeAction.GO_STRAIGHT);
        Thread.sleep(2000);
        game.action(SnakeAction.GO_STRAIGHT);
        Thread.sleep(2000);
        game.action(SnakeAction.TURN_LEFT);
        Thread.sleep(2000);
        game.action(SnakeAction.TURN_RIGHT);
        Thread.sleep(2000);
        game.action(SnakeAction.TURN_RIGHT);
        Thread.sleep(2000);
        game.action(SnakeAction.GO_STRAIGHT);
        Thread.sleep(2000);
        game.action(SnakeAction.GO_STRAIGHT);
        Thread.sleep(2000);
        game.action(SnakeAction.GO_STRAIGHT);
        Thread.sleep(2000);
        game.action(SnakeAction.TURN_RIGHT);
        Thread.sleep(2000);
        game.action(SnakeAction.GO_STRAIGHT);
        Thread.sleep(2000);
        game.action(SnakeAction.GO_STRAIGHT);
    }
}
