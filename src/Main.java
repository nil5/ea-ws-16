import gp.Executor;
import help.Config;

/**
 * Created by Nils on 10.01.2017.
 */
public class Main {
    private static double[] input, output;

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        new Executor(Config.THREADCOUNT);
    }
}
