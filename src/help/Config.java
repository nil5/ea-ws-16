package help;

/**
 * Created by Laura on 20.01.2017.
 */
public final class Config {
    public static final int THREADCOUNT = 8;
    public static final int GENECOUNT = 50;
    public static final int GENERATIONCOUNT = 2000;
    public static final int TOURNAMENTSIZE = 10;
    public static final int INIT = 1;
    public static final double MUTATIONRATE = 0.5;
    public static final double RECOMBINATIONRATE = 0.25;
    public static final boolean PROTECT_BEST = true;
    public static final double MIN = 0.0;
    public static final double MAX = 20.0;

    public static final String FILENAME = "values.csv";

    public static final int MODE_FULL = 1;
    public static final int MODE_GROW = 2;
    public static final int MODE_HALF = 3;
    public static final int MAXTREEDEPTH = 6;

    public static final int INPUT = 0;
    public static final int RANDOM = 1;
    public static final int CONSTANT = 2;

    public static final int NODE = 1;
    public static final int LEAF = 2;
}
