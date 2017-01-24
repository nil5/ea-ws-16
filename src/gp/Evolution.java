package gp;

import help.Config;
import main.Main;

import java.util.concurrent.Callable;

/**
 * Created by Nils on 19.01.2017.
 */
public class Evolution implements Callable<Genome> {
    private static int idCounter = 1;

    public final int id = idCounter++;

    private Genome genome;

    public Evolution(final Genome genome) {
        this.genome = genome;
    }

    @Override
    public Genome call() {
        final Mutator subTreeMutator = new SubTreeMutator(Config.MUTATIONRATE, Config.PROTECT_BEST);
        final Mutator randomMutation = new RandomMutator(Config.MUTATIONRATE, Config.PROTECT_BEST);
        final Mutator swapMutator = new SwapMutator(Config.MUTATIONRATE, Config.PROTECT_BEST);
        final Mutator crossover = new SubTreeCrossover(Config.RECOMBINATIONRATE, Config.PROTECT_BEST, Config.TOURNAMENTSIZE);
        //final Replication replication = new BestXRandomReplication(2, 0.5);
        final Replication replication = new RankBasedReplication(genome.length, Config.PROTECT_BEST);
        Gene winner = null;

        for (int i = 0; i < Config.GENERATIONCOUNT; i++) {
            genome = replication.replicate(genome);

            switch (i % 3) {
                case 0:
                    subTreeMutator.mutate(genome);
                    break;
                case 1:
                    randomMutation.mutate(genome);
                    break;
                case 2:
                    swapMutator.mutate(genome);
                    break;
            }
            crossover.mutate(genome);

            final Gene fittest = genome.get(genome.getBestGeneIndex());
            // System.out.println(genome);

            if (winner == null || fittest.getFitness() < winner.getFitness()) {
                winner = fittest;
            }

            final double fitness = Math.round(winner.getFitness() * 10000000) / 100000000;

            Main.updateProgress(id, i + 1, fitness, id, i + 1);
            if (fitness == 0) break;
        }

        return genome;
    }
}
