package gp;

import help.Config;

import java.util.concurrent.Callable;

/**
 * Created by Nils on 19.01.2017.
 */
public class Evolution implements Callable<Genome> {
    private final Genome genome;

    public Evolution(final Genome genome) {
        this.genome = genome;
    }

    @Override
    public Genome call() {
        final Mutator mutation = new RandomMutator(Config.MUTATIONRATE, Config.PROTECT_BEST);
        final Mutator crossover = new SubTreeCrossover(Config.RECOMBINATIONRATE, Config.PROTECT_BEST, Config.TOURNAMENTSIZE);
        Gene winner = null;

        for (int i = 0; i < Config.GENERATIONCOUNT; i++) {
            mutation.mutate(genome);
            crossover.mutate(genome);

            final Gene fittest = genome.get(genome.getBestGeneIndex());
            // System.out.println(genome);

            if (winner == null || fittest.getFitness() < winner.getFitness())
                winner = fittest;
            System.out.println("GEN " + (i + 1) + ": " + fittest.getFitness());
        }

        System.out.println(winner);

        return genome;
    }
}
