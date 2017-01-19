package terminals;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nils on 19.01.2017.
 */
public class IOTerminalSet {
    public final int inputCount;
    public final Terminal output;
    public final Terminal[] inputs;

    public IOTerminalSet(final List<String> columns) {
        inputCount = columns.size() - 1;
        inputs = new Terminal[inputCount];
        output = new ConstantTerminal(Double.valueOf(columns.get(inputCount)));
        for (int i = 0; i < inputCount; i++) inputs[i] = new ConstantTerminal(Double.valueOf(columns.get(i)));
    }

    public Terminal getInputTerminal(final int index) {
        return inputs[index];
    }
}
