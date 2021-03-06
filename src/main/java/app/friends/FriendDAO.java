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

    /**
     * Default Constructor.
     */
    public FriendDAO() {
    }

    /**
     * Get friends of user from database.
     *
     * @param userid .
     * @return List of friend username.
     */
    public List<String> retrieveFriends(int userid) {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        List<String> friends = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT username FROM users JOIN friends ON users.userid = friends.addressee "
                        + "WHERE friends.status = 1 AND friends.requester = ?;")) {
            statement.setInt(1, userid);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    friends.add(resultSet.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT username FROM users JOIN friends ON users.userid = friends.requester "
                        + "WHERE friends.status = 1 AND friends.addressee = ?;")) {
            statement.setInt(1, userid);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    friends.add(resultSet.getString(1));
                }
                return friends;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get sent friend requests from database.
     *
     * @param userid .
     * @return List of usernames of requests sent.
     */
    public List<String> retrieveSentRequests(int userid) {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        List<String> sentRequests = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT username FROM users JOIN friends ON users.userid = friends.addressee "
                        + "WHERE friends.status = 0 AND friends.requester = ?;")) {
            statement.setInt(1, userid);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    sentRequests.add(resultSet.getString(1));
                }

                return sentRequests;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get received friend requests from database.
     *
     * @param userid .
     * @return List of usernames of received requests.
     */
    public List<String> retrieveReceivedRequests(int userid) {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        List<String> receivedRequests = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT username FROM users JOIN friends ON users.userid = friends.requester "
                        + "WHERE friends.status = 0 AND friends.addressee = ?;")) {
            statement.setInt(1, userid);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    receivedRequests.add(resultSet.getString(1));
                }

                return receivedRequests;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Delete friend.
     *
     * @param userid Userid of user whose friend to delete.
     * @param friend Username of friend to be deleted.
     * @return If successfully updated database or not.
     */
    public boolean deleteFriend(int userid, String friend) {
        assert friend != null;

        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM friends WHERE status = 1 AND ? = ? AND ? = "
                        + "(SELECT userid FROM users WHERE username = ?);")) {

            //first case where (user, friend) = (requester, addressee) in database
            statement.setString(1, "requester");
            statement.setString(3, "addressee");

            statement.setInt(2, userid);
            statement.setString(4, friend);

            int updatedRows = statement.executeUpdate();

            //second case where (user, friend) = (addressee, requester) in database
            statement.setString(1, "addressee");
            statement.setString(3, "requester");

            updatedRows += statement.executeUpdate();

            final int One = 1;
            return updatedRows == One;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Send friend request.
     *
     * @param userid Userid of user who sends request.
     * @param friend Username of user to send request to.
     * @return If successfully updated database.
     */
    public boolean sendRequest(int userid, String friend) {
        assert friend != null;

        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO friends (requester, addressee, status) "
                        + "VALUES (?, (SELECT userid FROM users WHERE username = ?), 0);")) {

            statement.setInt(1, userid);
            statement.setString(2, friend);

            int updatedRows = statement.executeUpdate();

            final int One = 1;
            return updatedRows == One;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Accept friend request.
     *
     * @param userid    Userid of user who received request.
     * @param requester Username of user who sent request.
     * @return If successfully updated database or not.
     */
    public boolean acceptRequest(int userid, String requester) {
        assert requester != null;

        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE friends SET status = 1 WHERE addressee = ? AND requester = "
                        + "(SELECT userid FROM users WHERE username = ?) AND status = 0;")) {

            statement.setInt(1, userid);
            statement.setString(2, requester);

            int updatedRows = statement.executeUpdate();

            final int One = 1;
            return updatedRows == One;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Decline friend request.
     *
     * @param userid    Userid of user who received request.
     * @param requester Username of user who sent request.
     * @return If successfully updated database or not.
     */
    public boolean declineRequest(int userid, String requester) {
        assert requester != null;

        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM friends WHERE addressee = ? AND requester = "
                        + "(SELECT userid FROM users WHERE username = ?) AND status = 0;")) {

            statement.setInt(1, userid);
            statement.setString(2, requester);

            int updatedRows = statement.executeUpdate();

            final int One = 1;
            return updatedRows == One;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
