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
    public static final Function[] functionSet = new Function[] {
            new AddFunction(),
            new SubtractFunction(),
            new MultiplyFunction(),
            new DivideFunction(),
            new SinusFunction(),
            new CosinusFunction(),
            new ExpFunction()
    };

    public static final Terminal[] terminalSet = new RandomTerminal[] {
            new RandomTerminal(-5.0, 5.0),
            new RandomTerminal(-5.0, 5.0),
            new RandomTerminal(-5.0, 5.0)
    };

    public static Terminal getRandomTerminal() {
        try {
            return terminalSet[rand(0, terminalSet.length)];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Function getRandomFunction() {
        try {
            return functionSet[rand(0, functionSet.length)];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object getRandomObject() {
        final int r = rand(0, functionSet.length + terminalSet.length);
        return r < functionSet.length ? functionSet[r] : terminalSet[r - functionSet.length];
    }

    public static int rand(final int min, final int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
