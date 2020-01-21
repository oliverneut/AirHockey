package app.userstats;

import app.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "PMD.DataflowAnomalyAnalysis"})
public class UserStatsDAO {
    private static Connection connection;

    public UserStatsDAO() {
    }

    private int executeStatement(PreparedStatement statement) {

        try (ResultSet resultSet = statement.executeQuery()) {

            int result = 0;
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    int retrieveMatchesPlayed(int userid) {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT played FROM user_stats WHERE userid = ?;")) {
            statement.setInt(1, userid);

            return executeStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    int retrieveMatchesWon(int userid) {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT won FROM user_stats WHERE userid = ?;")) {
            statement.setInt(1, userid);

            return executeStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    int retrieveGoalsScored(int userid) {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT goals_scored FROM user_stats WHERE userid = ?;")) {
            statement.setInt(1, userid);

            return executeStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    int retrieveGoalsAgainst(int userid) {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT goals_against FROM user_stats WHERE userid = ?;")) {
            statement.setInt(1, userid);

            return executeStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
