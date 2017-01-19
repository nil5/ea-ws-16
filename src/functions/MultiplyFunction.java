package functions;

/**
 * Created by Nils on 10.01.2017.
 */
public class MultiplyFunction extends Function {

    public MultiplyFunction() {
        this(2);
    }
    public MultiplyFunction(final int numParams) {
        super("Multiply", numParams);
    }

    @Override
    public double execute(double[] params) {
        if (params.length != numParams) throw new ArithmeticException("Invalid number of parameters for function '" +
                name + "'. Expected " + numParams + ", got " + params.length);

        double product = 0;
        for (double p : params) product *= p;
        return product;
    }
}
