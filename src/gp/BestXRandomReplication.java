package gp;

import help.Helper;

/**
 * Created by Nils on 22.01.2017.
 */
public class BestXRandomReplication implements Replication {
    public final int x;
    public final double p;

    public BestXRandomReplication(final int x, final double p) {
        this.x = x;
        this.p = p;
    }

    @Override
    public Genome replicate(final Genome genome) {
        final Gene[] genes = new Gene[genome.length];
        final int halfSize = (int) (genes.length * p);

        genome.sort();

        int i = 0;
        for (; i < halfSize; i++) {
            genes[i] = new Gene(genome.get(i % x));
        }
        for (; i < genes.length; i++) {
            genes[i] = new Gene(genome.get(Helper.rand(0, genome.length)));
        }

        return new Genome(genes);
    }
}
