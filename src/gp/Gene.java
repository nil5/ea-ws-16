package gp;

import help.Config;
import help.Helper;
import terminals.IOTerminalSet;
import terminals.InputTerminal;
import tree.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Nils on 19.01.2017.
 */
public class Gene extends GeneticTree implements Comparable<Gene> {
    private static int idCounter = 1;

    private final int id = idCounter++;

    private double fitness = 0;

    public Gene(final Gene gene) {
        super(gene);

        fitness = gene.fitness;
    }

    public Gene(final IOTerminalSet testTerminal) {
        this(testTerminal, Config.MAXTREEDEPTH);
    }

    public Gene(final IOTerminalSet testTerminal, final int maxDepth) {
        this(testTerminal, maxDepth, Config.MODE_HALF);
    }

    public Gene(final IOTerminalSet testTerminal, final int maxDepth, final int buildMode) {
        super(testTerminal, maxDepth, buildMode);

        updateFitness();
    }

    public void updateFitness() {
        //final TreeCalcVisitor v = new TreeCalcVisitor();
        //root.accept(v);
        //fitness = Math.abs(v.getResult() - testTerminal.output.getValue());

        for (int i = 0; i < testTerminal.lineCount; i++) {
            InputTerminal terminal = getInputTerminal();
            if (terminal == null) {
                System.out.println("Could not find InputTerminal");
                break;
            }
            terminal.setValue(testTerminal.inputs[i]);

            final TreeCalcVisitor v = new TreeCalcVisitor();
            root.accept(v);
            fitness += Math.abs(v.getResult() - testTerminal.outputs[i]);
            //System.out.println(newFitness);
        }
    }

    public InputTerminal getInputTerminal() {
        final List<GeneticTreeComponent> children = new ArrayList<>();
        Helper.iterateTree(root, children);

        GeneticTreeLeaf leaf;
        InputTerminal terminal = null;

        for (GeneticTreeComponent child : children) {
            if (child.type == Config.LEAF) {
                leaf = (GeneticTreeLeaf) child;

                if (leaf.getTerminal().getType() == Config.INPUT) {
                    terminal = (InputTerminal) leaf.getTerminal();
                    break;
                }
            }
        }
        return terminal;
    }


    public double getFitness() {
        return fitness;
    }

    @Override
    public String toString() {
        return "GENE " + id + ": fitness = " + fitness;
    }

    @Override
    public int compareTo(Gene o) {
        final boolean nan1 = Double.isNaN(fitness), nan2 = Double.isNaN(o.fitness);
        if (nan1 && nan2) return 0;
        if (nan1 || fitness > o.fitness) return 1;
        if (nan2 || o.fitness > fitness) return -1;
        return 0;
    }
}