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
        /*final Function[] functionSet = new Function[] {
                new AddFunction(),
                new SubtractFunction(),
                new MultiplyFunction(),
                new DivideFunction(),
                new SinusFunction(),
                new CosinusFunction(),
                new ExpFunction()
        };
        final Terminal[] terminalSet = new Terminal[] {
                new ConstantTerminal(4.0),
                new RandomTerminal(-5.0, 5.0),
                new RandomTerminal(-5.0, 5.0),
                new RandomTerminal(-5.0, 5.0)
        };

        final GeneticTreeBuilder builder = new GeneticTreeBuilder(2, terminalSet, functionSet);
        final GeneticTree tree = builder.build();*/

        final IOTerminalSet[] testTerminals;
        final List<List<String>> values;

        try {
            values = CSVUtils.parseFile("values.csv");
        } catch (IOException e) {
            System.out.println("Could not read values file.");
            e.printStackTrace();
            return;
        }

        final int lineCount = values.size();

        testTerminals = new IOTerminalSet[lineCount];

        for (int i = 0; i < lineCount; i++) testTerminals[i] = new IOTerminalSet(values.get(i));

        Genome genome = new Genome(GeneticTree.MODE_HALF, testTerminals,2,1,0.9, true);

        for (int i = 0; i < lineCount; i++) {
            final List<String> line = values.get(i);

            System.out.println("Line " + (i + 1) + ": " + line.get(0) + " => " + line.get(1));
        }

        final Evolution evolution = new Evolution(genome);
        final Thread evolutionThread = new Thread(evolution);

        evolutionThread.start();
    }
}
