package app.leaderboard;

import static app.util.RequestUtil.getSessionCurrentUser;

import app.login.LoginController;
import com.github.cliftonlabs.json_simple.JsonObject;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName"})
public class LeaderboardController {

    transient LeaderboardDAO leaderboardDAO;
    transient LoginController loginController;

    public Route getGeneralTopPlayers = (Request request, Response response) -> {
        loginController.ensureUserIsLoggedIn(request, response);

        Map<String, Double> topPlayers =
                leaderboardDAO.retrieveGeneralBestPlayers();

        JsonObject reply = new JsonObject();
        reply.put("Head", "Top Players");
        reply.put("Top Players", topPlayers);

        response.status(200);
        return reply.toJson();
    };

    public Route getFriendTopPlayers = (Request request, Response response) -> {
        loginController.ensureUserIsLoggedIn(request, response);

        int userid = getSessionCurrentUser(request);

        Map<String, Double> topPlayers =
                leaderboardDAO.retrieveBestFriendsPlayers(userid);

        JsonObject reply = new JsonObject();
        reply.put("Head", "Top Friends");
        reply.put("Top Friends", topPlayers);

        response.status(200);

        String help = topPlayers.toString();

        return reply.toJson();
    };

    /**
     * Constructor.
     *
     * @param leaderboardDAO  .
     * @param loginController .
     */
    public LeaderboardController(LeaderboardDAO leaderboardDAO, LoginController loginController) {
        this.leaderboardDAO = leaderboardDAO;
        this.loginController = loginController;
    }
}
