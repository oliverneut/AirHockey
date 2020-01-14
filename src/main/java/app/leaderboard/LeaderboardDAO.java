package app.leaderboard;

import app.database.DatabaseConnection;
import com.mysql.cj.conf.ConnectionUrlParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class LeaderboardDAO {

    private static Connection connection;

/**    public static void main(String[] args) {
        retrieveGeneralBestPlayers();
    }
**/
    /**
     * Default Constructor.
     */
    public LeaderboardDAO() {
    }

    /**
     * Retrieve top 10 best players from the entire game.
     *
     * @return If successfully updated database or not.
     */
    @SuppressWarnings("Duplicates")
    public ArrayList<ConnectionUrlParser.Pair<String, Integer>> retrieveGeneralBestPlayers() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "select userid, (won / played) as win_ratio from user_stats "
                        + "order by win_ratio desc limit 10;")) {

            try (ResultSet resultSet = statement.executeQuery()) {
                ArrayList<ConnectionUrlParser.Pair<String, Integer>> topPlayers = new ArrayList<>();

                while (resultSet.next()) {
                    topPlayers.add(new ConnectionUrlParser
                            .Pair(resultSet.getString(1), resultSet.getString(2)));
                }

                System.out.println(topPlayers.toString());

                return topPlayers;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     *  Retrieve top 10 best players from the player's friends.
     *
     * @return If successfully updated database or not.
     */
    public ArrayList<ConnectionUrlParser.Pair<String, Integer>>
        retrieveBestFriendsPlayers(int userid) {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        try (PreparedStatement statement = connection.prepareStatement(
                " select users.username, user_stats.won / user_stats.played as win_ratio "
                        + "from user_stats join (select CASE WHEN friends.requester = a.userid "
                        + "THEN friends.addressee ELSE friends.requester END as id from friends "
                        + "join (select user_stats.userid from user_stats) as a on "
                        + "(friends.addressee = a.userid or friends.requester = a.userid)"
                        + "where friends.status = 1 and a.userid = ?) as c on "
                        + "(c.id = user_stats.userid) join users on "
                        + "(c.id = users.userid) order by win_ratio desc limit 10;")) {

            statement.setInt(1, userid);

            try (ResultSet resultSet = statement.executeQuery()) {
                ArrayList<ConnectionUrlParser.Pair<String, Integer>> topPlayers = new ArrayList<>();

                while (resultSet.next()) {
                    topPlayers.add(new ConnectionUrlParser.Pair(resultSet.getString(1),
                            resultSet.getString(2)));
                }

                return topPlayers;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
