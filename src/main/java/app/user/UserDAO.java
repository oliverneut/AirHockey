package app.user;

import app.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class UserDAO {

    private static Connection connection;

    public UserDAO() {
    }

    /**
     * Retrieve user from database using email address.
     *
     * @param emailAddress .
     * @return The user object.
     */
    public User getByEmailAddress(String emailAddress) {
        try {
            if (emailAddress == null || emailAddress.isEmpty()) {
                return null;
            }

            connection = DatabaseConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE emailAddress = ?;");
            statement.setString(1, emailAddress);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4));

                resultSet.close();
                return user;
            }

            resultSet.close();
        } catch (SQLException e) {
            e.getSQLState();
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Add new User to the database.
     *
     * @param username     .
     * @param emailAddress .
     * @param password     .
     * @return The new user.
     */
    public User registerNewUser(String username, String emailAddress, String password) {
        try {
            connection = DatabaseConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO projects_SEMAirHockey.users (username, emailAddress, password) "
                    + "VALUES (?, ?, ?);");
            statement.setString(1, username);
            statement.setString(2, emailAddress);
            statement.setString(3, password);

            int updated = statement.executeUpdate();

            if (updated > 0) {
                connection.commit();
                return getByEmailAddress(emailAddress);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            e.getSQLState();
        }

        return null;
    }
}
