package gp;

import functions.*;
import help.Config;
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
    private double bestGeneFitness = Double.MAX_VALUE;
    private int bestGeneIndex = -1;

    public Genome(final Gene[] genes) {
        this.genes = genes;
        this.length = genes.length;

        updateBestGeneIndex();
    }

    public Genome(final int buildMode, final int length) {
        this.length = length;
        this.genes = new Gene[length];

        int i = 0;

        if (buildMode == Config.MODE_HALF) {
            for (; i < length; i++) {
                final int depth = (i % (Config.MAXTREEDEPTH - 1)) + 2;
                genes[i] = new Gene(depth, i < length / 2 ? Config.MODE_FULL : Config.MODE_GROW);
            }
        } else for (; i < length; i++) genes[i] = new Gene(Config.MAXTREEDEPTH, buildMode);

        updateBestGeneIndex();

        // Debugging
        /*for (Gene gene : genes) {
            System.out.println(gene.toString() + "\nTree result: " + gene.getFitness());
            System.out.println("Tree function: " + gene.getFunction());
        }*/
    }

    public Gene get(final int index) {
        return genes[index];
    }

    public int getBestGeneIndex() {
        return bestGeneIndex;
    }

    public void sort() {
        Arrays.sort(genes);
        bestGeneIndex = 0;
    }

    public void updateBestGeneIndex() {
        bestGeneFitness = Double.MAX_VALUE;
        bestGeneIndex = -1;
        for (int i = 0; i < genes.length; i++) {
            updateBestGeneIndex(i);
        }
    }

    public void updateBestGeneIndex(final int index) {
        final double fitness = genes[index].getFitness();
        if (!Double.isNaN(fitness) && fitness < bestGeneFitness) {
            bestGeneFitness = fitness;
            bestGeneIndex = index;
        }
    }

    @Override
    public String toString() {
        String s = "GENOME: Best Gene at index " + bestGeneIndex + " with fitness " + bestGeneFitness + "\n";
        for (final Gene g : genes) s += g.toString() + "\n";
        return s;
    }
}