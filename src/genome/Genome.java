package genome;

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
    public final int maxTreeDepth;
    private double recombinationRate;
    private double mutationRate;
    private double fitness = 0;

    private IOTerminalSet[] testTerminalSets;
    private GeneticTree[] genes;


    public Genome(int buildMode, IOTerminalSet[] testTerminalSets, int maxTreeDepth, double mutationRate, double recombinationRate) {
        this.testTerminalSets = testTerminalSets;
        this.length = testTerminalSets.length;
        this.maxTreeDepth = maxTreeDepth;
        this.mutationRate = mutationRate;
        this.recombinationRate = recombinationRate;
        this.genes = new GeneticTree[length];

        int i = 0;

        if (buildMode == GeneticTree.MODE_HALF) {
            for (; i < length / 2; i++) setTree(i, GeneticTree.MODE_FULL);
            for (; i < length; i++) setTree(i, GeneticTree.MODE_GROW);
        } else for (; i < length; i++) setTree(i, buildMode);

        Arrays.sort(genes);

        System.out.println("Genome fitness: " + fitness);
        for (GeneticTree gene : genes) {
            gene.print();
            System.out.println("Tree result: " + gene.getFitness());
        }
    }

    private void setTree(final int index, final int mode) {
        genes[index] = new GeneticTree(testTerminalSets[index], maxTreeDepth, GeneticTree.MODE_FULL);
        fitness += genes[index].getFitness();
    }

    public void mutate() {
        final int mutationCount = (int) (length * mutationRate);

        System.out.println("Performing " + mutationCount + " mutations");

        for (int i = 0; i < mutationCount; i++) {
            final int treeNo = ThreadLocalRandom.current().nextInt(0, length);
            final GeneticTreeComponent root = genes[treeNo].getRoot();
            final List<GeneticTreeComponent> components = new ArrayList<>();

            System.out.println("Mutate Tree Nr: " + treeNo);

            iterateTree(root, components);

            final int componentsSize = components.size();
            final int componentNo = ThreadLocalRandom.current().nextInt(0, componentsSize);

            System.out.println("Mutate Compontant Nr: " + componentNo);

            final GeneticTreeComponent component = components.get(componentNo);
            if (component.type == GeneticTreeComponent.LEAF) {
                final GeneticTreeLeaf leaf = (GeneticTreeLeaf) component;
                final Terminal oldTerminal = leaf.getTerminal(), newTerminal = Helper.getRandomTerminal();

                leaf.setTerminal(newTerminal);

                System.out.println("Mutated leaf " + oldTerminal.getValue() + " to " + newTerminal.getValue());
            } else if (component.type == GeneticTreeComponent.NODE) {
                final GeneticTreeNode node = (GeneticTreeNode) component;
                final Function oldFunction = node.getFunction(), newFunction = Helper.getRandomFunction();

                if (oldFunction.numParams == newFunction.numParams) {
                    node.setFunction(newFunction);

                    System.out.println("Mutated node " + oldFunction.toString() + " to " + newFunction.toString());
                } else {
                    System.out.println("Couldn't mutate node because Functions have different number of Params");
                }
            }
        }
    }

    public void crossover() {

    }

    public void iterateTree(GeneticTreeComponent component, List<GeneticTreeComponent> componentList){
        componentList.add(component);

        if (component.type == GeneticTreeComponent.NODE) {
            final List<GeneticTreeComponent> children = ((GeneticTreeNode) component).getChildren();
            for (int i = 0, c = children.size(); i < c; i++) {
                iterateTree(children.get(i), componentList);
            }
        }
    }
}