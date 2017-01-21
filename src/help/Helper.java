package help;

import functions.*;
import io.CSVUtils;
import terminals.IOTerminalSet;
import terminals.RandomTerminal;
import terminals.Terminal;
import tree.GeneticTreeComponent;
import tree.GeneticTreeNode;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Laura on 12.01.2017.
 */
public class Helper {
    public static final Function[] functionSet = new Function[] {
            new AddFunction(),
            new SubtractFunction(),
            new MultiplyFunction(),
            new DivideFunction(),
            new SinusFunction(),
            new CosinusFunction(),
            new ExpFunction()
    };

    private static IOTerminalSet[] inputOutputSets = null;

    public static Function getRandomFunction() {
        try {
            return functionSet[rand(0, functionSet.length)];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Terminal getRandomTerminal() {
        return new RandomTerminal(-5.0, 5.0);
    }

    public static Object getRandomObject() {
        return rand(0, 1) == 0 ? functionSet[rand(0, functionSet.length)] : getRandomTerminal();
    }

    public static IOTerminalSet[] getIOSets() {
        if (inputOutputSets == null) {
            final List<List<String>> values;

            try {
                values = CSVUtils.parseFile("values.csv");
            } catch (IOException e) {
                System.out.println("Could not read values file.");
                e.printStackTrace();
                return null;
            }

            inputOutputSets = new IOTerminalSet[values.size()];

            for (int i = 0; i < inputOutputSets.length; i++) {
                final List<String> line = values.get(i);

                inputOutputSets[i] = new IOTerminalSet(line);

                System.out.println("Line " + (i + 1) + ": " + line.get(0) + " => " + line.get(1));
            }
        }

        return inputOutputSets;
    }

    public static int rand(final int min, final int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static void iterateTree(GeneticTreeComponent component, List<GeneticTreeComponent> componentList){
        componentList.add(component);

        if (component.type == Config.NODE) {
            final List<GeneticTreeComponent> children = ((GeneticTreeNode) component).getChildren();
            for (int i = 0, c = children.size(); i < c; i++) {
                iterateTree(children.get(i), componentList);
            }
        }
    }
}
