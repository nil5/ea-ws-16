import gp.Evolution;
import gp.Genome;
import help.Config;
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
        final IOTerminalSet testTerminal;
        final List<List<String>> values;

        try {
            values = CSVUtils.parseFile(Config.FILENAME);
        } catch (IOException e) {
            System.out.println("Could not read values file.");
            e.printStackTrace();
            return;
        }

        final int lineCount = values.size();

        testTerminal = new IOTerminalSet(values);
        //testTerminal = new IOTerminalSet[values];

        //for (int i = 0; i < lineCount; i++) testTerminals[i] = new IOTerminalSet(values.get(i));

        Genome genome = new Genome(Config.MODE_HALF, testTerminal, true);
/*
        for (int i = 0; i < lineCount; i++) {
            final List<String> line = values.get(i);

            System.out.println("Line " + (i + 1) + ": " + line.get(0) + " => " + line.get(1));
        }
*/

        final Evolution evolution = new Evolution(genome);
        final Thread evolutionThread = new Thread(evolution);

        evolutionThread.start();
    }
}
