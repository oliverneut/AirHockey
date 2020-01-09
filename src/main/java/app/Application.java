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


    private static UserDAO userDAO;
    private static UserStatsDAO userStatsDAO;
    private static FriendDAO friendDAO;
    private static LeaderboardDAO leaderboardDAO;

    private static UserController userController;
    private static LoginController loginController;
    private static UserStatsController userStatsController;
    private static FriendController friendController;
    private static MatchController matchController;
    private static LeaderboardController leaderboardController;

    private static MatchWebSocketHandler matchWebSocketHandler;

    /**
     * Server start.
     *
     * @param args vm args.
     */
    public static void main(String[] args) {

        userDAO = new UserDAO();
        userStatsDAO = new UserStatsDAO();
        friendDAO = new FriendDAO();
        leaderboardDAO = new LeaderboardDAO();

        userController = new UserController(userDAO);
        loginController = new LoginController(userController);
        userStatsController = new UserStatsController(userStatsDAO, userDAO);
        friendController = new FriendController(friendDAO, userDAO, loginController);
        matchController = new MatchController();
        leaderboardController = new LeaderboardController(leaderboardDAO, loginController);

        matchWebSocketHandler = new MatchWebSocketHandler(matchController);

        port(6969);

        //incompatible with spark-core dependency ¯\_(ツ)_/¯
        //enableDebugScreen();

        webSocket(Path.MATCH, matchWebSocketHandler);

        get(Path.REGISTER, loginController.handleCreateUser);
        get(Path.LOGIN, loginController.handleLogin);
        get(Path.LOGOUT, loginController.handleLogoutPost);

        get(Path.SEARCHUSERNAME, friendController.searchUsers);
        get(Path.USERSTATS, userStatsController.getUserStats);

        get(Path.FRIENDS, friendController.getFriends);
        get(Path.GENERALLEADERBOARD, leaderboardController.getTopPlayers);
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
