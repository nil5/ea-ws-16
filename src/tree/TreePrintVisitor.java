package tree;


import help.Config;

import java.util.List;

/**
 * Created by Nils on 18.01.2017.
 */
public class TreePrintVisitor implements TreeVisitor<Void> {
    @Override
    public Void visitComponent(GeneticTreeComponent component) {
        System.out.println(new String(new char[component.level]).replace("\0", "\t") + component);

        if (component.type == Config.NODE) {
            final List<GeneticTreeComponent> children = ((GeneticTreeNode) component).getChildren();
            for(GeneticTreeComponent child : children) child.accept(this);
        }

        return null;
    }
}
