package com.elbraulio.neuroevolution.snake;

import com.elbraulio.genetical.CheckSolution;
import com.elbraulio.neuralnet.network.NeuralNetwork;
import com.elbraulio.neuralnet.unit.NeuralUnit;
import com.elbraulio.neuralnet.utils.Normalize;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class SnakeSolution implements CheckSolution<NeuralUnit> {
    public static boolean isFound = false;
    private final double learningRate;
    private final int outputLength;
    private final int[] hiddenLength;
    private final int x;
    private final int y;
    private final int minScore;

    public SnakeSolution(
            double learningRate, int outputLength,
            int[] hiddenLength, int x, int y, int minScore
    ) {
        this.learningRate = learningRate;
        this.outputLength = outputLength;
        this.hiddenLength = hiddenLength;
        this.x = x;
        this.y = y;
        this.minScore = minScore;
    }

    @Override
    public int score(List<NeuralUnit> genes) {

        NeuralNetwork net =
                new MakeNetwork(learningRate, outputLength, hiddenLength).net(genes);
        SnakeGame<SnakeAction> game = new DefaultSnakeGame(x, y);
        int steps = 0;
        while (!game.isOver() && game.score() <= minScore && steps < x * y) {
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
            steps++;
        }
        if (game.score() >= minScore) {
            isFound = true;
        }
        return game.score();
    }

    private Number normalize(Number raw, Number dataMax) {
        return new Normalize(raw, 0, dataMax, 0, 1);
    }
}
