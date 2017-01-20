package functions;

/**
 * Created by Nils on 10.01.2017.
 */
public class SubtractFunction extends Function {

    public SubtractFunction() {
        this(2);
    }
    public SubtractFunction(final int numParams) {
        super("Subtract", numParams);
    }

    @Override
    public double execute(double[] params) {
        if (params.length != numParams) throw new ArithmeticException("Invalid number of parameters for function '" +
                name + "'. Expected " + numParams + ", got " + params.length);

        double difference = 0;
        for (double p : params) difference -= p;
        return difference;
    }
}
