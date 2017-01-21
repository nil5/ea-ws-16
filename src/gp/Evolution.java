package gp;

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
        final Mutator mutation = new RandomMutator(0.01, true);
        final Mutator crossover = new SubTreeCrossover(0.9, true, 2);

        for (int i = 0; i < 10; i++) {
            mutation.mutate(genome);
            crossover.mutate(genome);

            final Gene fittest = genome.get(genome.getBestGeneIndex());
            System.out.println(fittest);
            fittest.print();
        }
    }
}
