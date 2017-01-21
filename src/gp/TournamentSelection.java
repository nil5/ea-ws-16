package gp;

import help.Config;
import help.Helper;

import java.util.Arrays;

/**
 * Created by Nils on 19.01.2017.
 */
public class TournamentSelection extends Selection {
    public final int tournamentSize;
    public final boolean protectBest;

    public TournamentSelection(final int tournamentSize, final boolean protectBest) {
        this.tournamentSize = tournamentSize;
        this.protectBest = protectBest;
    }

    @Override
    public Gene select(final Genome genome) {
        final int bestIndex = protectBest ? genome.getBestGeneIndex() : -1;

        final Gene[] tournament = new Gene[Config.TOURNAMENTSIZE];

        for (int j = 0, r = bestIndex; j < tournament.length; j++) {
            while(r == bestIndex) r = Helper.rand(0, genome.length);
            tournament[j] = genome.get(r);
        }

        Arrays.sort(tournament);

        return tournament[0];
    }


}
