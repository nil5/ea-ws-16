package main;

import gp.Executor;
import help.Config;

/**
 * Created by Nils on 10.01.2017.
 */
public class Main {
    private static final int progressStep = 2;
    private static final char progressChar = '=';

    private static int[] progress;
    private static int sumProgress;
    private static double maxProgress;

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        progress = new int[Config.THREADCOUNT];
        maxProgress = progress.length * Config.GENERATIONCOUNT;

        for (int i = 0; i < progress.length; i++) {
            progress[i] = 0;
        }

        new Executor(Config.THREADCOUNT);
    }

    public static synchronized void updateProgress(final int evolutionId, final int generationNum) {
        sumProgress -= progress[evolutionId - 1];
        progress[evolutionId - 1] = generationNum;
        sumProgress += generationNum;

        final double p = Math.round(sumProgress / maxProgress * 10000) / 100d;

        String progressStr = "";
        for (int i = progressStep; i <= 100; i += progressStep) {
            progressStr += p >= i ? "=" : " ";
        }

        System.out.print("\r[" + progressStr + "] " + p + "%");
    }
}
