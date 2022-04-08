package com.revision.dao.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static final Logger log = LoggerFactory.getLogger(ConnectionFactory.class);

    private static ComboPooledDataSource dataSource;

    static {
        try (InputStream stream = ConnectionFactory.class.getResourceAsStream("/db/database.properties")) {
            Properties properties = new Properties();
            properties.load(stream);

            dataSource = new ComboPooledDataSource();
            dataSource.setDriverClass(properties.getProperty("JDBC_DRIVER"));
            dataSource.setJdbcUrl(properties.getProperty("DB_URL"));
            dataSource.setUser(properties.getProperty("USERNAME"));
            dataSource.setPassword(properties.getProperty("PASSWORD"));

            dataSource.setMinPoolSize(1);
            dataSource.setMaxPoolSize(100);

        } catch (IOException | PropertyVetoException e) {
            log.error(e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
