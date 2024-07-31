package pl.ziolson;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScoreBoard {

    private final List<Match> matches;

    public ScoreBoard() {
        this.matches = new ArrayList<>();
    }

    public void startMatch(String homeTeam, String awayTeam) {
        matches.add(new Match(homeTeam, awayTeam));
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        for (Match match : matches) {
            if (match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam)) {
                match.setHomeScore(homeScore);
                match.setAwayScore(awayScore);
            }
        }
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        matches.removeIf(match -> match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam));
    }

    public List<Match> getSummary() {
        List<Match> sortedMatches = new ArrayList<>(matches.reversed());
        sortedMatches.sort(Comparator.comparingInt(m -> (m.getHomeScore() + m.getAwayScore())));
        return sortedMatches;
    }
}
