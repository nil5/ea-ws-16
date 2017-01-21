package terminals;

/**
 * Created by Laura on 21.01.2017.
 */
public class InputTerminal implements Terminal {
    private double value;
    public final int type = 0;

    public void setValue(double value) {this.value = value;}

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
        return "INPUT Terminal " + value;
    }
}
