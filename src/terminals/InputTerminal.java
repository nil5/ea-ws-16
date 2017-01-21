package terminals;

/**
 * Created by Laura on 21.01.2017.
 */
public class InputTerminal implements Terminal {
    private double value;

    public void setValue(double value) {this.value = value;}

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public String toString() {
        return "INPUT Terminal " + getValue();
    }
}
