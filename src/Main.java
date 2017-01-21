import gp.Evolution;
import gp.Genome;
import io.CSVUtils;
import terminals.IOTerminalSet;
import tree.GeneticTree;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Nils on 10.01.2017.
 */
public class Main {
    private static double[] input, output;

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Genome genome = new Genome(GeneticTree.MODE_HALF, testTerminals,2,1,0.9, true);

        final Evolution evolution = new Evolution(genome);
        final Thread evolutionThread = new Thread(evolution);

        evolutionThread.start();
    }
}
