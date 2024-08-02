package pl.ziolson;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardTest {

    @Test
    void startMatchShouldStartNewMatch() {
        ScoreBoard scoreBoard = new ScoreBoard();

        scoreBoard.startMatch("Mexico", "Canada");

        assertEquals(1, scoreBoard.getSummary().size());
    }

    @Test
    void startMatchShouldThrowExceptionWhenDuplicatedMatch() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Mexico", "Canada");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.startMatch("Mexico", "Canada");
        });

        assertEquals("Match already exists", exception.getMessage());
        assertEquals(1, scoreBoard.getSummary().size());
    }

    @Test
    void startMatchShouldThrowExceptionWhenTeamNamesAreTheSame() {
        ScoreBoard scoreBoard = new ScoreBoard();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.startMatch("Mexico", "Mexico");
        });

        assertEquals("Teams must be different", exception.getMessage());
        assertEquals(0, scoreBoard.getSummary().size());
    }

    @Test
    void startMatchShouldThrowExceptionWhenTeamAlreadyPlayDifferentMatch() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Mexico", "Canada");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.startMatch("Mexico", "Brazil");
        });

        assertEquals("Team already plays different match", exception.getMessage());
        assertEquals(1, scoreBoard.getSummary().size());
    }

    @Test
    void startMatchShouldThrowExceptionWhenHomeTeamIsNull() {
        ScoreBoard scoreBoard = new ScoreBoard();

        Exception exception = assertThrows(NullPointerException.class, () -> {
            scoreBoard.startMatch(null, "Canada");
        });

        assertEquals("Home team must not be null", exception.getMessage());
        assertEquals(0, scoreBoard.getSummary().size());
    }

    @Test
    void startMatchShouldThrowExceptionWhenAwayTeamIsNull() {
        ScoreBoard scoreBoard = new ScoreBoard();

        Exception exception = assertThrows(NullPointerException.class, () -> {
            scoreBoard.startMatch("Mexico", null);
        });

        assertEquals("Away team must not be null", exception.getMessage());
        assertEquals(0, scoreBoard.getSummary().size());
    }

    @Test
    void updateScoreShouldUpdateMatchScore() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Mexico", "Canada");

        scoreBoard.updateScore("Mexico", "Canada", (byte) 1, (byte) 0);

        assertEquals(1, scoreBoard.getSummary().size());
        assertEquals(1, scoreBoard.getSummary().getFirst().getHomeScore());
        assertEquals(0, scoreBoard.getSummary().getFirst().getAwayScore());
    }

    @Test
    void updateScoreShouldAcceptOnlyPositiveNumbers() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Mexico", "Canada");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.updateScore("Mexico", "Canada", (byte) -1, (byte) -2);
        });

        assertEquals("Score must be greater or equal to 0", exception.getMessage());
        assertEquals(0, scoreBoard.getSummary().getFirst().getHomeScore());
        assertEquals(0, scoreBoard.getSummary().getFirst().getAwayScore());
    }

    @Test
    void updateScoreShouldThrowExceptionWhenHomeTeamIsNull() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Mexico", "Canada");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.updateScore(null, "Canada", (byte) 1, (byte) 0);
        });

        assertEquals(0, scoreBoard.getSummary().getFirst().getHomeScore());
        assertEquals(0, scoreBoard.getSummary().getFirst().getAwayScore());
    }

    @Test
    void updateScoreShouldThrowExceptionWhenAwayTeamIsNull() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Mexico", "Canada");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.updateScore("Mexico", null, (byte) 1, (byte) 0);
        });

        assertEquals(0, scoreBoard.getSummary().getFirst().getHomeScore());
        assertEquals(0, scoreBoard.getSummary().getFirst().getAwayScore());
    }

    @Test
    void updateScoreShouldThrowExceptionWhenMatchNotStarted() {
        ScoreBoard scoreBoard = new ScoreBoard();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.updateScore("Mexico", "Canada", (byte) 1, (byte) 0);
        });

        assertEquals("Match not found", exception.getMessage());
    }

    @Test
    void finishMatchShouldRemoveMatch() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Mexico", "Canada");
        assertEquals(1, scoreBoard.getSummary().size());

        scoreBoard.finishMatch("Mexico", "Canada");

        assertEquals(0, scoreBoard.getSummary().size());
    }

    @Test
    void finishMatchShouldThrowExceptionWhenMatchNotStarted() {
        ScoreBoard scoreBoard = new ScoreBoard();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.finishMatch("Mexico", "Canada");
        });

        assertEquals("Match not found", exception.getMessage());
    }

    @Test
    void finishMatchShouldThrowExceptionWhenHomeTeamIsNull() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Mexico", "Canada");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.finishMatch(null, "Canada");
        });

        assertEquals(1, scoreBoard.getSummary().size());
    }

    @Test
    void finishMatchShouldThrowExceptionWhenAwayTeamIsNull() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Mexico", "Canada");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scoreBoard.finishMatch("Mexico", null);
        });

        assertEquals(1, scoreBoard.getSummary().size());
    }

    @Test
    void getSummaryShouldSortByTotalScoresDescending() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Mexico", "Canada");
        scoreBoard.updateScore("Mexico", "Canada", (byte) 0, (byte) 5);
        scoreBoard.startMatch("Spain", "Brazil");
        scoreBoard.updateScore("Spain", "Brazil", (byte) 10, (byte) 2);

        List<Match> summary = scoreBoard.getSummary();

        assertEquals(2, summary.size());
        assertEquals("Spain", summary.get(0).getHomeTeam());
        assertEquals("Mexico", summary.get(1).getHomeTeam());
    }

    @Test
    void getSummaryShouldShouldSortByMostRecentWhenSameScore() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Germany", "France");
        scoreBoard.updateScore("Germany", "France", (byte) 2, (byte) 2);
        scoreBoard.startMatch("Argentina", "Australia");
        scoreBoard.updateScore("Argentina", "Australia", (byte) 3, (byte) 1);

        List<Match> summary = scoreBoard.getSummary();

        assertEquals(2, summary.size());
        assertEquals("Argentina", summary.get(0).getHomeTeam());
        assertEquals("Germany", summary.get(1).getHomeTeam());
    }

    @Test
    void getSummaryShouldReturnEmptyListWhenNoMatchesStarted() {
        ScoreBoard scoreBoard = new ScoreBoard();

        List<Match> summary = scoreBoard.getSummary();

        assertEquals(0, summary.size());
    }

    @Test
    void getSummaryShouldSortByTotalScoreDescendingAndMostRecentlyForSameScores() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Mexico", "Canada");
        scoreBoard.updateScore("Mexico", "Canada", (byte) 0, (byte) 5);
        scoreBoard.startMatch("Spain", "Brazil");
        scoreBoard.updateScore("Spain", "Brazil", (byte) 10, (byte) 2);
        scoreBoard.startMatch("Germany", "France");
        scoreBoard.updateScore("Germany", "France", (byte) 2, (byte) 2);
        scoreBoard.startMatch("Uruguay", "Italy");
        scoreBoard.updateScore("Uruguay", "Italy", (byte) 6, (byte) 6);
        scoreBoard.startMatch("Argentina", "Australia");
        scoreBoard.updateScore("Argentina", "Australia", (byte) 3, (byte) 1);

        List<Match> summary = scoreBoard.getSummary();

        assertEquals(5, summary.size());
        assertEquals("Uruguay", summary.get(0).getHomeTeam());
        assertEquals("Spain", summary.get(1).getHomeTeam());
        assertEquals("Mexico", summary.get(2).getHomeTeam());
        assertEquals("Argentina", summary.get(3).getHomeTeam());
        assertEquals("Germany", summary.get(4).getHomeTeam());
    }
}