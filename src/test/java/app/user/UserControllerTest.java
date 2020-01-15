package app.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.database.DatabaseConnection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserControllerTest {

    static UserDAO userDAO;
    static UserController userController;
    transient String password;
    transient String username;
    transient User user;

    @BeforeAll
    static void mainSetUp() {
        //use test database
        DatabaseConnection.test = true;
        userDAO = new UserDAO();
        userController = new UserController(userDAO);
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
        //hardcoded user in db
        username = "john";
        password = "john";
        user = new User(15, username, password);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void authenticateUserFail() {
        assertFalse(userController.authenticate("nonexistinguser", password));
    }

    @Test
    void authenticateUserPass() {
        assertTrue(userController.authenticate(username, password));
    }

    @Test
    void createExistingUser() {
        assertEquals(2, userController.createUser(username, password));
    }

    @Test
    void createUserFail() {
        assertEquals(2, userController.createUser(username, password));
    }

    @Test
    void createUserPass() {
        assertEquals(1, userController.createUser("newuser", password));
    }

    @Test
    void getUserPass() {
        assertEquals(user, userController.getUser(username));
    }

    @Test
    void getUserNull() {
        assertNull(userController.getUser(null));
    }

    @Test
    void getUserEmpty() {
        assertNull(userController.getUser(""));
    }
}