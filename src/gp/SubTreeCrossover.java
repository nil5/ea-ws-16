package gp;

import help.Config;
import terminals.Terminal;
import tree.GeneticTreeComponent;
import tree.GeneticTreeLeaf;
import tree.GeneticTreeNode;

import java.util.List;

/**
 * Created by Nils on 19.01.2017.
 */
public class SubTreeCrossover extends Mutator {
    private final boolean D = false;
    private final Selection selection;

    public SubTreeCrossover(final double mutationRate, final boolean protectBest, final int tournamentSize) {
        super(2, 2, mutationRate, protectBest);

        this.selection = new TournamentSelection(tournamentSize, protectBest);
    }

    @Override
    public void mutate(Genome genome) {
        outer: for (int i = 0; i < mutationRate * genome.length; i++) {
            final Gene[] parents = new Gene[inputGeneCount];
            final GeneticTreeNode[] subNodes = new GeneticTreeNode[inputGeneCount];

            for (int j = 0; j < inputGeneCount; j++) {
                Gene gene = j == 0 ? null : parents[j - 1];
                while (hasGene(parents, gene)) gene = selection.select(genome);
                parents[j] = gene;
            }

            if (D) System.out.println("==========  SUBTREE CROSSOVER  ==========");

            for (int j = 0, subNodeLevel = -1; j < parents.length; j++) {
                subNodes[j] = parents[j].getRandomSubNode(subNodeLevel);

                if (subNodes[j] == null) {
                    if (D) {
                        System.out.println("Failed to do crossover. Did not find an appropriate sub node.");
                        System.out.println(parents[j].toString());
                    }
                    continue outer;
                }

                if (hasInputLeaf(subNodes[j])) {
                    if (D) System.out.println("Failed to do crossover. Input leafs are not allowed.");
                    //continue outer;
                }

                if (subNodeLevel < 0) subNodeLevel = subNodes[j].getLevel();
            }

            for (int j = 0, k = j + 1; k < parents.length; j++, k++) {
                if (D) System.out.println(parents[j].toString() + "\n" + parents[k].toString());

                subNodes[j].swapParents(subNodes[k]);

                if (D) System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

                parents[j].updateFitness();
                parents[k].updateFitness();

                genome.updateBestGeneIndex();

                if (D) {
                    System.out.println(parents[j].toString() + "\n" + parents[k].toString());
                    System.out.println("---------------------------------------------------------------------------");
                }
            }
        }
    }

    private static boolean hasInputLeaf(final GeneticTreeNode node) {
        final List<GeneticTreeComponent> children = node.getChildren();
        for (GeneticTreeComponent child : children) {
            if (child.type == Config.LEAF) {
                GeneticTreeLeaf l = (GeneticTreeLeaf) child;
                if (l.getTerminal().getType() == Config.INPUT) return true;
            } else {
                GeneticTreeNode n = (GeneticTreeNode) child;
                if (hasInputLeaf(n)) return true;
            }
        }
        return false;
    }

    private static boolean hasGene(final Gene[] genes, final Gene gene) {
        for (final Gene g : genes) if (gene == g) return true;
        return false;
    }
}
