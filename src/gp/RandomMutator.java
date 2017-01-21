package gp;

import functions.Function;
import help.Config;
import help.Helper;
import terminals.Terminal;
import tree.GeneticTreeComponent;
import tree.GeneticTreeLeaf;
import tree.GeneticTreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Nils on 20.01.2017.
 */
public class RandomMutator extends Mutator {
    public RandomMutator(double mutationRate, boolean protectBest) {
        super(1, 1, mutationRate, protectBest);
    }

    @Override
    public void mutate(Genome genome) {
        final int mutationCount = (int) (genome.length * mutationRate);

        //System.out.println("Performing " + mutationCount + " mutations");

        for (int i = 0; i < mutationCount; i++) {
            int geneIndex = ThreadLocalRandom.current().nextInt(0, genome.length);

            if (protectBest) {
                final int bestGeneIndex = genome.getBestGeneIndex();
                while(geneIndex == bestGeneIndex)
                    geneIndex = ThreadLocalRandom.current().nextInt(0, genome.length);
            }

            final Gene gene = genome.get(geneIndex);
            final GeneticTreeComponent root = gene.getRoot();
            final List<GeneticTreeComponent> components = new ArrayList<>();

            //System.out.println("Mutate Tree Nr: " + geneIndex);

            iterateTree(root, components);

            final int componentsSize = components.size();
            final int componentNo = ThreadLocalRandom.current().nextInt(0, componentsSize);

            //System.out.println("Mutate Compontant Nr: " + componentNo);

            final GeneticTreeComponent component = components.get(componentNo);
            if (component.type == Config.LEAF) {
                final GeneticTreeLeaf leaf = (GeneticTreeLeaf) component;
                final Terminal oldTerminal = leaf.getTerminal(), newTerminal = Helper.getRandomTerminal();

                if (oldTerminal.getType() == Config.INPUT) {
                    System.out.println("Failed to mutate. Input leafs are not allowed.");
                    continue;
                }

                leaf.setTerminal(newTerminal);

                //System.out.println("Mutated leaf " + oldTerminal.getValue() + " to " + newTerminal.getValue());
            } else if (component.type == Config.NODE) {
                final GeneticTreeNode node = (GeneticTreeNode) component;
                final Function oldFunction = node.getFunction(), newFunction = Helper.getRandomFunction();

                if (oldFunction.numParams == newFunction.numParams) {
                    node.setFunction(newFunction);

                    //System.out.println("Mutated node " + oldFunction.toString() + " to " + newFunction.toString());
                } else {
                    System.out.println("Couldn't mutate node because Functions have different number of Params");
                }
            }

            gene.updateFitness();
            genome.updateBestGeneIndex(geneIndex);
        }
    }

    private static void iterateTree(GeneticTreeComponent component, List<GeneticTreeComponent> componentList){
        componentList.add(component);

        if (component.type == Config.NODE) {
            final List<GeneticTreeComponent> children = ((GeneticTreeNode) component).getChildren();
            for (int i = 0, c = children.size(); i < c; i++) {
                iterateTree(children.get(i), componentList);
            }
        }
    }
}
