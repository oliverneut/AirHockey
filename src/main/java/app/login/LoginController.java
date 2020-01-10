package app.login;

import static app.util.RequestUtil.getQueryLoginRedirect;
import static app.util.RequestUtil.getQueryPassword;
import static app.util.RequestUtil.getQueryUser;
import static app.util.RequestUtil.removeSessionAttrLoginRedirect;
import static spark.Spark.halt;

import app.user.User;
import app.user.UserController;
import app.util.Path;
import com.github.cliftonlabs.json_simple.JsonObject;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginController {

    public Route handleLogoutPost = (Request request, Response response) -> {
        request.session().removeAttribute("currentUser");
        request.session().attribute("loggedOut", true);
        response.status(200);
        return "Logged Out";
    };
    transient UserController userController;
    public Route handleCreateUser = (Request request, Response response) -> {
        String username = getQueryUser(request);
        String password = getQueryPassword(request);

        int status = userController.createUser(username, password);

        String info;
        switch (status) {
            case 0:
                info = "Provide username and password";
                break;
            case 1:
                response.status(201);
                info = "Created user!";
                break;
            case 2:
                info = "Username already in use.";
                break;
            case 3:
                info = "Couldn't create user.";
                break;
            default:
                info = "Unknown error";
                break;
        }

        System.out.println("LoginController - register : " + username + " " + info);

        JsonObject reply = new JsonObject();
        reply.put("Head", "Info");
        reply.put("Info", info);
        return reply.toJson();
    };

    public Route handleLogin = (Request request, Response response) -> {
        String username = getQueryUser(request);
        String password = getQueryPassword(request);

        if (!userController.authenticate(username, password)) {
            System.out.println("LoginController - unauthorised : " + username);
            halt(401, "Go away!");
        }

        User user = userController.getUser(username);

        request.session().attribute("currentUser", user.getUserid());

        String redirectPath = getQueryLoginRedirect(request);
        if (redirectPath != null) {
            removeSessionAttrLoginRedirect(request);
            response.redirect(redirectPath);
        }

        System.out.println("LoginController - authorized : " + username);
        response.status(200);
        JsonObject reply = new JsonObject();
        reply.put("Head", "Info");
        reply.put("Info", "Authentication successful");

        return reply.toJson();
    };

    public LoginController(UserController userController) {
        this.userController = userController;
    }

    /**
     * Checks if user is logged in and redirects to login if not.
     * The origin of the request (request.pathInfo()) is saved in the session so
     * the user can be redirected back after login.
     *
     * @param request  Original request.
     * @param response Original response.
     */
    public void ensureUserIsLoggedIn(Request request, Response response) {
        System.out.println("LoginController - unauthorised : "
                + getQueryUser(request) + " " + request.pathInfo());
        if (request.session().attribute("currentUser") == null) {
            response.status(401);
            response.redirect(Path.LOGIN);
        }
    }
}
