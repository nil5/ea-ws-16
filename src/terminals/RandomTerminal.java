package terminals;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Nils on 10.01.2017.
 */
public class RandomTerminal implements Terminal {
    private final double value;

    public RandomTerminal(final double start, final double end) {
        value = ThreadLocalRandom.current().nextDouble(start, end);
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "RANDOM Terminal " + value;
    }
}
