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

    public static String HEAD = "Head";

    transient LoginController loginController;
    transient FriendDAO friendDAO;
    transient UserDAO userDAO;

    public Route getFriends = (Request request, Response response) -> {
        loginController.ensureUserIsLoggedIn(request, response);

        int userid = getSessionCurrentUser(request);

        System.out.println("FriendController - getFriends : " + userid);

        List<String> friends = friendDAO.retrieveFriends(userid);

        JsonObject reply = new JsonObject();
        reply.put(HEAD, "Friends");
        reply.put("Friends", friends);

        response.status(200);
        return reply.toJson();
    };
    public Route deleteFriends = (Request request, Response response) -> {
        loginController.ensureUserIsLoggedIn(request, response);
        int userid = getSessionCurrentUser(request);
        String friend = request.queryParams("username");

        System.out.println("FriendController - deleteFriends : " + userid + " " + friend);

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
        String to = request.queryParams("to");

        System.out.println("FriendController - sendRequest : " + userid + " -> " + to);

        boolean flag = friendDAO.sendRequest(userid, to);

        JsonObject reply = new JsonObject();
        if (flag) {
            response.status(200);
        } else {
            response.status(400);
        }
        return reply;
    };
    public Route getSentRequests = (Request request, Response response) -> {
        loginController.ensureUserIsLoggedIn(request, response);
        int userid = getSessionCurrentUser(request);

        System.out.println("FriendController - getSentRequests : " + userid);

        List<String> sentRequests = friendDAO.retrieveSentRequests(userid);

        JsonObject reply = new JsonObject();
        reply.put(HEAD, "Requests sent");
        reply.put("Requests sent", sentRequests);

        response.status(200);
        return reply.toJson();
    };
    public Route getReceivedRequests = (Request request, Response response) -> {
        loginController.ensureUserIsLoggedIn(request, response);
        int userid = getSessionCurrentUser(request);

        System.out.println("FriendController - getReceivedRequests : " + userid);

        List<String> receivedRequests = friendDAO.retrieveReceivedRequests(userid);

        JsonObject reply = new JsonObject();
        reply.put(HEAD, "Requests received");
        reply.put("Requests received", receivedRequests);

        response.status(200);
        return reply.toJson();
    };
    public Route acceptRequest = (Request request, Response response) -> {
        loginController.ensureUserIsLoggedIn(request, response);
        int userid = getSessionCurrentUser(request);
        String from = request.queryParams("from");

        System.out.println("FriendController - acceptRequest : " + userid + " <- " + from);

        boolean flag = friendDAO.acceptRequest(userid, from);

        JsonObject reply = new JsonObject();
        if (flag) {
            response.status(200);
        } else {
            response.status(400);
        }
        return reply.toJson();
    };
    public Route declineRequest = (Request request, Response response) -> {
        loginController.ensureUserIsLoggedIn(request, response);
        int userid = getSessionCurrentUser(request);
        String from = request.queryParams("from");

        System.out.println("FriendController - declineRequest : " + userid + " <- " + from);

        boolean flag = friendDAO.declineRequest(userid, from);

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
        int userid = getSessionCurrentUser(request);
        String searchTerm = request.queryParams("search");

        System.out.println("FriendController - searchUsers : " + userid + " " + searchTerm);

        List<String> usernames = userDAO.getSimilarUsernames(searchTerm);

        JsonObject result = new JsonObject();

        if (usernames != null) {
            response.status(200);
            result.put(HEAD, "Usernames");
            result.put("Usernames", usernames);
        } else {
            response.status(400);
            result.put(HEAD, "Error");
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
