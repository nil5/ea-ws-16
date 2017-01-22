package gp;

import functions.Function;
import help.Config;
import help.Helper;
import tree.GeneticTree;
import tree.GeneticTreeComponent;
import tree.GeneticTreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nils on 22.01.2017.
 */
public class SubTreeMutator extends Mutator {
    public SubTreeMutator(double mutationRate, boolean protectBest) {
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
            final GeneticTreeNode node = gene.getRandomSubNode(-1);

            if (node == null) continue;

            final int depth = Config.MAXTREEDEPTH - node.getLevel() - 1;

            if (depth < 1) continue;

            final GeneticTree tree = new GeneticTree(depth, Config.INIT == Config.MODE_HALF ? Helper.rand(Config.MODE_FULL, Config.MODE_GROW + 1) : Config.INIT);
            final GeneticTreeNode root = tree.getRoot();
            final List<GeneticTreeComponent> componentList = new ArrayList<>();

            node.swapParents(root);
            Helper.iterateTree(root, componentList);

            for (GeneticTreeComponent component : componentList) component.updateLevel();

            gene.updateFitness();
            genome.updateBestGeneIndex(geneIndex);
        }
    }
}
