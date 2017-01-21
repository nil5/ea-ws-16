package tree;

import functions.Function;
import help.Config;
import help.Helper;
import terminals.IOTerminalSet;
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
    protected final IOTerminalSet testTerminal;

    protected GeneticTreeNode root;

    private int testTerminalInputCount = 0;
    private boolean hasInputLeaf = false;

    protected GeneticTree(final GeneticTree tree) {
        testTerminal = tree.testTerminal;
        root = new GeneticTreeNode(null, tree.root.getFunction());

        copyTree(tree.root, root);
    }

    public GeneticTree(final IOTerminalSet testTerminal) {
        this(testTerminal, Config.MAXTREEDEPTH);
    }

    public GeneticTree(final IOTerminalSet testTerminal, int maxDepth) {
        this(testTerminal, maxDepth, MODE_FULL);
    }

    public GeneticTree(final IOTerminalSet testTerminal, final int maxDepth, final int buildMode) {
        this.root = new GeneticTreeNode(null, Helper.getRandomFunction());
        this.testTerminal = testTerminal;

        switch (buildMode) {
            case MODE_FULL:
                generateFullTree(this.root);
                break;
            case MODE_GROW:
                generateGrowTree(root);
                break;
        }
    }

    /*
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
    }*/
    private void generateFullTree(final GeneticTreeNode node) {
        final int numChildren = node.getFunction().numParams;

        if (node.getLevel() < Config.MAXTREEDEPTH - 1) {
            for (int i = 0; i < numChildren; i++) {
                final GeneticTreeNode child = new GeneticTreeNode(node, Helper.getRandomFunction());
                generateFullTree(child);
            }
        } else {
            for (int i = 0; i < numChildren; i++) {
                Terminal terminal;

                if (!hasInputLeaf) {
                    terminal = new InputTerminal();
                    hasInputLeaf = true;
                } else {
                    terminal = Helper.getRandomTerminal();
                }

                new GeneticTreeLeaf(node, terminal);
            }
        }
    }

    /*private void generateGrowTree(final GeneticTreeNode node) {
        final int numChildren = node.getFunction().numParams;

        if (node.getLevel() < Config.MAXTREEDEPTH - 1) {
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
    }*/

    private void generateGrowTree(final GeneticTreeNode node) {
        final int numChildren = node.getFunction().numParams;

        if (node.getLevel() < Config.MAXTREEDEPTH - 1) {
            for (int i = 0; i < numChildren; i++) {
                // Jedes Blatt einmal verwenden oder sogar der Reihe nach?
                final Object obj = Helper.getRandomObject();

                try {
                    final Function function = (Function) obj;
                    final GeneticTreeNode child = new GeneticTreeNode(node, function);

                    generateGrowTree(child);
                } catch (ClassCastException e) {
                    Terminal terminal;

                    if (!hasInputLeaf) {
                        terminal = new InputTerminal();
                        hasInputLeaf = true;
                    } else {
                        terminal = (Terminal) obj;
                    }
                    new GeneticTreeLeaf(node, terminal);
                }
            }
        } else {
            for (int i = 0; i < numChildren; i++) {
                Terminal terminal;

                if (!hasInputLeaf) {
                    terminal = new InputTerminal();
                    hasInputLeaf = true;
                } else {
                    terminal = Helper.getRandomTerminal();
                }

                new GeneticTreeLeaf(node, terminal);
            }
        }
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

    public void print() {
        root.accept(new TreePrintVisitor());
    }

}
