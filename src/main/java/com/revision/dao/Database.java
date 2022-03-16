package com.revision.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

    private static Connection connection;

    static {
        try(InputStream stream = Database.class.getResourceAsStream("/db/database.properties")) {
            Properties properties = new Properties();
            properties.load(stream);
            String userName = properties.getProperty("USERNAME");
            String password = properties.getProperty("PASSWORD");
            String dbUrl = properties.getProperty("DB_URL");
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(dbUrl, userName, password);
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
