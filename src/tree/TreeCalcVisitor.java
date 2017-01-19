package tree;


import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Nils on 18.01.2017.
 */
public class TreeCalcVisitor implements TreeVisitor<Double> {
    private double result;

    @Override
    public Double visitComponent(GeneticTreeComponent component) {
        if (component.type == GeneticTreeComponent.NODE) {
            final GeneticTreeNode node = (GeneticTreeNode) component;
            final List<GeneticTreeComponent> children = node.getChildren();
            final int childrenSize = children.size();
            final double[] childValues = new double[childrenSize];

            for (int i = 0; i < childrenSize; i++) {
                final TreeCalcVisitor v = new TreeCalcVisitor();
                children.get(i).accept(v);
                childValues[i] = v.getResult();
            }

            result = node.getFunction().execute(childValues);
        } else result = ((GeneticTreeLeaf) component).getTerminal().getValue();

        return result;
    }

    public double getResult() {
        return result;
    }
}
