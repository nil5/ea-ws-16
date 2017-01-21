import gp.Evolution;
import gp.Genome;
import help.Config;

/**
 * Created by Nils on 10.01.2017.
 */
public class Main {
    private static double[] input, output;

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Genome genome = new Genome(Config.MODE_HALF, Config.GENECOUNT);

        final Evolution evolution = new Evolution(genome);
        final Thread evolutionThread = new Thread(evolution);

        evolutionThread.start();
    }
}
