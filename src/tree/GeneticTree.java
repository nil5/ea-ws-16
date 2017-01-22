package tree;

import functions.Function;
import help.Config;
import help.Helper;
import terminals.InputTerminal;
import terminals.Terminal;

import java.util.ArrayList;
import java.util.List;

import static help.Config.*;


/**
 * Created by Nils on 10.01.2017.
 */
public class GeneticTree {
    protected GeneticTreeNode root;

    private final int maxDepth;
    private final int buildMode;

    protected GeneticTree(final GeneticTree tree) {
        maxDepth = tree.maxDepth;
        buildMode = tree.buildMode;

        root = new GeneticTreeNode(tree.root, tree.root.parent);

        copyTree(tree.root, root);
    }

    public GeneticTree(final int maxDepth, final int buildMode) {
        this.maxDepth = maxDepth;
        this.buildMode = buildMode;

        this.root = new GeneticTreeNode(null, Helper.getRandomFunction());

        switch (buildMode) {
            case MODE_FULL:
                generateFullTree(this.root);
                break;
            case MODE_GROW:
                generateGrowTree(root);
                break;
        }

        //addInputTerminals(numInputs);
    }

    private void generateFullTree(final GeneticTreeNode node) {
        final int numChildren = node.getFunction().numParams;

        if (node.getLevel() < maxDepth - 1) {
            for (int i = 0; i < numChildren; i++) {
                final GeneticTreeNode child = new GeneticTreeNode(node, Helper.getRandomFunction());
                generateFullTree(child);
            }
        } else {
            for (int i = 0; i < numChildren; i++) {
                addLeaf(node);
            }
        }
    }

    private void generateGrowTree(final GeneticTreeNode node) {
        final int numChildren = node.getFunction().numParams;

        if (node.getLevel() < maxDepth - 1) {
            for (int i = 0; i < numChildren; i++) {
                final Object obj = Helper.getRandomObject();

                try {
                    final Function function = (Function) obj;
                    generateGrowTree(new GeneticTreeNode(node, function));
                } catch (ClassCastException e) {
                    addLeaf(node);
                }
            }
        } else {
            for (int i = 0; i < numChildren; i++) {
                addLeaf(node);
            }
        }
    }

    private void addLeaf(final GeneticTreeNode node) {
        new GeneticTreeLeaf(node, Helper.getRandomTerminal());
    }

    public void fillInputs(final double value) {
        fillInputs(root, value);
    }

    private void fillInputs(final GeneticTreeNode node, final double value) {
        final List<GeneticTreeComponent> children = node.getChildren();
        for (final GeneticTreeComponent child : children) {
            if (child.type == NODE) fillInputs((GeneticTreeNode) child, value);
            else {
                final Terminal terminal = ((GeneticTreeLeaf) child).getTerminal();
                if (terminal.getType() == INPUT) ((InputTerminal) terminal).setValue(value);
            }
        }
    }

    private int getInputCount(final GeneticTreeComponent component) {
        int count = 0;
        if (component.type == NODE) {
            for (final GeneticTreeComponent child : ((GeneticTreeNode) component).getChildren()) {
                count += getInputCount(child);
            }
        } else if (((GeneticTreeLeaf) component).getTerminal().getType() == INPUT) count++;
        return count;
    }

    /*private void addInputTerminals(final int numInputs) {
        for (int i = 0; i < numInputs; i++) {
            final InputTerminal inputTerminal = new InputTerminal();
            inputs.add(inputTerminal);
            root.replaceRandomTerminal(inputTerminal);
        }
    }*/

    private void copyTree(final GeneticTreeNode src, final GeneticTreeNode dst) {
        final List<GeneticTreeComponent> srcChildren = src.getChildren();

        for (GeneticTreeComponent srcChild : srcChildren) {
            if (srcChild.type == Config.NODE) {
                final GeneticTreeNode srcNode = (GeneticTreeNode) srcChild;
                final GeneticTreeNode dstNode = new GeneticTreeNode(srcNode, dst);

                copyTree(srcNode, dstNode);
            } else {
                new GeneticTreeLeaf((GeneticTreeLeaf) srcChild, dst);
            }
        }
    }

    private void getLevel(final int level, final int componentType, final GeneticTreeNode node,
                          final List<GeneticTreeComponent> list) {
        final int x = level - 1;
        final List<GeneticTreeComponent> children = node.getChildren();
        if (node.level == x) for (GeneticTreeComponent child : children) {
            if (child.type == componentType) list.add(child);
        }
        else if (node.level < x) for (GeneticTreeComponent child : children) {
            if (child.type == Config.NODE) getLevel(level, componentType, (GeneticTreeNode) child, list);
        }
    }

    /*public List<InputTerminal> getInputs() {
        return inputs;
    }*/

    public GeneticTreeNode getRandomSubNode(int level) {
        if (level < 0) level = Helper.rand(1, Config.MAXTREEDEPTH);

        final List<GeneticTreeComponent> levelList = new ArrayList<>();

        getLevel(level, Config.NODE, root, levelList);

        final int size = levelList.size();
        return size < 1 ? null : (GeneticTreeNode) levelList.get(Helper.rand(0, levelList.size()));
    }

    public GeneticTreeNode getRoot() {
        return root;
    }

    public String getFunction() {
        return root.get();
    }

    @Override
    public String toString() {
        final TreeToStringVisitor v = new TreeToStringVisitor();
        root.accept(v);
        return "TREE: " + getInputCount(root) + " inputs\n" + v.getResult() + "\n" + getFunction();
    }

}
