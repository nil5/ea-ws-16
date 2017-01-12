package help;

import functions.*;
import terminals.ConstantTerminal;
import terminals.RandomTerminal;
import terminals.Terminal;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Laura on 12.01.2017.
 */
public class Helper {
    final Function[] functionSet = new Function[] {
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

    public Terminal getRandomTerminal() {
        try {
            return terminalSet[ThreadLocalRandom.current().nextInt(0, terminalSet.length)];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Function getRandomFunction() {
        try {
            return functionSet[ThreadLocalRandom.current().nextInt(0, functionSet.length)];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
