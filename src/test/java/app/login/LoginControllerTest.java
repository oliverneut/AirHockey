package app.login;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import app.database.DatabaseConnection;
import app.user.UserController;
import app.user.UserDAO;
import app.util.Path;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import java.sql.SQLException;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
class LoginControllerTest {

    static String USER = "user";
    static String PASSWORD = "password";

    static UserDAO userDAO;

    static UserController userController;
    static LoginController loginController;

    static String username = "mike";
    static String password = "mike";
    static int userid = 14;

    transient Request request;
    transient Session session;
    transient Response response;

    @BeforeAll
    static void mainSetUp() {
        DatabaseConnection.test = true;
        userDAO = new UserDAO();
        userController = new UserController(userDAO);
        loginController = new LoginController(userController);
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
    public void setUp() {
        request = mock(Request.class);
        session = mock(Session.class);
        response = mock(Response.class);

        when(request.session()).thenReturn(session);
        when(session.attribute("currentUser")).thenReturn(userid);

        doNothing().when(session).attribute(any(), any());
    }

    @Test
    public void handleLoginPass() {
        when(request.queryParams(USER)).thenReturn(username);
        when(request.queryParams(PASSWORD)).thenReturn(password);
        try {
            loginController.handleLogin.handle(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }
        verify(response).status(200);
    }

    @Test
    public void handleLoginWrongPass() {
        when(request.queryParams(USER)).thenReturn(username);
        when(request.queryParams(PASSWORD)).thenReturn("wrongPassword");
        try {
            loginController.handleLogin.handle(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }
        verify(response).status(401);
    }

    @Test
    public void handleLoginNonUser() {
        when(request.queryParams(USER)).thenReturn("nonexistinguser");
        when(request.queryParams(PASSWORD)).thenReturn(PASSWORD);
        try {
            loginController.handleLogin.handle(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }
        verify(response).status(401);
    }

    @Test
    public void handleLoginRedirect() {
        when(request.queryParams(USER)).thenReturn(username);
        when(request.queryParams(PASSWORD)).thenReturn(password);
        when(session.attribute("loginRedirect")).thenReturn("redirect");

        try {
            loginController.handleLogin.handle(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }
        verify(session).removeAttribute("loginRedirect");
        verify(response).redirect("redirect");
    }

    @Test
    public void ensureUserLoggedInTrue() {
        loginController.ensureUserIsLoggedIn(request, response);
        verifyNoInteractions(response);
    }

    @Test
    public void ensureUserLoggedInFalse() {
        when(session.attribute("currentUser")).thenReturn(null);

        loginController.ensureUserIsLoggedIn(request, response);
        verify(response).redirect(Path.LOGIN);
    }

    @Test
    public void handleLogoutPort() {
        try {
            loginController.handleLogoutPost.handle(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        verify(session).removeAttribute("currentUser");
    }

    @Test
    public void handleCreateUserAlreadyExists() {

        when(request.queryParams(USER)).thenReturn(username);
        when(request.queryParams(PASSWORD)).thenReturn(password);

        Object reply;
        try {
            reply = loginController.handleCreateUser.handle(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        JsonObject jsonObject = Jsoner.deserialize((String) reply, new JsonObject());

        assertEquals("Username already in use.", jsonObject.get("Info"));
    }

    @Test
    public void handleCreateUserPass() {

        when(request.queryParams(USER)).thenReturn("newuser");
        when(request.queryParams(PASSWORD)).thenReturn(PASSWORD);

        try {
            loginController.handleCreateUser.handle(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }
        verify(response).status(HttpStatus.CREATED_201);
    }

    @Test
    public void handleCreateUserNoInfo() {
        when(request.queryParams(USER)).thenReturn("");
        when(request.queryParams(PASSWORD)).thenReturn("");

        Object reply;
        try {
            reply = loginController.handleCreateUser.handle(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }
        JsonObject jsonObject = Jsoner.deserialize((String) reply, new JsonObject());

        assertEquals("Provide username and password", jsonObject.get("Info"));
    }

}