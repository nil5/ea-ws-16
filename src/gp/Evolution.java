package gp;

import help.Config;

/**
 * Created by Nils on 19.01.2017.
 */
public class Evolution implements Runnable {
    private final Genome genome;

    public Evolution(final Genome genome) {
        this.genome = genome;
    }

    @Override
    public void run() {
        final Mutator crossoverMutator = new SubTreeCrossover(true);

        for (int i = 0; i < Config.GENERATIONCOUNT; i++) {
            genome.mutate();

            crossoverMutator.mutate(genome);
        }
    }
}
