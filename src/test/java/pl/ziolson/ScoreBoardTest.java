package pl.ziolson;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardTest {

    @Test
    void startMatch() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Mexico", "Canada");
        assertEquals(1, scoreBoard.getSummary().size());
    }

    @Test
    void updateScore() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch("Mexico", "Canada");
        scoreBoard.updateScore("Mexico", "Canada", 1, 0);
        assertEquals(1, scoreBoard.getSummary().size());
        assertEquals(1, scoreBoard.getSummary().getFirst().getHomeScore());
        assertEquals(0, scoreBoard.getSummary().getFirst().getAwayScore());
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
    void getSummary() {
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