package terminals;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Nils on 10.01.2017.
 */
public class RandomTerminal implements Terminal {
    private final double value;

    public RandomTerminal(final double start, final double end) {
        value = Math.round(ThreadLocalRandom.current().nextDouble(start, end) * 1000) / 1000d;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public String toString() {
        return "RANDOM Terminal " + getValue();
    }
}
