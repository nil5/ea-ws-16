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
    public final boolean protectBest;
    private double fitness = 0;

    private IOTerminalSet testTerminalSet;
    private Gene[] genes;

    private double bestGeneFitness;
    private int bestGeneIndex;


    public Genome(final int buildMode, final IOTerminalSet testTerminalSet, final boolean protectBest) {
        this.testTerminalSet = testTerminalSet;
        //this.length = testTerminalSets.length;
        this.length = Config.GENECOUNT;
        this.protectBest = protectBest;
        this.genes = new Gene[length];

        int i = 0;

        if (buildMode == Config.MODE_HALF) {
            for (; i < length / 2; i++) setGene(i, Config.MODE_FULL);
            for (; i < length; i++) setGene(i, Config.MODE_GROW);
        } else for (; i < length; i++) setGene(i, buildMode);

        updateBestGeneIndex();

        System.out.println("Genome fitness: " + fitness);
        for (Gene gene : genes) {
            gene.print();
            System.out.println("Tree result: " + gene.getFitness());
        }
    }

    public Gene get(final int index) {
        return genes[index];
    }

    public void setGene(final int index, final int mode) {
        //setGene(index, new Gene(testTerminalSets[index], Config.MAXTREEDEPTH, mode));
        setGene(index, new Gene(testTerminalSet, Config.MAXTREEDEPTH, mode));
    }

    public void setGene(final int index, final Gene gene) {
        if (genes[index] != null) fitness -= genes[index].getFitness();
        genes[index] = gene;
        fitness += gene.getFitness();
    }

    public void mutate() {
        mutate(false);
    }

    public void mutate(final boolean sortBefore) {
        final int mutationCount = (int) (length * Config.MUTATIONRATE);

        System.out.println("Performing " + mutationCount + " mutations");

        if (sortBefore) Arrays.sort(genes);

        for (int i = 0; i < mutationCount; i++) {
            final int geneIndex = ThreadLocalRandom.current().nextInt(0, length);
            final GeneticTreeComponent root = genes[geneIndex].getRoot();
            final List<GeneticTreeComponent> components = new ArrayList<>();

            System.out.println("Mutate Tree Nr: " + geneIndex);

            Helper.iterateTree(root, components);

            final int componentsSize = components.size();
            final int componentNo = ThreadLocalRandom.current().nextInt(0, componentsSize);

            System.out.println("Mutate Compontant Nr: " + componentNo);

            final GeneticTreeComponent component = components.get(componentNo);
            if (component.type == Config.LEAF) {
                final GeneticTreeLeaf leaf = (GeneticTreeLeaf) component;
                final Terminal oldTerminal = leaf.getTerminal(), newTerminal = Helper.getRandomTerminal();

                leaf.setTerminal(newTerminal);

                System.out.println("Mutated leaf " + oldTerminal.getValue() + " to " + newTerminal.getValue());
            } else if (component.type == Config.NODE) {
                final GeneticTreeNode node = (GeneticTreeNode) component;
                final Function oldFunction = node.getFunction(), newFunction = Helper.getRandomFunction();

                if (oldFunction.numParams == newFunction.numParams) {
                    node.setFunction(newFunction);

                    System.out.println("Mutated node " + oldFunction.toString() + " to " + newFunction.toString());
                } else {
                    System.out.println("Couldn't mutate node because Functions have different number of Params");
                }
            }

            genes[geneIndex].updateFitness();

            final double fitness = genes[geneIndex].getFitness();
            if (!Double.isNaN(fitness) && fitness < bestGeneFitness) {
                bestGeneIndex = geneIndex;
                bestGeneFitness = fitness;
            }
        }
    }

    public void sort() {
        Arrays.sort(genes);
        bestGeneIndex = 0;
    }

    private void updateBestGeneIndex() {
        int index = -1;
        double minFitness = Double.MAX_VALUE;

        for (int i = 0; i < genes.length; i++) {
            final double fitness = genes[i].getFitness();
            if (fitness < minFitness) {
                minFitness = fitness;
                index = i;
            }
        }

        bestGeneFitness = minFitness;
        bestGeneIndex = index;
    }
}