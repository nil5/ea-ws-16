package gp;

import tree.GeneticTreeNode;

/**
 * Created by Nils on 19.01.2017.
 */
public class SubTreeCrossover extends Mutator {
    private final Selection selection;

    public SubTreeCrossover(final double mutationRate, final boolean protectBest, final int tournamentSize) {
        super(2, 2, mutationRate, protectBest);

        this.selection = new TournamentSelection(tournamentSize, protectBest);
    }

    @Override
    public void mutate(Genome genome) {
        outer: for (int i = 0; i < mutationRate * genome.length; i++) {
            final Gene[] parents = selection.select(genome, inputGeneCount);
            final GeneticTreeNode[] subNodes = new GeneticTreeNode[inputGeneCount];

            System.out.println("==========  SUBTREE CROSSOVER  ==========");

            for (int j = 0, subNodeLevel = -1; j < parents.length; j++) {
                subNodes[j] = parents[j].getRandomSubNode(subNodeLevel);

                if (subNodes[j] == null) {
                    System.out.println("Failed to do crossover. Did not find an appropriate sub node.");
                    parents[j].print();
                    continue outer;
                }

                if (subNodeLevel < 0) subNodeLevel = subNodes[j].getLevel();
            }

            for (int j = 0, k = j + 1; k < parents.length; j++, k++) {
                System.out.println(parents[j]); System.out.println(parents[k]);

                parents[j].print(); parents[k].print();

                subNodes[j].swapParents(subNodes[k]);

                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                parents[j].print(); parents[k].print();

                parents[j].updateFitness();
                parents[k].updateFitness();

                System.out.println(parents[j]); System.out.println(parents[k]);
                System.out.println("---------------------------------------------------------------------------");
            }
        }
    }
}
