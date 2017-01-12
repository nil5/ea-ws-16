package functions;

/**
 * Created by Nils on 10.01.2017.
 */
public class CosinusFunction extends Function {

    public CosinusFunction() {
        super("Cosinus", 1);
    }

    @Override
    public double execute(double[] params) {
        if (params.length != numParams) throw new ArithmeticException("Invalid number of parameters for function '" +
                name + "'. Expected " + numParams + ", got " + params.length);

        return Math.cos(params[0]);
    }
}
