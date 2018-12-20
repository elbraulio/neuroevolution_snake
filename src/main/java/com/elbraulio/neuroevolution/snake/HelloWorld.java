package com.elbraulio.neuroevolution.snake;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class HelloWorld {
    static int x = 50, y = 50;

    public static void main(String... args) throws InterruptedException {

        SnakeGame<SnakeAction> game = new DefaultSnakeGame(x, y);
        long clock = 1000;

        JFrame frame = new JFrame();
        GridLayout grid = new GridLayout(x, y, 0, 0);
        JPanel panel = new JPanel(grid);
        updateGrid(panel, game.snake());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

        int i = 100;
        Random random = new Random();
        while (i-- > 0) {
            Thread.sleep(clock);
            int n = random.nextInt(3);
            switch (n) {
                case 0:
                    game.action(SnakeAction.GO_STRAIGHT);
                    break;
                case 1:
                    game.action(SnakeAction.TURN_RIGHT);
                    break;
                case 2:
                    game.action(SnakeAction.TURN_LEFT);
                    break;
            }
            updateGrid(panel, game.snake());
        }
    }

    private static void updateGrid(JPanel panel, List<DefaultSnakeGame.Position> snake) {
        panel.removeAll();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (snake.contains(new DefaultSnakeGame.Position(i, j))) {
                    panel.add(makePane("white"));
                } else {
                    panel.add(makePane("black"));
                }
            }
        }
        panel.updateUI();
    }

    private static Component makePane(String black) {
        JPanel panel = new JPanel();
        switch (black) {
            case "black":
                panel.setPreferredSize(new Dimension(10, 10));
                panel.setBackground(Color.BLACK);
                return panel;
            case "white":
                panel.setPreferredSize(new Dimension(10, 10));
                panel.setBackground(Color.WHITE);
                return panel;
        }
        return null;
    }
}
