package app.userstats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import app.database.DatabaseConnection;
import app.user.UserDAO;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import java.math.BigDecimal;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
class UserStatsControllerTest {

    static UserStatsDAO userStatsDAO;
    static UserDAO userDAO;
    static UserStatsController userStatsController;

    static int userid = 15;

    transient Request request;
    transient Session session;
    transient Response response;

    @BeforeAll
    static void mainSetUp() {
        DatabaseConnection.test = true;

        userStatsDAO = new UserStatsDAO();
        userDAO = new UserDAO();
        userStatsController = new UserStatsController(userStatsDAO, userDAO);
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

        when(request.session()).thenReturn(session);
        when(session.attribute("currentUser")).thenReturn(userid);
    }

    @Test
    void getUserStatsPass() {
        String reply;
        try {
            reply = (String) userStatsController.getUserStats.handle(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        JsonObject jsonReply = Jsoner.deserialize(reply, new JsonObject());

        assertEquals(20, ((BigDecimal) jsonReply.get("Matches played")).intValue());
        assertEquals(10, ((BigDecimal) jsonReply.get("Matches won")).intValue());
        assertEquals(80, ((BigDecimal) jsonReply.get("Goals scored")).intValue());
        assertEquals(110, ((BigDecimal) jsonReply.get("Goals against")).intValue());
    }


}