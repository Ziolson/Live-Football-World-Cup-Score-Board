package pl.ziolson;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardTest {

    @Test
    void startMatch_happyPath() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Mexico", "Canada");
        assertEquals(1, scoreBoard.getSummary().size());
    }

    @Test
    void startMatch_duplicateMatch() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Mexico", "Canada");
        scoreBoard.startMatch("Mexico", "Canada");
        assertEquals(1, scoreBoard.getSummary().size());
    }

    @Test
    void startMatch_nullHomeTeam() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch(null, "Canada");
        assertEquals(0, scoreBoard.getSummary().size());
    }

    @Test
    void startMatch_nullAwayTeam() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Mexico", null);
        assertEquals(0, scoreBoard.getSummary().size());
    }

    @Test
    void updateScore_happyPath() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Mexico", "Canada");
        scoreBoard.updateScore("Mexico", "Canada", 1, 0);
        assertEquals(1, scoreBoard.getSummary().size());
        assertEquals(1, scoreBoard.getSummary().getFirst().getHomeScore());
        assertEquals(0, scoreBoard.getSummary().getFirst().getAwayScore());
    }

    @Test
    void updateScore_nullHomeTeam() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Mexico", "Canada");
        Exception exception = assertThrows(Exception.class, () -> {
            scoreBoard.updateScore(null, "Canada", 1, 0);
        });
    }

    @Test
    void updateScore_nullAwayTeam() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Mexico", "Canada");
        Exception exception = assertThrows(Exception.class, () -> {
            scoreBoard.updateScore("Mexico", null, 1, 0);
        });
    }

    @Test
    void updateScore_matchNotStarted() {
        ScoreBoard scoreBoard = new ScoreBoard();
        Exception exception = assertThrows(Exception.class, () -> {
            scoreBoard.updateScore("Mexico", "Canada", 1, 0);
        });
    }

    @Test
    void finishMatch() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Mexico", "Canada");
        assertEquals(1, scoreBoard.getSummary().size());
        scoreBoard.finishMatch("Mexico", "Canada");
        assertEquals(0, scoreBoard.getSummary().size());
    }

    @Test
    void finishMatch_matchNotStarted() {
        ScoreBoard scoreBoard = new ScoreBoard();
        Exception exception = assertThrows(Exception.class, () -> {
            scoreBoard.finishMatch("Mexico", "Canada");
        });
    }

    @Test
    void finishMatch_nullHomeTeam() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Mexico", "Canada");
        Exception exception = assertThrows(Exception.class, () -> {
            scoreBoard.finishMatch(null, "Canada");
        });
    }

    @Test
    void finishMatch_nullAwayTeam() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Mexico", "Canada");
        assertEquals(1, scoreBoard.getSummary().size());
        Exception exception = assertThrows(Exception.class, () -> {
            scoreBoard.finishMatch("Mexico", null);
        });
    }

    @Test
    void getSummary_happyPath() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Mexico", "Canada");
        scoreBoard.updateScore("Mexico", "Canada", 0, 5);
        scoreBoard.startMatch("Spain", "Brazil");
        scoreBoard.updateScore("Spain", "Brazil", 10, 2);

        List<Match> summary = scoreBoard.getSummary();

        assertEquals(2, summary.size());
        assertEquals("Spain", summary.get(0).getHomeTeam());
        assertEquals("Mexico", summary.get(1).getHomeTeam());
    }

    @Test
    void getSummary_sameScore() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Germany", "France");
        scoreBoard.updateScore("Germany", "France", 2, 2);
        scoreBoard.startMatch("Argentina", "Australia");
        scoreBoard.updateScore("Argentina", "Australia", 3, 1);

        List<Match> summary = scoreBoard.getSummary();

        assertEquals(2, summary.size());
        assertEquals("Argentina", summary.get(0).getHomeTeam());
        assertEquals("Germany", summary.get(1).getHomeTeam());
    }
}