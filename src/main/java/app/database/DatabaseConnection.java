package app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static boolean test = false;

    static String url = "jdbc:mysql://projects-db.ewi.tudelft.nl/projects_SEMAirHockey"
            + "?useUnicode=true&characterEncoding=utf-8&serverTimezone=CET";
    static String dbUser = "pu_SEMAirHockey";
    static String dbPassword = "h5lJ00aQPX7p";

    static String urlTest = "jdbc:mysql://projects-db.ewi.tudelft.nl/projects_testSEMAirHockey"
            + "?useUnicode=true&characterEncoding=utf-8&serverTimezone=CET";
    static String dbUserTest = "pu_W18CdR9xd8K7W";
    static String dbPasswordTest = "Zw4pB98PZoFQ";

    static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get connection to database.
     *
     * @return Connection object to database.
     * @throws SQLException .
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            if (test) {
                connection = DriverManager.getConnection(urlTest, dbUserTest, dbPasswordTest);
                connection.setAutoCommit(false);
            } else {
                connection = DriverManager.getConnection(url, dbUser, dbPassword);
                connection.setAutoCommit(true);
            }
        }

        return connection;
    }
}