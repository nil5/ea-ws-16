package tree;


import help.Config;

import java.util.List;

/**
 * Created by Nils on 18.01.2017.
 */
public class TreeToStringVisitor implements TreeVisitor<String> {
    private String result = "";

    @Override
    public String visitComponent(GeneticTreeComponent component) {
        result += new String(new char[component.level]).replace("\0", "\t") + component + "\n";

        if (component.type == Config.NODE) {
            final List<GeneticTreeComponent> children = ((GeneticTreeNode) component).getChildren();
            for(GeneticTreeComponent child : children) child.accept(this);
        }

        return result;
    }

    public String getResult() {
        return result.substring(0, result.length() - 1);
    }
}
