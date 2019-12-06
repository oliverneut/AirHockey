package app.friends;

import app.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "PMD.DataflowAnomalyAnalysis"})
public class FriendDAO {
    private static Connection connection;

    public FriendDAO() {
    }

    /**
     * Get friends of user from database.
     *
     * @param userid .
     * @return List of friend username.
     */
    public List<String> retrieveFriends(int userid) {

        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();

            List<String> friends = new ArrayList<>();

            PreparedStatement statement = connection.prepareStatement(
                "SELECT username FROM users JOIN friends ON users.userid = friends.addressee "
                    + "WHERE friends.status = 1 AND friends.requester = ?;");
            statement.setInt(1, userid);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                friends.add(resultSet.getString(1));
            }
            resultSet.close();
            statement.close();

            statement = connection.prepareStatement(
                "SELECT username FROM users JOIN friends ON users.userid = friends.requester "
                    + "WHERE friends.status = 1 AND friends.addressee = ?;");
            statement.setInt(1, userid);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                friends.add(resultSet.getString(1));
            }

            return friends;


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
     * Get sent friend requests from database.
     *
     * @param userid .
     * @return List of usernames of requests sent.
     */
    public List<String> retrieveSentRequests(int userid) {

        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();

            List<String> sentRequests = new ArrayList<>();

            PreparedStatement statement = connection.prepareStatement(
                "SELECT username FROM users JOIN friends ON users.userid = friends.addressee "
                    + "WHERE friends.status = 0 AND friends.requester = ?;");
            statement.setInt(1, userid);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                sentRequests.add(resultSet.getString(1));
            }

            return sentRequests;

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
     * Get received friend requests from database.
     *
     * @param userid .
     * @return List of usernames of received requests.
     */
    public List<String> retrieveReceivedRequests(int userid) {
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();

            List<String> receivedRequests = new ArrayList<>();

            PreparedStatement statement = connection.prepareStatement(
                "SELECT username FROM users JOIN friends ON users.userid = friends.requester "
                    + "WHERE friends.status = 0 AND friends.addressee = ?;");
            statement.setInt(1, userid);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                receivedRequests.add(resultSet.getString(1));
            }

            return receivedRequests;

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
