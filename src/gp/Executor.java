package gp;

import help.Config;
import tree.GeneticTree;
import tree.GeneticTreeComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Nils on 20.01.2017.
 */
public class Executor {
    private final ExecutorService executorService;
    private final List<Evolution> evolutions;

    public Executor(final int numRuns) {
        this(numRuns, Runtime.getRuntime().availableProcessors());
    }

    public Executor(final int numRuns, final int numThreads) {
        final long start = System.currentTimeMillis();

        executorService = Executors.newFixedThreadPool(numThreads);
        evolutions = new ArrayList<>();

        for (int i = 0; i < numRuns; i++) {
            evolutions.add(new Evolution(new Genome(Config.INIT, Config.GENECOUNT)));
        }

        try {
            final List<Future<Genome>> futures = executorService.invokeAll(evolutions);

            System.out.println();

            Gene overallBest = null;

            for (int i = 0; i < numRuns; i++) {
                final Genome genome = futures.get(i).get();

                genome.sort();

                final Gene best = genome.get(genome.getBestGeneIndex());

                if (overallBest == null || best.getFitness() < overallBest.getFitness())
                    overallBest = best;

                System.out.println("\n------------  Evolution " + evolutions.get(i).id + "  ------------");
                System.out.println(best.getFunction());
                System.out.println("[fitness: " + best.getFitness() + "]");
            }

            System.out.println("\nOVERALL BEST GENE: " + overallBest);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("\n>>> DONE in " + (Math.round((System.currentTimeMillis() - start) / 100d) / 10d) + "s <<<");
    }
}
