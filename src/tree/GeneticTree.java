package tree;

import functions.Function;
import help.Helper;
import terminals.IOTerminalSet;
import terminals.Terminal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nils on 10.01.2017.
 */
public class GeneticTree {
    public static final int MODE_FULL = 1;
    public static final int MODE_GROW = 2;
    public static final int MODE_HALF = 3;

    public final int maxDepth;
    public final int buildMode;

    protected final IOTerminalSet testTerminal;

    protected GeneticTreeNode root;

    private int testTerminalInputCount = 0;

    protected GeneticTree(final GeneticTree tree) {
        maxDepth = tree.maxDepth;
        buildMode = tree.buildMode;
        testTerminal = tree.testTerminal;
        root = new GeneticTreeNode(null, tree.root.getFunction());

        copyTree(tree.root, root);
    }

    public GeneticTree(final IOTerminalSet testTerminal) {
        this(testTerminal, 2);
    }

    public GeneticTree(final IOTerminalSet testTerminal, int maxDepth) {
        this(testTerminal, maxDepth, MODE_FULL);
    }

    public GeneticTree(final IOTerminalSet testTerminal, final int maxDepth, final int buildMode) {
        this.root = new GeneticTreeNode(null, Helper.getRandomFunction());
        this.testTerminal = testTerminal;
        this.maxDepth = maxDepth;
        this.buildMode = buildMode;

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
                // Jedes Blatt einmal verwenden oder sogar der Reihe nach?
                final Terminal terminal;

                if (testTerminalInputCount < testTerminal.inputCount) {
                    terminal = testTerminal.getInputTerminal(testTerminalInputCount);
                    testTerminalInputCount++;
                } else terminal = Helper.getRandomTerminal();

                new GeneticTreeLeaf(node, terminal);
            }
        }
    }

    private void generateGrowTree(final GeneticTreeNode node) {
        final int numChildren = node.getFunction().numParams;

        if (node.getLevel() < maxDepth - 1) {
            for (int i = 0; i < numChildren; i++) {
                // Jedes Blatt einmal verwenden oder sogar der Reihe nach?
                final Object obj = Helper.getRandomObject();

                try {
                    final Function function = (Function) obj;
                    final GeneticTreeNode child = new GeneticTreeNode(node, function);

                    generateGrowTree(child);
                } catch (ClassCastException e) {
                    new GeneticTreeLeaf(node, (Terminal) obj);
                }
            }
        } else {
            for (int i = 0; i < numChildren; i++) {
                // Jedes Blatt einmal verwenden oder sogar der Reihe nach?
                new GeneticTreeLeaf(node, Helper.getRandomTerminal());
            }
        }
    }

    private void copyTree(final GeneticTreeNode src, final GeneticTreeNode dst) {
        final List<GeneticTreeComponent> srcChildren = src.getChildren();

        for (GeneticTreeComponent srcChild : srcChildren) {
            if (srcChild.type == GeneticTreeComponent.NODE) {
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
        } else if (node.level < x) for (GeneticTreeComponent child : children) {
            if (child.type == GeneticTreeComponent.NODE) getLevel(level, componentType, (GeneticTreeNode) child, list);
        }
    }

    public GeneticTreeNode getRandomSubNode() {
        return getRandomSubNode(Helper.rand(1, maxDepth));
    }

    public GeneticTreeNode getRandomSubNode(final int level) {
        final List<GeneticTreeComponent> levelList = new ArrayList<>();

        getLevel(level, GeneticTreeComponent.NODE, root, levelList);

        final int size = levelList.size();
        return size < 1 ? null : (GeneticTreeNode) levelList.get(Helper.rand(0, levelList.size()));
    }

    public GeneticTreeNode getRoot() {
        return root;
    }

    public void print() {
        root.accept(new TreePrintVisitor());
    }
}
