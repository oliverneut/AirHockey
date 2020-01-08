package app.friends;

import static app.util.RequestUtil.getSessionCurrentUser;

import app.login.LoginController;
import app.user.UserDAO;
import app.util.Message;
import com.github.cliftonlabs.json_simple.JsonArray;
import java.util.List;
import spark.Request;
import spark.Response;
import spark.Route;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class FriendController {

    transient LoginController loginController;
    transient FriendDAO friendDAO;
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
    transient UserDAO userDAO;
    public Route searchUsers = (Request request, Response response) -> {
        loginController.ensureUserIsLoggedIn(request, response);

        List<String> temp = userDAO.getSimilarUsernames(request.params("search"));

        if (temp != null) {
            response.status(200);
            JsonArray usernames = new JsonArray(temp);

            Message msg = new Message("Search Result");
            msg.put("usernames", usernames.toJson());
            return msg.toString();
        }

        response.status(400);
        return "";
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
