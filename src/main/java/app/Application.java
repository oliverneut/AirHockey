package app;

import app.login.LoginController;
import app.match.Match;
import app.match.MatchController;
import app.match.MatchWebSocketHandler;
import app.user.UserDAO;

import java.util.*;

import static spark.Spark.*;

//import static spark.debug.DebugScreen.enableDebugScreen;


public class Application {

    //used to interface with the users in database
    public static UserDAO userDAO;

    //used to interface with the matches in database
    //public static MatchDAO matchDAO;

    public static Queue<UUID> waitingMatches;

    public static Map<UUID, Match> runningMatches;

    /**
     * Server start.
     *
     * @param args vm args.
     */
    public static void main(String[] args) {

        userDAO = new UserDAO();

        waitingMatches = new LinkedList<>();

        runningMatches = new HashMap<>();

        port(6969);

        //incompatible with spark-core dependency ¯\_(ツ)_/¯
        //enableDebugScreen();

        path("/user", () -> {
            post("/register", LoginController.handleCreateUser);
            post("/login", LoginController.handleLoginPost);
            post("/logout", LoginController.handleLogoutPost);
        });

//        path("/match", () -> {
//
//            post("/join", MatchController.handleMatchJoin);
//
//            webSocket("/join/:matchid", MatchWebSocketHandler.class);
//            init();
//        });
    }
}
