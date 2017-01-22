package gp;

import functions.Function;
import help.Config;
import help.Helper;
import tree.GeneticTreeComponent;
import tree.GeneticTreeNode;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Nils on 22.01.2017.
 */
public class SwapMutator extends Mutator {
    public SwapMutator(double mutationRate, boolean protectBest) {
        super(1, 1, mutationRate, protectBest);
    }

    @Override
    public void mutate(Genome genome) {
        final int mutationCount = (int) (genome.length * mutationRate);

        //System.out.println("Performing " + mutationCount + " mutations");

        for (int i = 0; i < mutationCount; i++) {
            int geneIndex = Helper.rand(0, genome.length);

            if (protectBest) {
                final int bestGeneIndex = genome.getBestGeneIndex();
                while (geneIndex == bestGeneIndex)
                    geneIndex = Helper.rand(0, genome.length);
            }

            final Gene gene = genome.get(geneIndex);
            final GeneticTreeNode node = gene.getRandomSubNode(Helper.rand(1, Config.MAXTREEDEPTH));

            if (node != null) {
                final GeneticTreeNode parent = node.getParent();

                final Function nodeFunction = node.getFunction();
                final Function parentFunction = parent.getFunction();

                if (nodeFunction.numParams == parentFunction.numParams) {
                    node.setFunction(parentFunction);
                    parent.setFunction(nodeFunction);

                    gene.updateFitness();
                    genome.updateBestGeneIndex(geneIndex);
                }
            }
        }
    }
}
