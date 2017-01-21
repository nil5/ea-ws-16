package gp;

import functions.*;
import help.Helper;
import terminals.IOTerminalSet;
import terminals.Terminal;
import tree.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Laura on 11.01.2017.
 */
public class Genome {
    public final int length;

    private Gene[] genes;

    private double bestGeneFitness;
    private int bestGeneIndex;


    public Genome(final int buildMode, final int length) {
        this.length = length;
        this.genes = new Gene[length];

        int i = 0;

        if (buildMode == GeneticTree.MODE_HALF) {
            for (; i < length / 2; i++) setGene(i, GeneticTree.MODE_FULL);
            for (; i < length; i++) setGene(i, GeneticTree.MODE_GROW);
        } else for (; i < length; i++) setGene(i, buildMode);

        updateBestGeneIndex();

        // Debugging
        for (Gene gene : genes) {
            gene.print();
            System.out.println("Tree result: " + gene.getFitness());
        }
    }

    public Gene get(final int index) {
        return genes[index];
    }

    public void setGene(final int index, final int mode) {
        setGene(index, new Gene(testTerminalSets[index], maxTreeDepth, mode));
    }

    public void setGene(final int index, final Gene gene) {
        if (genes[index] != null) fitness -= genes[index].getFitness();
        genes[index] = gene;
        fitness += gene.getFitness();
    }

    public int getBestGeneIndex() {
        return bestGeneIndex;
    }

    public void sort() {
        Arrays.sort(genes);
        bestGeneIndex = 0;
    }

    private void updateBestGeneIndex() {
        for (int i = 0; i < genes.length; i++) {
            updateBestGeneIndex(i);
        }
    }

    public void updateBestGeneIndex(final int index) {
        final double fitness = genes[index].getFitness();
        if (fitness < bestGeneFitness) {
            bestGeneFitness = fitness;
            bestGeneIndex = index;
        }
    }
}