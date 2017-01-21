package gp;

import help.Config;
import tree.GeneticTree;
import tree.GeneticTreeComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        executorService = Executors.newFixedThreadPool(numThreads);
        evolutions = new ArrayList<>();

        for (int i = 0; i < numRuns; i++) {
            evolutions.add(new Evolution(new Genome(Config.MODE_GROW, Config.GENECOUNT)));
        }

        try {
            executorService.invokeAll(evolutions);
            System.out.println("DONE");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
