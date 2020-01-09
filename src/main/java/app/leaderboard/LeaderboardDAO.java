package app.leaderboard;

import app.database.DatabaseConnection;
import com.mysql.cj.conf.ConnectionUrlParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Get leader board from database.
 *
 * @return List of friend username.
 */
@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "PMD.DataflowAnomalyAnalysis"})
public class LeaderboardDAO {

    private static Connection connection;

    public LeaderboardDAO() {
    }



    /**
     * Accept friend request.
     *
     * @return If successfully updated database or not.
     */
    public ArrayList<ConnectionUrlParser.Pair<String, Integer>> retrieveGeneralBestPlayers() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "select userid, (won / played) as win_ratio from user_stats order by win_ratio desc limit 10;")) {

            try (ResultSet resultSet = statement.executeQuery()) {
                ArrayList<ConnectionUrlParser.Pair<String, Integer>> topPlayers = new ArrayList<>();

                while (resultSet.next()) {
                    topPlayers.add(new ConnectionUrlParser.Pair(resultSet.getString(1), resultSet.getString(2)));
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
    select friends.requester, friends.addressee, a.played, a.won, a.userid
    from friends
    join
            (select user_stats.userid, user_stats.won, user_stats.played
                    from user_stats
            ) as a on (friends.addressee=a.userid or friends.requester= a.userid)
    where ((friends.requester = 1) or (friends.addressee = 1)) and ((friends.status = 1) and (a.userid != 1))
**/


}
