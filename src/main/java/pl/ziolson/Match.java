package pl.ziolson;

import java.util.Objects;

public class Match {
    private final String homeTeam;
    private final String awayTeam;
    private byte homeScore;
    private byte awayScore;

    public Match(String homeTeam, String awayTeam) {
        this.homeTeam = Objects.requireNonNull(homeTeam, "Home team must not be null");
        this.awayTeam = Objects.requireNonNull(awayTeam, "Away team must not be null");
        if (homeTeam.isEmpty() || awayTeam.isEmpty()) {
            throw new IllegalArgumentException("Team name must not be empty");
        }
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Teams must be different");
        }
        this.homeScore = 0;
        this.awayScore = 0;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(byte homeScore) {
        if (homeScore < 0) {
            throw new IllegalArgumentException("Score must be greater or equal to 0");
        }
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(byte awayScore) {
        if (awayScore < 0) {
            throw new IllegalArgumentException("Score must be greater or equal to 0");
        }
        this.awayScore = awayScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Match match = (Match) o;
        return homeTeam.equals(match.homeTeam) && awayTeam.equals(match.awayTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeam, awayTeam);
    }

    @Override
    public String toString() {
        return homeTeam + " " + homeScore + " - " + awayTeam + " " + awayScore;
    }
}
