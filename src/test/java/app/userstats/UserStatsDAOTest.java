package app.userstats;

import static org.junit.jupiter.api.Assertions.assertEquals;

import app.database.DatabaseConnection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserStatsDAOTest {

    static UserStatsDAO userStatsDAO;

    static int userid = 15;

    @BeforeAll
    static void mainSetUp() {
        DatabaseConnection.test = true;
        userStatsDAO = new UserStatsDAO();
    }

    @AfterAll
    static void mainTearDown() {
        try {
            DatabaseConnection.getConnection().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void retrieveMatchesPlayedPass() {
        assertEquals(20, userStatsDAO.retrieveMatchesPlayed(userid));
    }

    @Test
    void retrieveMatchesWonPass() {
        assertEquals(10, userStatsDAO.retrieveMatchesWon(userid));
    }

    @Test
    void retrieveGoalsScoredPass() {
        assertEquals(80, userStatsDAO.retrieveGoalsScored(userid));
    }

    @Test
    void retrieveGoalsAgainstPass() {
        assertEquals(110, userStatsDAO.retrieveGoalsAgainst(userid));
    }
}