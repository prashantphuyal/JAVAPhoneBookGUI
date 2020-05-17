package Helpers;

import java.sql.*;

public class DBUtils {
    public static Connection getDbConnection() throws SQLException {
        String connectionString = "jdbc:mysql://" + Config.dbHost + ":" + Config.dbPort + "/" + Config.dbName;

        return DriverManager.getConnection(connectionString, Config.dbUser, Config.dbPass);
    }
}
