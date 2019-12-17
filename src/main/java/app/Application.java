package app;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.notFound;
import static spark.Spark.port;
import static spark.Spark.webSocket;

import app.friends.FriendController;
import app.friends.FriendDAO;
import app.login.LoginController;
import app.match.MatchController;
import app.match.MatchWebSocketHandler;
import app.user.UserController;
import app.user.UserDAO;
import app.util.Path;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;


//import static spark.debug.DebugScreen.enableDebugScreen;


public class Application {

    //used to serialize java objects into json
    public static Gson gson;

    private static UserDAO userDAO;
    private static FriendDAO friendDAO;

    private static UserController userController;
    private static LoginController loginController;
    private static FriendController friendController;
    private static MatchController matchController;

    private static MatchWebSocketHandler matchWebSocketHandler;

    /**
     * Server start.
     *
     * @param args vm args.
     */
    public static void main(String[] args) {
        gson = new Gson();

        userDAO = new UserDAO();
        friendDAO = new FriendDAO();

        userController = new UserController(userDAO);
        loginController = new LoginController(userController);
        friendController = new FriendController(friendDAO, loginController);
        matchController = new MatchController();

        matchWebSocketHandler = new MatchWebSocketHandler(matchController);

        port(6969);

        //incompatible with spark-core dependency ¯\_(ツ)_/¯
        //enableDebugScreen();

        webSocket(Path.MATCH, matchWebSocketHandler);

        get(Path.REGISTER, loginController.handleCreateUser);
        get(Path.LOGIN, loginController.handleLogin);
        get(Path.LOGOUT, loginController.handleLogoutPost);

        get(Path.FRIENDS, friendController.getFriends);
        get(Path.RECEIVEDREQUESTS, friendController.getReceivedRequests);
        get(Path.SENTREQUESTS, friendController.getSentRequests);

        before((Request request, Response response) -> {
            System.out.println(request.raw().getPathInfo());
        });

        notFound((Request request, Response response) -> {
            response.status(404);
            return "Not found.";
        });

    }
}
