package app.friends;

import static app.Application.friendDAO;
import static app.util.RequestUtil.getSessionCurrentUser;

import app.login.LoginController;
import java.util.List;
import spark.Request;
import spark.Response;
import spark.Route;

public class FriendController {

    public static Route getFriends = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);

        int userid = getSessionCurrentUser(request);

        List<String> friends = friendDAO.retrieveFriends(userid);

        response.status(200);
        return friends.toString();
    };

    public static Route getSentRequests = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);

        int userid = getSessionCurrentUser(request);

        List<String> friends = friendDAO.retrieveSentRequests(userid);

        response.status(200);
        return friends.toString();
    };

    public static Route getReceivedRequests = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);

        int userid = getSessionCurrentUser(request);

        List<String> friends = friendDAO.retrieveReceivedRequests(userid);

        response.status(200);
        return friends.toString();
    };
}
