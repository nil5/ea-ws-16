package terminals;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nils on 19.01.2017.
 */
public class IOTerminalSet {
    //public final int inputCount;
    //public final Terminal output;
    //public final Terminal[] inputs;
    public final int lineCount;
    public final double[] inputs;
    public final double[] outputs;

    /*public IOTerminalSet(final List<String> columns) {
        inputCount = columns.size() - 1;
        inputs = new Terminal[inputCount];
        output = new ConstantTerminal(Double.valueOf(columns.get(inputCount)));
        for (int i = 0; i < inputCount; i++) inputs[i] = new ConstantTerminal(Double.valueOf(columns.get(i)));
    }*/

    public IOTerminalSet(List<List<String>> values) {
       lineCount = values.size();
        inputs = new double[lineCount];
        outputs = new double[lineCount];
        for (int i = 0; i < lineCount; i++) {
            final List<String> line = values.get(i);
            inputs[i] = Double.valueOf(line.get(0));
            outputs[i] = Double.valueOf(line.get(1));
        }
    }

    /*public Terminal getInputTerminal(final int index) {
        return inputs[index];
    }*/
}
