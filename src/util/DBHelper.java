package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Yan on 2017/2/17.
 */
public class DBHelper {
    private Connection conn = null;
    private final String URL = "jdbc:mysql://localhost:3306/tzb?useUnicode=true&amp;characterEncoding=utf-8";
    private final String User = "root";
    private final String Password = "Yanmysql2017!";

    public DBHelper() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, User, Password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }
}
