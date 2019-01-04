package com.elbraulio.neuroevolution.snake;

import com.elbraulio.genetical.Individual;
import com.elbraulio.genetical.PrintSolution;
import com.elbraulio.neuralnet.network.NeuralNetwork;
import com.elbraulio.neuralnet.unit.NeuralUnit;
import com.elbraulio.neuralnet.utils.Normalize;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class NeuralPrint implements PrintSolution<NeuralUnit> {
    private final int x;
    private final int y;
    private final double learningRate;
    private final int outputLength;
    private final int[] hiddenLength;

    public NeuralPrint(int x, int y, double learningRate, int outputLength,
                       int[] hiddenLength) {
        this.x = x;
        this.y = y;
        this.learningRate = learningRate;
        this.outputLength = outputLength;
        this.hiddenLength = hiddenLength;
    }

    int gen = 1;

    @Override
    public void print(Individual<NeuralUnit> individual) {
        Thread t = new Thread(
                () -> {
                    long clock = 100;
                    JFrame frame = new JFrame();
                    GridLayout grid = new GridLayout(x, y, 0, 0);
                    JPanel panel = new JPanel(grid);
                    SnakeGame<SnakeAction> game = new DefaultSnakeGame(x, y);
                    updateGrid(panel, game.snake(), game.treat());
                    JPanel scorePanel = new JPanel();
                    JLabel text = new JLabel("Score: 0");
                    scorePanel.add(text);
                    JPanel nextPan = new JPanel();
                    JButton nextBtn = new JButton("Skip");
                    boolean[] skip = new boolean[]{false};
                    nextBtn.addActionListener(e -> skip[0] = true);
                    nextPan.add(nextBtn);
                    frame.setLayout(new BorderLayout());
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.add(scorePanel, BorderLayout.NORTH);
                    frame.add(panel, BorderLayout.CENTER);
                    frame.add(nextPan, BorderLayout.SOUTH);
                    frame.pack();
                    frame.setLocationByPlatform(true);
                    frame.setVisible(true);

                    game = new DefaultSnakeGame(x, y);
                    updateGrid(panel, game.snake(), game.treat());
                    NeuralNetwork net = new MakeNetwork(learningRate, outputLength,
                            hiddenLength).net(individual.genes());
                    while (!game.isOver()) {
                        Number[] out = net.feed(
                                normalize(game.straightDistance(), x),
                                normalize(game.rightDistance(), x),
                                normalize(game.leftDistance(), x),
                                game.isTreatLeft(),
                                game.isTreatRight(),
                                game.isTreatStraight(),
                                normalize(game.eastDistance(), x),
                                normalize(game.westDistance(), x),
                                normalize(game.northDistance(), x),
                                normalize(game.southDistance(), x),
                                normalize(game.northEastDistance(), x + y),
                                normalize(game.northWestDistance(), x + y),
                                normalize(game.southEastDistance(), x + y),
                                normalize(game.southWestDistance(), x + y),
                                game.left(),
                                game.right(),
                                game.down(),
                                game.up()
                        );
                        int n;
                        if (out[0].doubleValue() >= out[1].doubleValue() && out[0].doubleValue() >= out[2].doubleValue()) {
                            n = 0;
                        } else if (out[1].doubleValue() >= out[0].doubleValue() && out[1].doubleValue() >= out[2].doubleValue()) {
                            n = 1;
                        } else {
                            n = 2;
                        }
                        try {
                            Thread.sleep(clock);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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
                        updateGrid(panel, game.snake(), game.treat());
                        text.setText("Gen: " + gen + " | Score: " + game.score());
                        scorePanel.updateUI();
                        if (game.isOver() || skip[0]) {
                            frame.setVisible(false);
                            frame.dispose();
                            Thread.currentThread().stop();
                        }
                    }
                }
        );
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gen++;
    }

    private void updateGrid(JPanel panel, List<DefaultSnakeGame.Position> snake, DefaultSnakeGame.Position treat) {
        panel.removeAll();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (snake.contains(new DefaultSnakeGame.Position(i, j)) || treat.equals(new DefaultSnakeGame.Position(i, j))) {
                    panel.add(makePane("white"));
                } else {
                    panel.add(makePane("black"));
                }
            }
        }
        panel.updateUI();
    }

    private Component makePane(String black) {
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

    private Number normalize(Number raw, Number dataMax) {
        return new Normalize(raw, 0, dataMax, 0, 1);
    }
}
