package app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    static String url = "jdbc:mysql://projects-db.ewi.tudelft.nl/projects_SEMAirHockey"
        + "?useUnicode=true&characterEncoding=utf-8&serverTimezone=CET";
    static String dbUser = "pu_SEMAirHockey";
    static String dbPassword = "h5lJ00aQPX7p";

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
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
        }
        connection.setAutoCommit(false);

        return connection;
    }
}