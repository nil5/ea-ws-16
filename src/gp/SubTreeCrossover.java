package gp;

import help.Config;
import tree.GeneticTreeNode;

/**
 * Created by Nils on 19.01.2017.
 */
public class SubTreeCrossover extends Mutator {
    private final Selection selection;

    public SubTreeCrossover(final boolean protectBest, final int tournamentSize) {
        super(2, 2, protectBest);

        this.selection = new TournamentSelection(protectBest);
    }

    @Override
    public void mutate(Genome genome) {
        /*final Gene[] nextGeneration = new Gene[genes.length];
        int nextGenerationIndex = 0;*/

        outer: for (int i = 0; i < Config.RECOMBINATIONRATE * genome.length; i++) {
            final Gene[] parents = selection.select(genome, inputGeneCount);
            /*final Gene[] children = new Gene[inputGeneCount];*/
            final GeneticTreeNode[] subNodes = new GeneticTreeNode[inputGeneCount];

            System.out.println("==========  SUBTREE CROSSOVER  ==========");

            for (int j = 0, subNodeLevel = -1; j < parents.length; j++) {
                System.out.println(parents[j]);
                /*children[j] = new Gene(parents[j]);*/
                /*nextGeneration[nextGenerationIndex++] = children[j];*/

                subNodes[j] = parents[j].getRandomSubNode(subNodeLevel);

                if (subNodes[j] == null) {
                    System.out.println("Failed to do crossover. Did not find an appropriate sub node.");
                    continue outer;
                }

                if (subNodeLevel < 0) subNodeLevel = subNodes[j].getLevel();
            }

            for (int j = 0; j < parents.length; j++) {
                final int k = (j + 1) % parents.length;
                
                System.out.println(parents[j]);
                System.out.println(parents[k]);

                parents[j].print();
                parents[k].print();

                subNodes[j].swapParents(subNodes[k]);

                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                parents[j].print();
                parents[k].print();

                /*children[j]*/parents[j].updateFitness();
                /*children[k]*/parents[k].updateFitness();

                System.out.println(parents[j]);
                System.out.println(parents[k]);
                System.out.println("---------------------------------------------------------------------------");
            }
        }
    }
}
