package app.leaderboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.database.DatabaseConnection;
import app.login.LoginController;
import com.github.cliftonlabs.json_simple.JsonObject;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

class LeaderboardControllerTest {

    static LoginController loginController;
    static LeaderboardController leaderboardController;
    static LeaderboardDAO leaderboardDAO;
    transient Request request;
    transient Response response;
    transient Session session;

    @BeforeAll
    static void mainSetUp() {
        //use test database
        DatabaseConnection.test = true;
        leaderboardDAO = new LeaderboardDAO();
        loginController = mock(LoginController.class);
        leaderboardController = new LeaderboardController(leaderboardDAO, loginController);
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
    }

    @Test
    void getGeneralTopPlayersTestFail() throws Exception {
        Object reply = leaderboardController.getGeneralTopPlayers.handle(request, response);

        Map<String, Double> map = new HashMap<>();
        map.put("14", 1.0);
        map.put("15", 0.5);
        map.put("16", 0.2);

        JsonObject json = new JsonObject();
        json.put("Head", "Top Players");
        json.put("Top Players", map);

        verify(response).status(200);
        assertNotEquals(reply, json.toJson());
    }

    @Test
    void getGeneralTopPlayersTestPass() throws Exception {
        Object reply = leaderboardController.getGeneralTopPlayers.handle(request, response);

        Map<String, Double> map = new HashMap<>();
        map.put("14", 1.0);
        map.put("15", 0.5);
        map.put("16", 0.2);
        map.put("17", 0.6364);
        map.put("18", 0.0);
        map.put("19", 0.8571);

        JsonObject json = new JsonObject();
        json.put("Head", "Top Players");
        json.put("Top Players", map);

        verify(response).status(200);
        assertEquals(reply, json.toJson());
    }

    @Test
    void getFriendTopPlayersUserDoesNotExistTest() throws Exception {
        when(request.session()).thenReturn(session);
        when(session.attribute("currentUser")).thenReturn(2);
        Object reply = leaderboardController.getFriendTopPlayers.handle(request, response);

        JsonObject json = new JsonObject();
        json.put("Head", "Top Friends");
        json.put("Top Friends", new HashMap());

        verify(response).status(200);
        assertEquals(reply, json.toJson());
    }

    @Test
    void getFriendTopPlayersTestPass() throws Exception {
        when(request.session()).thenReturn(session);
        when(session.attribute("currentUser")).thenReturn(19);
        Object reply = leaderboardController.getFriendTopPlayers.handle(request, response);

        Map<String, Double> players = new HashMap<>();
        players.put("rey", 0.2);

        JsonObject json = new JsonObject();
        json.put("Head", "Top Friends");
        json.put("Top Friends", players);

        verify(response).status(200);

        assertEquals(json.toJson(), reply);
    }
}