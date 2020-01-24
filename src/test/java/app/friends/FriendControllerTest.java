package app.friends;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.database.DatabaseConnection;
import app.login.LoginController;
import app.user.UserDAO;
import com.github.cliftonlabs.json_simple.JsonObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

class FriendControllerTest {

    static UserDAO userDao;
    static FriendDAO friendDAO;
    static LoginController loginController;
    static FriendController friendController;
    transient Request request;
    transient Response response;
    transient Session session;
    transient String head;
    transient String players;

    final static String userString = "currentUser";
    final static String friendString = "Friends";
    final static String requestsSent = "Requests sent";
    final static String requestsReceived = "Requests received";

    @BeforeAll
    static void mainSetUp() {
        //use test database
        DatabaseConnection.test = true;
        friendDAO = new FriendDAO();
        userDao = new UserDAO();
        loginController = mock(LoginController.class);
        friendController = new FriendController(friendDAO, userDao, loginController);
    }

    @AfterAll
    static void mainTearDown() {
        try {
            DatabaseConnection.getConnection().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setUp() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        head = "Head";
    }

    @Test
    void getFriendsNoFriendsTest() throws Exception {
        when(request.session()).thenReturn(session);
        when(session.attribute(userString)).thenReturn(2);

        Object reply = friendController.getFriends.handle(request, response);

        List<String> friends = new ArrayList<>();

        JsonObject json = new JsonObject();
        json.put(head, friendString);
        json.put(friendString, friends);

        verify(response).status(200);

        assertEquals(json.toJson(), reply);
    }

    @Test
    void getFriendsTest() throws Exception {
        when(request.session()).thenReturn(session);
        when(session.attribute(userString)).thenReturn(14);

        Object reply = friendController.getFriends.handle(request, response);

        List<String> friends = new ArrayList<>();
        friends.add("john");
        friends.add("luca");
        friends.add("siri");

        JsonObject json = new JsonObject();
        json.put(head, friendString);
        json.put(friendString, friends);

        verify(response).status(200);

        assertEquals(json.toJson(), reply);
    }

    @Test
    void getSentRequestsTest() throws Exception {
        when(request.session()).thenReturn(session);
        when(session.attribute(userString)).thenReturn(15);

        Object reply = friendController.getSentRequests.handle(request, response);

        List<String> sentRequests = new ArrayList<>();
        sentRequests.add("dani");

        JsonObject json = new JsonObject();
        json.put(head, requestsSent);
        json.put(requestsSent, sentRequests);

        verify(response).status(200);

        assertEquals(json.toJson(), reply);
    }

    @Test
    void getSentRequestsNonExistingUserTest() throws Exception {
        when(request.session()).thenReturn(session);
        when(session.attribute(userString)).thenReturn(2);

        Object reply = friendController.getSentRequests.handle(request, response);

        List<String> sentRequests = new ArrayList<>();

        JsonObject json = new JsonObject();
        json.put(head, requestsSent);
        json.put(requestsSent, sentRequests);

        verify(response).status(200);

        assertEquals(json.toJson(), reply);
    }

    @Test
    void getSentRequestsEmptyTest() throws Exception {
        when(request.session()).thenReturn(session);
        when(session.attribute(userString)).thenReturn(16);

        Object reply = friendController.getSentRequests.handle(request, response);

        List<String> sentRequests = new ArrayList<>();

        JsonObject json = new JsonObject();
        json.put(head, requestsSent);
        json.put(requestsSent, sentRequests);

        verify(response).status(200);

        assertEquals(json.toJson(), reply);
    }

    @Test
    void getReceivedRequestsTest() throws Exception {
        when(request.session()).thenReturn(session);
        when(session.attribute(userString)).thenReturn(16);

        Object reply = friendController.getReceivedRequests.handle(request, response);

        List<String> receivedRequests = new ArrayList<>();
        receivedRequests.add("mike");

        JsonObject json = new JsonObject();
        json.put(head, requestsReceived);
        json.put(requestsReceived, receivedRequests);

        verify(response).status(200);

        assertEquals(json.toJson(), reply);
    }

    @Test
    void getReceivedRequestsNonExistingUserTest() throws Exception {
        when(request.session()).thenReturn(session);
        when(session.attribute(userString)).thenReturn(3);

        Object reply = friendController.getReceivedRequests.handle(request, response);

        List<String> receivedRequests = new ArrayList<>();

        JsonObject json = new JsonObject();
        json.put(head, requestsReceived);
        json.put(requestsReceived, receivedRequests);

        verify(response).status(200);

        assertEquals(json.toJson(), reply);
    }

    @Test
    void getReceivedRequestsEmptyTest() throws Exception {
        when(request.session()).thenReturn(session);
        when(session.attribute(userString)).thenReturn(18);

        Object reply = friendController.getReceivedRequests.handle(request, response);

        List<String> receivedRequests = new ArrayList<>();

        JsonObject json = new JsonObject();
        json.put(head, requestsReceived);
        json.put(requestsReceived, receivedRequests);

        verify(response).status(200);

        assertEquals(json.toJson(), reply);
    }
}