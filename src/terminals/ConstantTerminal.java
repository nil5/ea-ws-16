package terminals;

/**
 * Created by Nils on 10.01.2017.
 */
public class ConstantTerminal implements Terminal {
    private final double value;
    public final int type = 2;

    public ConstantTerminal(final double value) {
        this.value = value;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "CONSTANT Terminal " + value;
    }
}
