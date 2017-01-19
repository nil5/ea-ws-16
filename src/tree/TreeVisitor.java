package tree;

/**
 * Created by Nils on 18.01.2017.
 */
public interface TreeVisitor<T> {
    T visitComponent(GeneticTreeComponent component);
}
