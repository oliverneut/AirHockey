package app;

import static spark.Spark.path;
import static spark.Spark.port;
import static spark.Spark.post;

import app.login.LoginController;
import app.user.UserDao;


//import static spark.debug.DebugScreen.enableDebugScreen;


public class Application {

    //used to interface with the users in database
    public static UserDao userDAO;

    /**
     * Server start.
     *
     * @param args vm args.
     */
    public static void main(String[] args) {

        userDAO = new UserDao();

        port(6969);

        //incompatible with spark-core dependency ¯\_(ツ)_/¯
        //enableDebugScreen();

        path("/user", () -> {
            post("/register", LoginController.handleCreateUser);
            post("/login", LoginController.handleLoginPost);
            post("/logout", LoginController.handleLogoutPost);
        });

    }
}
