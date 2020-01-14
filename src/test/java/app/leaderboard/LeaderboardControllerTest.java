package app.leaderboard;

import app.database.DatabaseConnection;
import app.login.LoginController;
import app.user.User;
import app.user.UserController;
import app.user.UserDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

class LeaderboardControllerTest {

    static LeaderboardDAO leaderboardDAO;
    static LoginController loginController;
    static UserController userController;
    static UserDAO userDAO;
    transient String password;
    transient String username;
    transient User user;

    @BeforeAll
    static void mainSetUp() {
        //use test database
        DatabaseConnection.test = true;
        userDAO = new UserDAO();
        leaderboardDAO = new LeaderboardDAO();
        userController = new UserController(userDAO);
        loginController = new LoginController(userController);
    }

    @BeforeEach
    void setUp() {
        username = "john";
        password = "john";
        user = new User(15, username, password);
    }
}