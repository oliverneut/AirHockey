package app.user;

import org.mindrot.jbcrypt.BCrypt;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class UserController {

    transient UserDAO userDAO;

    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Authenticate the client.
     *
     * @param username The name of the user
     * @param password The password of the user
     * @return If successfully authenticated.
     */
    public boolean authenticate(String username, String password) {

        if (username == null || password == null
                || username.isEmpty() || password.isEmpty()) {
            return false;
        }

        User user = userDAO.getByUsername(username);

        if (user == null) {
            System.out.println("UserController - authenticate : " + username + " dne");
            return false;
        }

        return BCrypt.checkpw(password, user.password);
    }

    /**
     * Registers a new user.
     *
     * @param username Username of new user.
     * @param password Password of new user.
     * @return Integer code for the following situations.
     *         0 if username or password empty.
     *         1 if user successfully added.
     *         2 if user already exists.
     *         3 couldn't create user for some reason.
     */
    public int createUser(String username, String password) {

        if (username == null || password == null
                || username.isEmpty() || password.isEmpty()) {
            return 0;
        }

        User user = userDAO.getByUsername(username);

        if (user != null) {
            return 2;
        }

        user = userDAO.registerNewUser(username,
                BCrypt.hashpw(password, BCrypt.gensalt()));

        if (user != null) {
            return 1;
        }

        return 3;
    }

    /**
     * Get user using username.
     *
     * @param username .
     * @return the user.
     */
    public User getUser(String username) {
        if (username == null || username.isEmpty()) {
            return null;
        }

        User user = userDAO.getByUsername(username);

        return user;
    }


}
