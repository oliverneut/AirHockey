package app.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

class UserControllerTest {

    static UserDAO userDAO;
    static UserController userController;
    transient String password;
    transient String username;
    transient User user;

    @BeforeAll
    static void mainSetUp() {
        userDAO = mock(UserDAO.class);
        userController = new UserController(userDAO);
    }

    @BeforeEach
    void setUp() {
        password = "password";
        username = "username";
        user = new User(42, username, BCrypt.hashpw(password, BCrypt.gensalt()));

        when(userDAO.getByUsername(username)).thenReturn(user);
        when(userDAO.getByUsername("nonexistinguser")).thenReturn(null);

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
        when(userDAO.registerNewUser(anyString(), anyString())).thenReturn(null);
        assertEquals(3, userController.createUser("nonexistinguser", password));
    }

    @Test
    void createUserPass() {
        when(userDAO.registerNewUser(anyString(), anyString())).thenReturn(user);
        assertEquals(1, userController.createUser("newuser", password));
    }

    @Test
    void getUserPass() {
        assertEquals(user, userController.getUser(username));
    }

    @Test
    void getUserNull() {
        assertEquals(null, userController.getUser(null));
    }

    @Test
    void getUserEmpty() {
        assertEquals(null, userController.getUser(new String()));
    }
}