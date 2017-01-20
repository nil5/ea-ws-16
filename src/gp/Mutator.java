package gp;

import help.Config;

/**
 * Created by Nils on 19.01.2017.
 */
public abstract class Mutator {
    public final boolean protectBest;
    public final int inputGeneCount;
    public final int outputGeneCount;

    public Mutator(final int inputGeneCount, final int outputGeneCount, final boolean protectBest) {
        this.inputGeneCount = inputGeneCount;
        this.outputGeneCount = outputGeneCount;
        this.protectBest = protectBest;
    }

    public abstract void mutate(final Genome genome);
}
