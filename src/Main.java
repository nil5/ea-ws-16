import functions.*;
import genome.Genome;
import io.CSVUtils;
import terminals.ConstantTerminal;
import terminals.RandomTerminal;
import terminals.Terminal;
import tree.GeneticTree;
import tree.GeneticTreeBuilder;

import java.io.IOException;
import java.util.Dictionary;
import java.util.List;

/**
 * Created by Nils on 10.01.2017.
 */
public class Main {
    private static double[] input, output;

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        final List<List<String>> values;
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

        Genome genome = new Genome(4,2,1,0.9);
        genome.mutate();

        try {
            values = CSVUtils.parseFile("values.csv");
        } catch (IOException e) {
            System.out.println("Could not read values file.");
            e.printStackTrace();
            return;
        }

        final int size = values.size();

        input = new double[size];
        output = new double[size];

        for (int i = 0; i < size; i++) {
            final List<String> line = values.get(i);

            //System.out.println("Line " + (i + 1) + ": " + line.get(0) + " => " + line.get(1));

            input[i] = Double.valueOf(line.get(0));
            output[i] = Double.valueOf(line.get(1));
        }
    }
}
