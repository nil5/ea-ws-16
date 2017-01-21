package tree;

import functions.Function;
import help.Config;
import help.Helper;
import terminals.InputTerminal;
import terminals.Terminal;

import java.util.ArrayList;
import java.util.List;

import static help.Config.MODE_FULL;
import static help.Config.MODE_GROW;


/**
 * Created by Nils on 10.01.2017.
 */
public class GeneticTree {
    protected GeneticTreeNode root;

    private final List<InputTerminal> inputs = new ArrayList<>();

    private final int maxDepth;
    private final int buildMode;
    private final int numInputs;

    protected GeneticTree(final GeneticTree tree) {
        maxDepth = tree.maxDepth;
        buildMode = tree.buildMode;
        numInputs = tree.numInputs;

        root = new GeneticTreeNode(null, tree.root.getFunction());

        copyTree(tree.root, root);
    }

    public GeneticTree(final int maxDepth) {
        this(maxDepth, MODE_FULL);
    }

    public GeneticTree(final int maxDepth, final int buildMode) {
        this(maxDepth, buildMode, 1);
    }

    public GeneticTree(final int maxDepth, final int buildMode, final int numInputs) {
        this.maxDepth = maxDepth;
        this.buildMode = buildMode;
        this.numInputs = numInputs;

        this.root = new GeneticTreeNode(null, Helper.getRandomFunction());

        switch (buildMode) {
            case MODE_FULL:
                generateFullTree(this.root);
                break;
            case MODE_GROW:
                generateGrowTree(root);
                break;
        }
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

        if (node.getLevel() < Config.MAXTREEDEPTH - 1) {
            for (int i = 0; i < numChildren; i++) {
                final Object obj = Helper.getRandomObject();

                try {
                    final Function function = (Function) obj;
                    generateGrowTree(new GeneticTreeNode(node, function));
                } catch (ClassCastException e) {
                    addLeaf(node, (Terminal) obj);
                }
            }
        } else {
            for (int i = 0; i < numChildren; i++) {
                addLeaf(node);
            }
        }
    }

    private void addLeaf(final GeneticTreeNode node) {
        addLeaf(node, null);
    }

    private void addLeaf(final GeneticTreeNode node, Terminal terminal) {
        final int inputSize = inputs.size();

        if (inputSize < numInputs) {
            terminal = new InputTerminal();
            inputs.add((InputTerminal) terminal);
        } else if (terminal == null) terminal = Helper.getRandomTerminal();

        new GeneticTreeLeaf(node, terminal);
    }

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

    public List<InputTerminal> getInputs() {
        return inputs;
    }

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
        return "TREE: " + numInputs + " inputs\n" + v.getResult();
    }

}
