package functions;

import tree.GeneticTree;
import tree.GeneticTreeNode;

/**
 * Created by Nils on 10.01.2017.
 */
public abstract class Function {
    public final String name;
    public final int numParams;

    protected Function(final String name, final int numParams) {
        this.name = name;
        this.numParams = numParams;
    }

    public abstract double execute(double[] params);

    @Override
    public String toString() {
        return "Function '" + name + "', " + numParams + " parameters";
    }

    public abstract String get(GeneticTreeNode node);
}
