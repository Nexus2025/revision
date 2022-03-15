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
        InputStream stream = null;
        Properties properties = new Properties();

        try {
            stream = Database.class.getResourceAsStream("/db/database.properties");
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String userName = properties.getProperty("USERNAME");
        String password = properties.getProperty("PASSWORD");
        String dbUrl = properties.getProperty("DB_URL");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(dbUrl, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Database() throws ClassNotFoundException {
        InputStream stream = null;
        Properties properties = new Properties();

        try {
            stream = Database.class.getResourceAsStream("/database.properties");
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String userName = properties.getProperty("USERNAME");
        String password = properties.getProperty("PASSWORD");
        String dbUrl = properties.getProperty("DB_URL");

        Class.forName("org.postgresql.Driver");
        try {
            connection = DriverManager.getConnection(dbUrl, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
