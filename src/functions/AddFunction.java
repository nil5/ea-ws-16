package functions;

/**
 * Created by Nils on 10.01.2017.
 */
public class AddFunction extends Function {

    public AddFunction() {
        this(2);
    }
    public AddFunction(final int numParams) {
        super("Add", numParams);
    }

    @Override
    public double execute(double[] params) {
        if (params.length != numParams) throw new ArithmeticException("Invalid number of parameters for function '" +
                name + "'. Expected " + numParams + ", got " + params.length);

        double sum = 0;
        for (double p : params) sum += p;
        return sum;
    }
}
