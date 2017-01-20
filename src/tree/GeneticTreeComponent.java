package tree;

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

        addParent(parent);
    }

    public boolean setParent(final GeneticTreeNode parent) {
        boolean success = removeParent();
        //TODO
    }

    private boolean removeParent() {
        if (parent != null) {
            if(!parent.removeChild(this)) return false;
            parent = null;
        } return true;
    }

    private boolean addParent(final GeneticTreeNode parent) {
        if (parent != null) {
            if (!parent.addChild(this)) return false;
        }

        this.parent = parent;

        return true;
    }

    private void updateLevel() {
        GeneticTreeNode parent = this.parent;
        level = 0;

        while (parent != null) {
            level++;
            parent = parent.parent;
        }
    }

    public boolean swapParents(final GeneticTreeComponent component) {
        final GeneticTreeNode leftParent = this.parent;
        final GeneticTreeNode rightParent = component.parent;

        removeParent();
        component.removeParent();


    }

    public GeneticTreeNode getParent() {
        return parent;
    }

    public int getLevel() {
        return level;
    }

    public void accept(TreeVisitor visitor) {
        visitor.visitComponent(this);
    }

    @Override
    public abstract String toString();
}
