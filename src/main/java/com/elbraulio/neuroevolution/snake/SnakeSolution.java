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
        while(!game.isOver() && game.score() <= minScore && steps < (x + y)*2) {
            Number[] out = net.feed(
                    normalize(game.straightDistance()),
                    normalize(game.rightDistance()),
                    normalize(game.leftDistance()),
                    game.isTreatLeft(),
                    game.isTreatRight(),
                    game.isTreatStraight()
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
        if (game.score() >= minScore){
            isFound = true;
        }
        return game.score();
    }

    private Number normalize(Number raw) {
        return new Normalize(raw, 0, x, 0, 1);
    }
}
