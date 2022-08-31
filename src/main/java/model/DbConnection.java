package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/contactsnew";
//    private static final String DB_USER = "root";
//    private static final String DB_PASSWORD = "password";
//    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";

    private static DbConnection instance;
    private Connection connection;
    public static DbConnection getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new DbConnection();
        }
        return instance;
    }
    public Connection getConnection() {
        return connection;
    }
    public DbConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Setup the connection with the DB
        connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/contactsnew?"
                        + "user=root&password=password");
    }

}
