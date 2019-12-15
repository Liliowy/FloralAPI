package me.lilac.floralapi.root.storage;

import me.lilac.floralapi.root.FloralPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class to manage an SQL Database.
 * NOT YET IMPLEMENTED.
 */
public class SQLDatabase {

    private FloralPlugin plugin;
    private Connection connection;
    private String host, database, username, password;
    private int port;

    public SQLDatabase() {
        plugin = FloralPlugin.getInstance();
        host = plugin.getConfigFile().getString("sql.host");
        port = plugin.getConfigFile().getInt("sql.port");
        database = plugin.getConfigFile().getString("sql.database");
        username = plugin.getConfigFile().getString("sql.username");
        password = plugin.getConfigFile().getString("sql.password");

        openConnection();
    }

    public void openConnection() {
        try {
            if (connection != null && !connection.isClosed()) return;
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        synchronized (this) {
            try {
                if (connection != null && !connection.isClosed()) return;
            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }

            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }

            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" +
                        this.database, this.username, this.password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
