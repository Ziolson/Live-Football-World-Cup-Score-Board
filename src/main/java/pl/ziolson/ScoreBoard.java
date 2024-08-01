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
        if (homeTeam == null || awayTeam == null) {
            return;
        }

        Match match = new Match(homeTeam, awayTeam);
        if (!matches.contains(match)) {
            matches.add(match);
        }
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        Match match = findMatch(homeTeam, awayTeam);
        match.setHomeScore(homeScore);
        match.setAwayScore(awayScore);
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        Match match = findMatch(homeTeam, awayTeam);
        matches.remove(match);
    }

    public List<Match> getSummary() {
        List<Match> sortedMatches = new ArrayList<>(matches);
        sortedMatches.sort(Comparator.comparingInt(m -> (m.getHomeScore() + m.getAwayScore())));
        return sortedMatches.reversed();
    }

    private Match findMatch(String homeTeam, String awayTeam) {
        return matches.stream()
                .filter(match -> match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Match not found"));
    }
}
