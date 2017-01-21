package help;

/**
 * Created by Laura on 20.01.2017.
 */
public final class Config {
    public static final String FILENAME = "values.csv";
    public static final int GENECOUNT = 1000;
    public static final int GENERATIONCOUNT = 5000;

    public static final int MODE_FULL = 1;
    public static final int MODE_GROW = 2;
    public static final int MODE_HALF = 3;
    public static final int MAXTREEDEPTH = 3;

    public static final int INPUT = 0;
    public static final int RANDOM = 1;
    public static final int CONSTANT = 2;

    public static final int NODE = 1;
    public static final int LEAF = 2;

    public static final int INIT = MODE_GROW;
    public static final double MUTATIONRATE = 0.05;
    public static final double RECOMBINATIONRATE = 1;
    public static final boolean PROTECT_BEST = true;

    public static final int TOURNAMENTSIZE = 5;

    public static final int THREADCOUNT = 1;

    public static final double MIN = 0.0;
    public static final double MAX = 2.5;

}
