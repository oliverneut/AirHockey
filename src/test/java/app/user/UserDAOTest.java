package app.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import app.database.DatabaseConnection;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserDAOTest {

    static UserDAO userDAO;

    //hardcoded user in test db
    static int userid = 15;
    static String username = "john";
    static String password = "john";
    static User user = new User(15, username, password);

    @BeforeAll
    static void mainSetUp() {
        DatabaseConnection.test = true;
        userDAO = new UserDAO();
    }

    @Test
    void getByUsernamePass() {
        assertEquals(user, userDAO.getByUsername(username));
    }

    @Test
    void getByUsernameFail() {
        assertNull(userDAO.getByUsername("nonexitingusername"));
    }

    @Test
    void registerNewUserPass() {
        assertNotNull(userDAO.registerNewUser("newusername", "newpassword"));
    }

    @Test
    void registerNewUserFail() {
        assertNull(userDAO.registerNewUser(username, password));
    }

    @Test
    void getUsernamePass() {
        assertEquals(username, userDAO.getUsername(15));
    }

    @Test
    void getUsernameFail() {
        assertNull(userDAO.getUsername(0));
    }

    @Test
    void getSimilarUsernames() {
        ArrayList<String> usernames = new ArrayList<>();
        usernames.add("mike");

        assertEquals(usernames, userDAO.getSimilarUsernames("mike"));
    }

    @Test
    void getSimilarUsernames2() {
        assertEquals(new ArrayList<String>(), userDAO.getSimilarUsernames("einstein"));
    }
}