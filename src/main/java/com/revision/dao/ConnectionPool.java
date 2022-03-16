package com.revision.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionPool {

    private static ComboPooledDataSource dataSource;

    static {
        try (InputStream stream = ConnectionPool.class.getResourceAsStream("/db/database.properties")) {
            Properties properties = new Properties();
            properties.load(stream);

            dataSource = new ComboPooledDataSource();
            dataSource.setDriverClass("org.postgresql.Driver");
            dataSource.setJdbcUrl(properties.getProperty("DB_URL"));
            dataSource.setUser(properties.getProperty("USERNAME"));
            dataSource.setPassword(properties.getProperty("PASSWORD"));

            dataSource.setMinPoolSize(1);
            dataSource.setMaxPoolSize(100);

        } catch (IOException | PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
