package tree;

import functions.Function;
import help.Helper;
import terminals.IOTerminalSet;
import terminals.Terminal;

import static java.lang.Double.NaN;

/**
 * Created by Nils on 10.01.2017.
 */
public class GeneticTree implements Comparable<GeneticTree> {
    public static final int MODE_FULL = 1;
    public static final int MODE_GROW = 2;
    public static final int MODE_HALF = 3;

    public final int maxDepth;
    public final int buildMode;

    private GeneticTreeNode root;
    private IOTerminalSet testTerminal;
    private int testTerminalInputCount = 0;
    private double fitness;

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

        updateFitness();
    }

    private void generateFullTree(final GeneticTreeNode node) {
        final int numChildren = node.getFunction().numParams;

        if (node.getLevel() < maxDepth - 1) {
            for (int i = 0; i < numChildren; i++) {
                final GeneticTreeNode child = new GeneticTreeNode(node, Helper.getRandomFunction());
                node.addChild(child);
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

                node.addChild(new GeneticTreeLeaf(node, terminal));
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
                    final GeneticTreeNode child = new GeneticTreeNode(node, (Function) obj);
                    node.addChild(child);
                    generateGrowTree(child);
                } catch (ClassCastException e) {
                    node.addChild(new GeneticTreeLeaf(node, (Terminal) obj));
                }
            }
        } else {
            for (int i = 0; i < numChildren; i++) {
                // Jedes Blatt einmal verwenden oder sogar der Reihe nach?
                node.addChild(new GeneticTreeLeaf(node, Helper.getRandomTerminal()));
            }
        }
    }

    public void updateFitness() {
        final TreeCalcVisitor v = new TreeCalcVisitor();
        root.accept(v);
        fitness = Math.abs(v.getResult() - testTerminal.output.getValue());
    }

    public double getFitness() {
        return fitness;
    }

    public GeneticTreeNode getRoot() {
        return root;
    }

    public void print() {
        root.accept(new TreePrintVisitor());
    }

    @Override
    public int compareTo(GeneticTree o) {
        final boolean nan1 = Double.isNaN(fitness), nan2 = Double.isNaN(o.fitness);
        if (nan1 && nan2) return 0;
        if (nan1 || fitness > o.fitness) return 1;
        if (nan2 || o.fitness > fitness) return -1;
        return 0;
    }
}
