package pl.ziolson;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoard {

    private final List<Match> matches;

    public ScoreBoard() {
        this.matches = new ArrayList<>();
    }

    public void startMatch(String homeTeam, String awayTeam) {
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
    }

    public void finishMatch(String homeTeam, String awayTeam) {
    }

    public List<Match> getSummary() {
        return matches;
    }
}
