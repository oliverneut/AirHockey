package app.friends;

import static app.util.RequestUtil.getSessionCurrentUser;

import app.login.LoginController;
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

    public Route getReceivedRequests = (Request request, Response response) -> {
        loginController.ensureUserIsLoggedIn(request, response);

        int userid = getSessionCurrentUser(request);

        List<String> friends = friendDAO.retrieveReceivedRequests(userid);

        response.status(200);
        return friends.toString();
    };

    public FriendController(FriendDAO friendDAO, LoginController loginController) {
        this.friendDAO = friendDAO;
        this.loginController = loginController;
    }
}
