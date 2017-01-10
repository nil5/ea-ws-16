import io.CSVUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by Nils on 10.01.2017.
 */
public class Main {
    public static void main(String[] args) {
        final List<List<String>> values;

        try {
            values = CSVUtils.parseFile("values.csv");
        } catch (IOException e) {
            System.out.println("Could not read values file.");
            e.printStackTrace();
            return;
        }

        for (int i = 0; i < values.size(); i++) {
            final List<String> line = values.get(i);
            System.out.println("Line " + (i + 1) + ": " + line.get(0) + " => " + line.get(1));
        }
    }
}
