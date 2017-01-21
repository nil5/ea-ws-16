package gp;

import help.Config;
import tree.GeneticTree;
import tree.GeneticTreeComponent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Nils on 20.01.2017.
 */
public class Executor {
    private final ExecutorService executorService;
    private final Evolution[] evolutions;

    public Executor(final int numRuns) {
        this(numRuns, Config.MODE_HALF);
    }

    public Executor(final int numRuns, final int buildMode) {
        this(numRuns, buildMode, Runtime.getRuntime().availableProcessors());
    }

    public Executor(final int numRuns, final int buildMode, final int numThreads) {
        executorService = Executors.newFixedThreadPool(numThreads);
        evolutions = new Evolution[numRuns];

        for (int i = 0; i < numRuns; i++) {
            evolutions[i] = new Evolution(new Genome(buildMode, 100));
            executorService.execute(evolutions[i]);
        }
    }
}
