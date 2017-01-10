import functions.*;
import io.CSVUtils;

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
        final Class[] functionSet = new Class[] {
                AddFunction.class,
                SubtractFunction.class,
                MultiplyFunction.class,
                DivideFunction.class,
                SinusFunction.class,
                CosinusFunction.class,
                ExpFunction.class
        };

        System.out.println(functionSet[1].newInstance());

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

            System.out.println("Line " + (i + 1) + ": " + line.get(0) + " => " + line.get(1));

            input[i] = Double.valueOf(line.get(0));
            output[i] = Double.valueOf(line.get(1));
        }
    }
}
