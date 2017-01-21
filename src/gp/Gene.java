package gp;

import help.Helper;
import terminals.IOTerminalSet;
import tree.GeneticTree;
import tree.TreeCalcVisitor;

/**
 * Created by Nils on 19.01.2017.
 */
public class Gene extends GeneticTree implements Comparable<Gene> {
    private static int idCounter = 1;

    private final int id = idCounter++;

    private double fitness;

    public Gene(final Gene gene) {
        super(gene);

        fitness = gene.fitness;
    }

    public Gene(final int maxDepth, final int buildMode) {
        super(maxDepth, buildMode);

        updateFitness();
    }


    public void updateFitness() {
        fitness = 0;

        final IOTerminalSet[] ioSets = Helper.getIOSets();

        for (int i = 0; i < ioSets.length; i++) {
            final TreeCalcVisitor v = new TreeCalcVisitor();

            root.accept(v);
            fitness += Math.abs(v.getResult() - ioSets[i].output.getValue());
        }
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
