package functions;

import tree.GeneticTreeNode;

/**
 * Created by Nils on 10.01.2017.
 */
public class SinusFunction extends Function {

    public SinusFunction() {
        super("Sinus", 1);
    }

    @Override
    public double execute(double[] params) {
        if (params.length != numParams) throw new ArithmeticException("Invalid number of parameters for function '" +
                name + "'. Expected " + numParams + ", got " + params.length);

        return Math.sin(params[0]);
    }

    public String get(GeneticTreeNode node) {
        String function = "";

        for (int i = 0; i < node.getChildren().size(); i++) {
            function += " sin(";
            function += node.getChildren().get(i).get();
            function += ")";
        }

        return function;
    }
}
