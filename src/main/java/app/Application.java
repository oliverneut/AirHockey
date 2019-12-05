package app;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.notFound;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.webSocket;

import app.friends.FriendController;
import app.friends.FriendDAO;
import app.login.LoginController;
import app.match.Match;
import app.match.MatchWebSocketHandler;
import app.user.UserDAO;
import app.util.Path;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import spark.Request;
import spark.Response;


//import static spark.debug.DebugScreen.enableDebugScreen;


public class Application {

    public static Gson gson;

    //used to interface with the users in database
    public static UserDAO userDAO;

    public static FriendDAO friendDAO;

    //list of matches to be started
    public static Queue<UUID> waitingMatches;
    //set of all matches
    public static Map<UUID, Match> matches;

    /**
     * Server start.
     *
     * @param args vm args.
     */
    public static void main(String[] args) {

        gson = new Gson();

        userDAO = new UserDAO();

        friendDAO = new FriendDAO();

        matches = new HashMap<>();

        waitingMatches = new LinkedList<>();

        port(6969);

        //incompatible with spark-core dependency ¯\_(ツ)_/¯
        //enableDebugScreen();

        webSocket(Path.MATCH, MatchWebSocketHandler.class);

        post(Path.REGISTER, LoginController.handleCreateUser);
        get(Path.LOGIN, LoginController.handleLoginPost);
        post(Path.LOGOUT, LoginController.handleLogoutPost);

        get(Path.FRIENDS, FriendController.getFriends);
        get(Path.RECEIVEDREQUESTS, FriendController.getReceivedRequests);
        get(Path.SENTREQUESTS, FriendController.getSentRequests);

        before((Request request, Response response) -> {
            System.out.println(request.raw().toString());
        });

        notFound((Request request, Response response) -> {
            response.status(404);
            return "Not found.";
        });

    }
}
