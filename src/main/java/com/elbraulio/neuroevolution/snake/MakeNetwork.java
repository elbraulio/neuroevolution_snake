package com.elbraulio.neuroevolution.snake;

import com.elbraulio.neuralnet.args.FromTimeline;
import com.elbraulio.neuralnet.epoch.DefaultEpoch;
import com.elbraulio.neuralnet.epoch.Epoch;
import com.elbraulio.neuralnet.network.DefaultNetwork;
import com.elbraulio.neuralnet.network.NeuralNetwork;
import com.elbraulio.neuralnet.timeline.DefaultTimeLine;
import com.elbraulio.neuralnet.timeline.TimeLine;
import com.elbraulio.neuralnet.unit.NeuralUnit;
import com.elbraulio.neuralnet.unit.SaveToTimeline;
import com.elbraulio.neuralnet.unit.Sigmoid;
import com.elbraulio.neuralnet.utils.MatrixByLength;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class MakeNetwork {


    private final double learningRate;
    private final int outputLength;
    private final int[] hiddenLength;

    public MakeNetwork(double learningRate, int outputLength, int[] hiddenLength) {

        this.learningRate = learningRate;
        this.outputLength = outputLength;
        this.hiddenLength = hiddenLength;
    }

    public NeuralNetwork net(List<NeuralUnit> genes) {
        Number[][] bias = new MatrixByLength(this.outputLength,
                this.hiddenLength).matrix();
        for (int i = 0; i < this.hiddenLength.length; i++) {
            for (int j = 0; j < this.hiddenLength[i]; j++) {
                bias[i][j] =
                        genes.get(valueOf(i, j, hiddenLength).intValue()).args().bias();
            }
        }
        for (int i = 0; i < this.outputLength; i++) {
            bias[this.hiddenLength.length][i] =
                    genes.get(
                            genes.size() - 1 - (outputLength - i)
                    ).args().bias();
        }
        List<List<Number[]>> weights = new ArrayList<>();
        weights.add(new ArrayList<>(this.hiddenLength[0]));
        for (int j = 0; j < this.hiddenLength[0]; j++) {
            weights.get(0).add(genes.get(j).args().weights());
        }
        for (int i = 1; i < this.hiddenLength.length; i++) {
            weights.add(new ArrayList<>(this.hiddenLength[i]));
            for (int j = 0; j < this.hiddenLength[i]; j++) {
                weights.get(i).add(genes.get(valueOf(i, j, hiddenLength).intValue()).args().weights());
            }
        }
        weights.add(new ArrayList<>(this.outputLength));
        for (int i = 0; i < this.outputLength; i++) {
            weights.get(this.hiddenLength.length).add(genes.get(genes.size() - 1 - (outputLength - i)).args().weights());
        }
        Epoch epoch = new DefaultEpoch(bias, weights);
        TimeLine timeLine = new DefaultTimeLine(epoch);
        final NeuralUnit[] units = new NeuralUnit[
                IntStream.of(this.hiddenLength).sum() +
                        this.outputLength
                ];
        int neuronIndex = 0;
        for (int layer = 0; layer < this.hiddenLength.length; layer++) {
            for (int i = 0; i < this.hiddenLength[layer]; i++) {
                units[neuronIndex++] = new SaveToTimeline(
                        new Sigmoid(
                                new FromTimeline(
                                        timeLine,
                                        layer,
                                        i
                                )
                        ),
                        layer, i, timeLine
                );
            }
        }
        for (int i = 0; i < this.outputLength; i++) {
            units[neuronIndex++] = new SaveToTimeline(
                    new Sigmoid(
                            new FromTimeline(
                                    timeLine,
                                    this.hiddenLength.length,
                                    i
                            )
                    ), this.hiddenLength.length, i, timeLine
            );
        }
        return new DefaultNetwork(
                learningRate, outputLength, timeLine, units, hiddenLength
        );
    }

    private Number valueOf(int i, int j, int[] hiddenLength) {
        int index = 0;
        for (int k = 0; k <= i; k++) {
            index += hiddenLength[k];
        }
        return index - (hiddenLength[i] - j);
    }
}
