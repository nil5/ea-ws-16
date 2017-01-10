package tree;

import functions.Function;
import terminals.Terminal;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Nils on 10.01.2017.
 */
public class GeneticTreeBuilder {
    public static final int MODE_RANDOM = 1;

    private final int depth;
    private final Terminal[] terminalSet;
    private final Function[] functionSet;

    private int mode = MODE_RANDOM;


    public GeneticTreeBuilder(final int depth, final Terminal[] terminalSet, final Function[] functionSet) {
        this.depth = depth;
        this.terminalSet = terminalSet;
        this.functionSet = functionSet;
    }

    public GeneticTreeBuilder setMode(final int mode) {
        this.mode = mode;
        return this;
    }

    public GeneticTree build() {
        final GeneticTreeNode root;

        switch (mode) {
            case MODE_RANDOM:
                root = new GeneticTreeNode(null, getRandomFunction());
                generateRandomChildren(root);
                break;
            default:
                root = null;
        }

        return new GeneticTree(root);
    }

    private void generateRandomChildren(final GeneticTreeNode node) {
        final int numChildren = node.getFunction().numParams;

        if (node.getLevel() == depth - 1) {
            for (int i = 0; i < numChildren; i++) {
                // Jedes Blatt einmal verwenden oder sogar der Reihe nach?
                node.addChild(new GeneticTreeLeaf(node, getRandomTerminal()));
            }
        } else {
            for (int i = 0; i < numChildren; i++) {
                final GeneticTreeNode child = new GeneticTreeNode(node, getRandomFunction());
                node.addChild(child);
                generateRandomChildren(child);
            }
        }
    }

    private Terminal getRandomTerminal() {
        try {
            return terminalSet[ThreadLocalRandom.current().nextInt(0, terminalSet.length)];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Function getRandomFunction() {
        try {
            return functionSet[ThreadLocalRandom.current().nextInt(0, functionSet.length)];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
