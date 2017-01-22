package tree;

/**
 * Created by Nils on 10.01.2017.
 */
public abstract class GeneticTreeComponent {
    protected GeneticTreeNode parent;
    protected int level;

    public final int type;

    protected GeneticTreeComponent(final GeneticTreeNode parent, final int type) {
        this.type = type;

        setParent(parent);
    }

    public boolean setParent(final GeneticTreeNode parent) {
        boolean success = removeParent() && addParent(parent);
        updateLevel();
        return success;
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

    public void updateLevel() {
        GeneticTreeNode parent = this.parent;
        level = 0;

        while (parent != null) {
            level++;
            parent = parent.parent;
        }
    }

    public boolean swapParents(final GeneticTreeComponent that) {
        final GeneticTreeNode thisParent = this.parent;
        final GeneticTreeNode thatParent = that.parent;

        boolean success = true;

        if (success) success = this.removeParent();
        if (success) success = that.removeParent();

        if (success) success = this.addParent(thatParent);
        if (success) success = that.addParent(thisParent);

        return success;
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

    public abstract String get();
}
