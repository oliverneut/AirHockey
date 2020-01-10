package app.leaderboard;

import app.login.LoginController;
import com.mysql.cj.conf.ConnectionUrlParser;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;

import static app.util.RequestUtil.getSessionCurrentUser;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class LeaderboardController {

    transient LeaderboardDAO leaderboardDAO;
    transient LoginController loginController;

    public Route getGeneralTopPlayers = (Request request, Response response) -> {
        loginController.ensureUserIsLoggedIn(request, response);

        ArrayList<ConnectionUrlParser.Pair<String, Integer>> topPlayers =
                leaderboardDAO.retrieveGeneralBestPlayers();

        response.status(200);
        return topPlayers.toString();
    };

    public Route getFriendTopPlayers = (Request request, Response response) -> {
        loginController.ensureUserIsLoggedIn(request, response);

        int userid = getSessionCurrentUser(request);

        ArrayList<ConnectionUrlParser.Pair<String, Integer>> topPlayers =
                leaderboardDAO.retrieveBestFriendsPlayers(userid);

        response.status(200);
        return topPlayers.toString();
    };



    /**
     * Constructor.
     *
     * @param leaderboardDAO .
     * @param loginController .
     */
    public LeaderboardController(LeaderboardDAO leaderboardDAO, LoginController loginController) {
        this.leaderboardDAO = leaderboardDAO;
        this.loginController = loginController;
    }
}
