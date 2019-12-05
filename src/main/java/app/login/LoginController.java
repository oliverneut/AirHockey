package app.login;

import static app.Application.userDAO;
import static app.util.RequestUtil.getQueryLoginRedirect;
import static app.util.RequestUtil.getQueryPassword;
import static app.util.RequestUtil.getQueryUser;
import static app.util.RequestUtil.removeSessionAttrLoginRedirect;
import static spark.Spark.halt;

import app.user.User;
import app.user.UserController;
import app.util.Path;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginController {

    public static Route handleLoginPost = (Request request, Response response) -> {
        String username = getQueryUser(request);
        String password = getQueryPassword(request);

        if (!UserController.authenticate(username, password)) {
            halt(401, "Go away!");
        }

        User user = userDAO.getByUsername(username);

        request.session().attribute("currentUser", user.getUserid());

        String redirectPath = getQueryLoginRedirect(request);
        if (redirectPath != null) {
            removeSessionAttrLoginRedirect(request);
            response.redirect(redirectPath);
        }

        return "Authentication successful";

    };

    public static Route handleLogoutPost = (Request request, Response response) -> {
        request.session().removeAttribute("currentUser");
        request.session().attribute("loggedOut", true);
        return "Logged Out.";
    };

    public static Route handleCreateUser = (Request request, Response response) -> {
        String username = getQueryUser(request);
        String password = getQueryPassword(request);

        if (username == null || password == null
            || username.isEmpty() || password.isEmpty()) {
            response.status(400);
            return "Provide username and password.";
        }


        int status = UserController.createUser(username, password);

        switch (status) {
            case 1:
                return "Created user!";
            case 2:
                return "Username already in use";
            case 3:
                return "Couldn't create user.";
            default:
                return null;
        }
    };

    /**
     * Checks if user is logged in and redirects to login if not.
     * The origin of the request (request.pathInfo()) is saved in the session so
     * the user can be redirected back after login.
     *
     * @param request  Original request.
     * @param response Original response.
     */
    public static void ensureUserIsLoggedIn(Request request, Response response) {
        if (request.session().attribute("currentUser") == null) {
            response.status(401);
            response.redirect(Path.LOGIN);
        }
    }

    ;
}
