package app.user;

import app.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

        ResultSet resultSet = null;

        try {
            if (username == null || username.isEmpty()) {
                return null;
            }

            connection = DatabaseConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE username = ?;");
            statement.setString(1, username);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                User user = new User(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3));

                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {

                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
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

            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO projects_SEMAirHockey.users (username, password) "
                    + "VALUES (?, ?);");
            statement.setString(1, username);
            statement.setString(2, password);

            int updated = statement.executeUpdate();

            final int One = 1;
            if (updated == One) {
                connection.commit();
                return getByUsername(username);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            e.getSQLState();
        }

        return null;
    }

    /**
     * Get usernames for given userids.
     *
     * @param userids .
     * @return List of resp. usernames.
     */
    public List<String> getUsernames(List<Integer> userids) {
        ResultSet resultSet = null;

        if (userids.isEmpty()) {
            return new ArrayList<String>();
        }
        try {

            connection = DatabaseConnection.getConnection();

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 1; i < userids.size(); i++) {
                stringBuilder.append("?, ");
            }
            stringBuilder.append("?");
            String str = stringBuilder.toString();

            PreparedStatement statement = connection.prepareStatement(
                "SELECT username FROM users WHERE userid in (" + str + ");");

            for (int i = 0; i < userids.size(); i++) {
                statement.setInt(i + 1, userids.get(i));
            }

            resultSet = statement.executeQuery();

            List<String> usernames = new ArrayList<>();
            while (resultSet.next()) {
                usernames.add(resultSet.getString(1));
            }

            return usernames;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {

                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
