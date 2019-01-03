package com.elbraulio.neuroevolution.snake;

import com.elbraulio.genetical.Mutation;
import com.elbraulio.neuralnet.unit.NeuralUnit;
import com.elbraulio.neuralnet.unit.Sigmoid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class NeuralMutation implements Mutation<NeuralUnit> {
    private final double biasMutation;
    private final double weightMutation;
    private final double unitMutation;
    private final Random random;

    public NeuralMutation(double biasMutation, double weightMutation, double unitMutation) {
        this(biasMutation, weightMutation, unitMutation, new Random());
    }

    public NeuralMutation(double biasMutation, double weightMutation, double unitMutation, Random random) {

        this.biasMutation = biasMutation;
        this.weightMutation = weightMutation;
        this.unitMutation = unitMutation;
        this.random = random;
    }

    @Override
    public List<NeuralUnit> genes(List<NeuralUnit> origin) {
        final List<NeuralUnit> genes = new ArrayList<>(origin.size());
        for (int i = 0; i < origin.size(); i++) {
            Number bias = origin.get(i).args().bias();
            Number[] weights = origin.get(i).args().weights();
            if (this.random.nextDouble() < this.unitMutation) {
                if (this.random.nextDouble() < this.biasMutation) {
                    bias = bias.doubleValue() +
                            this.random.nextDouble() * Math.pow(
                                    -1, this.random.nextInt()
                            );
                }
                for (int j = 0; j < weights.length; j++) {
                    if (this.random.nextDouble() < this.weightMutation) {
                        weights[j] = weights[j].doubleValue() +
                                this.random.nextDouble() * Math.pow(
                                        -1, this.random.nextInt()
                                );
                    }
                }

            }
            genes.add(new Sigmoid(bias, weights));
        }
        return genes;
    }
}
