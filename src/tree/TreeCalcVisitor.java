package tree;


import help.Config;

import java.util.List;

/**
 * Created by Nils on 18.01.2017.
 */
public class TreeCalcVisitor implements TreeVisitor<Double> {

    @Override
    public Double visitComponent(GeneticTreeComponent component) {
        double result = 0;
        if (component.type == Config.NODE) {
            final GeneticTreeNode node = (GeneticTreeNode) component;
            final List<GeneticTreeComponent> children = node.getChildren();
            final int childrenSize = children.size();
            final double[] childValues = new double[childrenSize];

            for (int i = 0; i < childrenSize; i++) {
                childValues[i] = this.visitComponent(children.get(i));
            }

            result += node.getFunction().execute(childValues);
        } else result += ((GeneticTreeLeaf) component).getTerminal().getValue();

        return result;
    }
}
