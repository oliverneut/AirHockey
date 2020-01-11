package app;

import app.friends.FriendController;
import app.friends.FriendDAO;
import app.leaderboard.LeaderboardController;
import app.leaderboard.LeaderboardDAO;
import app.login.LoginController;
import app.match.MatchController;
import app.match.MatchWebSocketHandler;
import app.user.UserController;
import app.user.UserDAO;
import app.userstats.UserStatsController;
import app.userstats.UserStatsDAO;
import app.util.Path;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

//import static spark.debug.DebugScreen.enableDebugScreen;

public class Application {


    /**
     * Server start.
     *
     * @param args vm args.
     */
    @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();
        UserStatsDAO userStatsDAO = new UserStatsDAO();
        FriendDAO friendDAO = new FriendDAO();
        LeaderboardDAO leaderboardDAO = new LeaderboardDAO();

        UserController userController = new UserController(userDAO);
        LoginController loginController = new LoginController(userController);
        UserStatsController userStatsController = new UserStatsController(userStatsDAO, userDAO);
        FriendController friendController = new FriendController(friendDAO, userDAO, loginController);
        MatchController matchController = new MatchController();
        LeaderboardController leaderboardController = new LeaderboardController(leaderboardDAO, loginController);

        MatchWebSocketHandler matchWebSocketHandler = new MatchWebSocketHandler(matchController);

        port(6969);

        //incompatible with spark-core dependency ¯\_(ツ)_/¯
        //enableDebugScreen();

        webSocket(Path.MATCH, matchWebSocketHandler);

        get(Path.REGISTER, loginController.handleCreateUser);
        get(Path.LOGIN, loginController.handleLogin);
        get(Path.LOGOUT, loginController.handleLogoutPost);

        get(Path.SEARCHUSERNAME, friendController.searchUsers);
        get(Path.USERSTATS, userStatsController.getUserStats);

        get(Path.FRIENDLEADERBOARD, leaderboardController.getFriendTopPlayers);
        get(Path.GENERALLEADERBOARD, leaderboardController.getGeneralTopPlayers);
        get(Path.FRIENDS, friendController.getFriends);
        get(Path.RECEIVEDREQUESTS, friendController.getReceivedRequests);
        get(Path.SENTREQUESTS, friendController.getSentRequests);
        get(Path.SENDREQUEST, friendController.sendRequest);
        get(Path.ACCEPTREQUEST, friendController.acceptRequest);

        before((Request request, Response response) -> {
            System.out.println(request.raw().getPathInfo());
        });

        notFound((Request request, Response response) -> {
            response.status(404);
            return "Not found.";
        });

    }
}
