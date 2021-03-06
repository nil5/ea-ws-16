package terminals;

/**
 * Created by Nils on 10.01.2017.
 */
public class ConstantTerminal implements Terminal {
    private final double value;

    public ConstantTerminal(final double value) {
        this.value = value;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public String toString() {
        return "CONSTANT Terminal " + getValue();
    }
}
