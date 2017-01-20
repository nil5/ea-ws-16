package gp;

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
    public Gene[] select(final Genome genome, final int selectionSize) {
        final Gene[] selection = new Gene[selectionSize];
        final int startIndex = protectBest ? 1 : 0;

        for (int i = 0; i < selection.length; i++) {
            final Gene[] tournament = new Gene[tournamentSize];

            for (int j = 0; j < tournament.length; j++) {
                tournament[j] = genome.get(Helper.rand(startIndex, genome.length));
            }

            Arrays.sort(tournament);
            selection[i] = tournament[0];
        }

        return selection;
    }
}
