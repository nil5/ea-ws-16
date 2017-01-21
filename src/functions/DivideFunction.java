package functions;

import tree.GeneticTreeNode;

import static java.lang.Double.NaN;

/**
 * Created by Nils on 10.01.2017.
 */
public class DivideFunction extends Function {

    public DivideFunction() {
        this(2);
    }
    public DivideFunction(final int numParams) {
        super("Divide", numParams);
    }

    @Override
    public double execute(double[] params) {
        if (params.length != numParams) throw new ArithmeticException("Invalid number of parameters for function '" +
                name + "'. Expected " + numParams + ", got " + params.length);

        double quotient = params[0];
        for (int i = 1; i < params.length; i++) {
            if (params[i] == 0.0) return NaN;
            quotient /= params[i];
        }
        return quotient;
    }

    public String get(GeneticTreeNode node) {
        String function = "(";

        for (int i = 0; i < node.getChildren().size(); i++) {
            function += node.getChildren().get(i).get();
            if(i != node.getChildren().size()-1) function += " / ";
        }

        return function +")";
    }
}
