package app.friends;

import app.login.LoginController;
import app.user.UserDAO;
import com.github.cliftonlabs.json_simple.JsonObject;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

import static app.util.RequestUtil.getSessionCurrentUser;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class FriendController {

    transient LoginController loginController;
    transient FriendDAO friendDAO;
    transient UserDAO userDAO;

    public Route getFriends = (Request request, Response response) -> {
        loginController.ensureUserIsLoggedIn(request, response);

        int userid = getSessionCurrentUser(request);

        List<String> friends = friendDAO.retrieveFriends(userid);

        response.status(200);
        return friends.toString();
    };

    public Route getSentRequests = (Request request, Response response) -> {
        loginController.ensureUserIsLoggedIn(request, response);

        int userid = getSessionCurrentUser(request);

        List<String> friends = friendDAO.retrieveSentRequests(userid);

        response.status(200);
        return friends.toString();
    };

    public Route acceptRequest = (Request request, Response response) -> {
        loginController.ensureUserIsLoggedIn(request, response);

        int userid = getSessionCurrentUser(request);

        boolean flag = friendDAO.acceptRequest(userid, request.params("from"));

        if (flag) {
            response.status(200);
        } else {
            response.status(400);
        }

        return "";
    };

    public Route sendRequest = (Request request, Response response) -> {
        loginController.ensureUserIsLoggedIn(request, response);

        int userid = getSessionCurrentUser(request);

        boolean flag = friendDAO.sendRequest(userid, request.params("to"));

        if (flag) {
            response.status(200);
        } else {
            response.status(400);
        }
        return "";
    };

    public Route getReceivedRequests = (Request request, Response response) -> {
        loginController.ensureUserIsLoggedIn(request, response);

        int userid = getSessionCurrentUser(request);

        List<String> friends = friendDAO.retrieveReceivedRequests(userid);

        response.status(200);
        return friends.toString();
    };

    public Route searchUsers = (Request request, Response response) -> {
        loginController.ensureUserIsLoggedIn(request, response);

        List<String> usernames = userDAO.getSimilarUsernames(request.params("search"));

        JsonObject result = new JsonObject();

        if (usernames != null) {
            response.status(200);
            result.put("Head", "Search Results");
            result.put("Usernames", usernames);
        } else {
            response.status(400);
            result.put("Head", "Error");
            result.put("Error", "Unable to retrieve usernames.");
        }

        return result.toJson();
    };

    /**
     * Constructor.
     *
     * @param friendDAO       .
     * @param userDAO         .
     * @param loginController .
     */
    public FriendController(FriendDAO friendDAO, UserDAO userDAO, LoginController loginController) {
        this.friendDAO = friendDAO;
        this.userDAO = userDAO;
        this.loginController = loginController;
    }
}
