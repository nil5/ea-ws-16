package tree;

import help.Config;
import terminals.Terminal;

/**
 * Created by Nils on 10.01.2017.
 */
public class GeneticTreeLeaf extends GeneticTreeComponent {
    private Terminal terminal;

    public GeneticTreeLeaf(final GeneticTreeLeaf leaf, final GeneticTreeNode parent) {
        super(parent, Config.LEAF);

        terminal = leaf.terminal;
    }

    public GeneticTreeLeaf(final GeneticTreeNode parent, Terminal terminal) {
        super(parent, Config.LEAF);

        this.terminal = terminal;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {this.terminal = terminal;}

    @Override
    public String toString() {
        return "LEAF level " + level + ", terminal: " + terminal;
    }
}
