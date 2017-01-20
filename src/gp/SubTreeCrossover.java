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
        /*final Gene[] nextGeneration = new Gene[genes.length];
        int nextGenerationIndex = 0;*/

        for (int i = 0; i < mutationRate * genome.length; i++) {
            final Gene[] parents = selection.select(genome, inputGeneCount);
            /*final Gene[] children = new Gene[inputGeneCount];*/
            final GeneticTreeNode[] subNodes = new GeneticTreeNode[inputGeneCount];

            System.out.println("==========  SUBTREE CROSSOVER  ==========");

            for (int j = 0, subNodeLevel = -1; j < parents.length; j++) {
                System.out.println(parents[j]);
                /*children[j] = new Gene(parents[j]);*/
                /*nextGeneration[nextGenerationIndex++] = children[j];*/

                if (subNodeLevel < 0) {
                    subNodes[j] = /*children[j]*/parents[j].getRandomSubNode();
                    subNodeLevel = subNodes[j].getLevel();
                } else subNodes[j] = /*children[j]*/parents[j].getRandomSubNode(subNodeLevel);

                if (subNodes[j] == null)
                    throw new NullPointerException("One of the crossover subnodes is null.");
            }

            for (int j = 0; j < parents.length; j++) {
                final int k = (j + 1) % parents.length;
                final GeneticTreeNode tmpParent = subNodes[j].getParent();

                parents[j].print();
                parents[k].print();

                subNodes[j].setParent(subNodes[k].getParent());
                subNodes[k].setParent(tmpParent);

                parents[j].print();
                parents[k].print();

                /*children[j]*/parents[j].updateFitness();
                /*children[k]*/parents[k].updateFitness();

                System.out.println(parents[j]);
                System.out.println(parents[k]);
            }
        }
    }
}
