package tree;

import java.util.List;

/**
 * Created by Nils on 10.01.2017.
 */
public abstract class GeneticTreeComponent {
    public static final int NODE = 1;
    public static final int LEAF = 2;

    protected GeneticTreeNode parent;
    protected int level;

    public final int type;

    protected GeneticTreeComponent(final GeneticTreeNode parent, final int type) {
        this.type = type;

        setParent(parent);
    }

    public void setParent(final GeneticTreeNode parent) {
        this.parent = parent;
    }
}
