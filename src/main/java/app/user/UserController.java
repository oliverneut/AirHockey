package app.user;

import static app.Application.userDAO;

import org.mindrot.jbcrypt.BCrypt;

public class UserController {

    /**
     * Authenticate the client.
     *
     * @param password .
     * @return If successfully authenticated.
     */
    public static boolean authenticate(String username, String password) {

        if (username == null || password == null
            || username.isEmpty() || password.isEmpty()) {
            return false;
        }

        User user = userDAO.getByUsername(username);

        if (user == null) {
            return false;
        }

        return BCrypt.checkpw(password, user.password);
    }

    /**
     * Registers a new user.
     *
     * @param username Username of new user.
     * @param password Password of new user.
     * @return 1 if user successfully added. 2 if user already exists.
     *     3 couldn't create user for some reason.
     */
    public static int createUser(String username, String password) {

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


}
