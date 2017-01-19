package tree;

import functions.Function;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nils on 10.01.2017.
 */
public class GeneticTreeNode extends GeneticTreeComponent {
    private List<GeneticTreeComponent> children = new ArrayList<>();
    private Function function;

    public GeneticTreeNode(final GeneticTreeNode parent, Function function) {
        super(parent, GeneticTreeComponent.NODE);

        this.function = function;
    }

    public boolean addChild(final GeneticTreeComponent child) {
        if (children.size() == function.numParams) return false;
        return children.add(child);
    }

    public List<GeneticTreeComponent> getChildren() {
        return children;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {this.function = function;}

    @Override
    public String toString() {
        return "NODE level " + level + ", function: " + function;
    }
}
