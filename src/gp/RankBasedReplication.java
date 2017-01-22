package gp;

/**
 * Created by Nils on 22.01.2017.
 */
public class RankBasedReplication implements Replication {
    private final double[] psKum;
    private final int numGenes;
    private final boolean protectBest;


    public RankBasedReplication(final int numGenes, final boolean protectBest) {
        this.numGenes = numGenes;
        this.protectBest = protectBest;

        final double[] ps = new double[numGenes];
        final double s = 2;

        double minPsKum = Double.MAX_VALUE;
        double maxPsKum = Double.MIN_VALUE;

        psKum = new double[numGenes];

        for (int i = numGenes - 1; i >= 0; i--) {
            final int r = numGenes - 1 - i;

            ps[r] = ((2d - s) / ((double) numGenes)) + ((2d * (double) r * (s - 1d)) / ((double) numGenes * ((double) numGenes - 1d)));
            psKum[r] = ps[r] + (r > 0 ? psKum[r - 1] : 0);

            if (psKum[r] < minPsKum) minPsKum = psKum[r];
            if (psKum[r] > maxPsKum) maxPsKum = psKum[r];
        }

        maxPsKum -= minPsKum;

        for (int i = 0; i < psKum.length; i++) {
            psKum[i] = (psKum[i] - minPsKum) / maxPsKum;
        }
    }

    @Override
    public Genome replicate(Genome genome) {
        final Gene[] genes = new Gene[genome.length];

        genome.sort();

        int i = 0;
        if (protectBest) {
            genes[0] = new Gene(genome.get(0));
            i++;
        }

        for (; i < numGenes; i++) {
            final double r = Math.random();

            int j = 0;
            double lower = 0;

            for (; j < psKum.length; j++) {
                if (j > 0) lower = psKum[j - 1];
                if (r >= lower && r < psKum[j]) break;
            }

            genes[i] = new Gene(genome.get(numGenes - j - 1));
        }

        return new Genome(genes);
    }
}
