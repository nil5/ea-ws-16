package terminals;

import java.util.List;

/**
 * Created by Nils on 19.01.2017.
 */
public class IOTerminalSet implements Terminal {
    public final int inputCount;
    public final Terminal output;
    public final Terminal[] inputs;

    public IOTerminalSet(final List<String> columns) {
        inputCount = columns.size() - 1;
        inputs = new Terminal[inputCount];
        output = new ConstantTerminal(Double.valueOf(columns.get(inputCount).replace(',', '.')));
        for (int i = 0; i < inputCount; i++) inputs[i] = new ConstantTerminal(Double.valueOf(columns.get(i).replace(',', '.')));
    }

    public Terminal getInputTerminal(final int index) {
        return inputs[index];
    }

    @Override
    public double getValue() {
        return output.getValue();
    }

    @Override
    public int getType() {
        return 3;
    }

    @Override
    public String toString() {
        return "IO Terminal set " + getValue();
    }
}
