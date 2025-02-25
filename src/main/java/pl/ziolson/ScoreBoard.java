package pl.ziolson;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ScoreBoard {

    private final List<Match> matches;

    public ScoreBoard() {
        this.matches = new LinkedList<>();
    }

    public void startMatch(String homeTeam, String awayTeam) {
        Match match = new Match(homeTeam, awayTeam);
        if (matches.contains(match)) {
            throw new IllegalArgumentException("Match already exists");
        }
        matches.stream().filter(m -> m.getHomeTeam().equals(homeTeam) || m.getAwayTeam().equals(homeTeam))
                .findAny()
                .ifPresent(m -> {
                    throw new IllegalArgumentException("Team already plays different match");
                });
        matches.add(match);
    }

    public void updateScore(String homeTeam, String awayTeam, byte homeScore, byte awayScore) {
        Match match = findMatch(homeTeam, awayTeam);
        match.setHomeScore(homeScore);
        match.setAwayScore(awayScore);
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        Match match = findMatch(homeTeam, awayTeam);
        matches.remove(match);
    }

    public List<Match> getSummary() {
        List<Match> sortedMatches = new LinkedList<>(matches);
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
