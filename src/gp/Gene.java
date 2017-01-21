package gp;

import help.Helper;
import terminals.IOTerminalSet;
import terminals.InputTerminal;
import tree.*;

import java.util.List;

/**
 * Created by Nils on 19.01.2017.
 */
public class Gene extends GeneticTree implements Comparable<Gene> {
    private static int idCounter = 1;

    public final int id = idCounter++;

    private double fitness = 0;

    public Gene(final Gene gene) {
        super(gene);

        fitness = gene.fitness;
    }

    public Gene(final int maxDepth, final int buildMode) {
        super(maxDepth, buildMode);

        updateFitness();
    }

    public void updateFitness() {
        final IOTerminalSet[] ioSets = Helper.getIOSets();
        final List<InputTerminal> inputTerminals = getInputs();
        final int inputSize = inputTerminals.size();

        fitness = 0;

        if (ioSets == null || ioSets.length < 1) {
            System.out.println("NO IO SETS FOUND: The ioSets array is empty.");
            return;
        }

        if (inputSize != ioSets[0].inputs.length) {
            System.out.println("INVALID TREE: No of input terminals does not equal number of inputs.");
            return;
        }

        for (final IOTerminalSet ioSet : ioSets) {
            final TreeCalcVisitor v = new TreeCalcVisitor();

            for (int i = 0; i < inputSize; i++) {
                inputTerminals.get(i).setValue(ioSet.inputs[i].getValue());
            }

            root.accept(v);
            fitness += Math.abs(v.getResult() - ioSet.output.getValue());
        }
    }


    public double getFitness() {
        return fitness;
    }

    @Override
    public String toString() {
        return "GENE " + id + ": fitness = " + fitness + "\n" + super.toString();
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