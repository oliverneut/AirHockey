package app.user;

import app.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "PMD.DataflowAnomalyAnalysis"})
public class UserDAO {

    private static Connection connection;

    public UserDAO() {
    }

    /**
     * Retrieve user from database using email address.
     *
     * @param username .
     * @return The user object.
     */
    public User getByUsername(String username) {

        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        try (PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM users WHERE username = ?;")) {

            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {

                    User user = new User(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3));

                    return user;
                }
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Add new User to the database.
     *
     * @param username .
     * @param password .
     * @return The new user.
     */
    public User registerNewUser(String username, String password) {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO projects_SEMAirHockey.users (username, password) "
                        + "VALUES (?, ?);")) {

            statement.setString(1, username);
            statement.setString(2, password);

            int updated = statement.executeUpdate();

            final int One = 1;
            if (updated == One) {
                connection.commit();
                return getByUsername(username);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get username for given userid.
     *
     * @param userid .
     * @return Resp userid.
     */
    public String getUsername(Integer userid) {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT username FROM users WHERE userid = ?;")) {

            statement.setInt(1, userid);
            try (ResultSet resultSet = statement.executeQuery()) {

                String username = null;
                if (resultSet.next()) {
                    username = resultSet.getString(1);
                }
                return username;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
