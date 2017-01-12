package functions;

/**
 * Created by Nils on 10.01.2017.
 */
public class DivideFunction extends Function {

    public DivideFunction() {
        super("Divide", 2);
    }

    @Override
    public double execute(double[] params) {
        if (params.length != numParams) throw new ArithmeticException("Invalid number of parameters for function '" +
                name + "'. Expected " + numParams + ", got " + params.length);

        return params[0] / params[1];
    }
}
