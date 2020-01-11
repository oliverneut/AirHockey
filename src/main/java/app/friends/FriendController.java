package app.friends;

import static app.util.RequestUtil.getSessionCurrentUser;

import app.login.LoginController;
import app.user.UserDAO;
import com.github.cliftonlabs.json_simple.JsonObject;
import java.util.List;
import spark.Request;
import spark.Response;
import spark.Route;

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

    public Route deleteFriends = (Request request, Response response) -> {
        loginController.ensureUserIsLoggedIn(request, response);

        int userid = getSessionCurrentUser(request);

        String friend = request.params("username");

        boolean flag = friendDAO.deleteFriend(userid, friend);

        JsonObject reply = new JsonObject();
        if (flag) {
            response.status(200);
        } else {
            response.status(400);
        }
        return reply;
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

    public Route getSentRequests = (Request request, Response response) -> {
        loginController.ensureUserIsLoggedIn(request, response);

        int userid = getSessionCurrentUser(request);

        List<String> friends = friendDAO.retrieveSentRequests(userid);

        response.status(200);
        return friends.toString();
    };

    public Route getReceivedRequests = (Request request, Response response) -> {
        loginController.ensureUserIsLoggedIn(request, response);

        int userid = getSessionCurrentUser(request);

        List<String> friends = friendDAO.retrieveReceivedRequests(userid);

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

    public Route declineRequest = (Request request, Response response) -> {
        loginController.ensureUserIsLoggedIn(request, response);

        int userid = getSessionCurrentUser(request);

        String username = request.params("from");

        boolean flag = friendDAO.declineRequest(userid, username);

        JsonObject reply = new JsonObject();
        if (flag) {
            response.status(200);
        } else {
            response.status(400);
        }
        return reply;
    };

    public Route searchUsers = (Request request, Response response) -> {
        loginController.ensureUserIsLoggedIn(request, response);

        String searchTerm = request.params("search");

        System.out.println("FriendController - " + getSessionCurrentUser(request) + " : "
                + searchTerm);

        List<String> usernames = userDAO.getSimilarUsernames(searchTerm);

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
