package genome;

import functions.*;
import help.Helper;
import terminals.Terminal;
import tree.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Laura on 11.01.2017.
 */
public class Genome {
    private int treeCount;
    private int treeDepth;
    private GeneticTree genes[];
    private Helper helper;

    private double recombinationRate;
    private double mutationRate;


    public Genome(int treeCount, int treeDepth, double mutationRate, double recombinationRate) {
        this.treeCount = treeCount;
        this.treeDepth = treeDepth;
        this.mutationRate = mutationRate;
        this.recombinationRate = recombinationRate;
        this.genes = new GeneticTree[treeCount];
        this.helper = new Helper();
        init();
    }

    public void init() {
        for (int i = 0; i < treeCount; i++) {
            final GeneticTreeBuilder builder = new GeneticTreeBuilder(treeDepth);
            final GeneticTree tree = builder.build();
            genes[i] = tree;
        }
    }

    public void mutate() {
        for (int i = 0; i < treeCount*mutationRate; i++) {
            int treeNo = ThreadLocalRandom.current().nextInt(0, treeCount);
            System.out.println("Mutate Tree Nr: " + treeNo);
            GeneticTreeComponent root = genes[treeNo].getRoot();

            List<GeneticTreeComponent> components = new ArrayList<>();
            iterateTree(root, components);

            int componentNo = ThreadLocalRandom.current().nextInt(0, components.size());
            System.out.println("Mutate Compontant Nr: " + componentNo);

            GeneticTreeComponent component = components.get(componentNo);
            if (component.type == GeneticTreeComponent.LEAF) {
                System.out.println("Mutate Leaf... ");
                GeneticTreeLeaf leaf = (GeneticTreeLeaf) component;

                Terminal oldTerminal = leaf.getTerminal();
                Terminal newTerminal = helper.getRandomTerminal();

                leaf.setTerminal(newTerminal);

                System.out.println("Mutated " + oldTerminal.getValue() + " to " + newTerminal.getValue());
            } else if (component.type == GeneticTreeComponent.NODE) {
                System.out.println("Mutate Node... ");
                GeneticTreeNode node = (GeneticTreeNode) component;

                Function oldFunction = node.getFunction();
                Function newFunction = helper.getRandomFunction();

                if (oldFunction.numParams == newFunction.numParams) {
                    node.setFunction(newFunction);
                } else {
                    System.out.println("Couldn't mutate because Functions have different number of Params");
                }

                System.out.println("Mutated " + oldFunction.toString() + " to " + newFunction.toString());
            }
        }
    }

    public void crossover() {

    }

    public void iterateTree(GeneticTreeComponent root, List<GeneticTreeComponent> children){
        if (root.type == GeneticTreeComponent.NODE) {
            GeneticTreeNode node = (GeneticTreeNode) root;
            children.add(node);

            for (int i = 0; i < node.getChildren().size(); i++) {
                iterateTree(node.getChildren().get(i), children);
            }
        }
        else if (root.type == GeneticTreeComponent.LEAF) {
            children.add(root);
        }
    }
}