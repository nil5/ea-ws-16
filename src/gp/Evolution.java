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
        final Mutator mutation = new RandomMutator(Config.MUTATIONRATE, Config.PROTECT_BEST);
        final Mutator crossover = new SubTreeCrossover(Config.RECOMBINATIONRATE, Config.PROTECT_BEST, Config.TOURNAMENTSIZE);

        for (int i = 0; i < Config.GENERATIONCOUNT; i++) {
            mutation.mutate(genome);
            crossover.mutate(genome);

            //final Gene fittest = genome.get(genome.getBestGeneIndex());
            System.out.println(genome);
        }
    }
}
