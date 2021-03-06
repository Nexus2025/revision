package com.revision.dao.util;

import com.ibatis.common.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;

public class DBUtil {
    private static final Logger log = LoggerFactory.getLogger(DBUtil.class);

    private DBUtil() {
    }

    public static void refreshDemoUserData() {

        try (Connection connection = ConnectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            new ScriptRunner(connection, false, false)
                    .runScript(new BufferedReader(new InputStreamReader(
                            DBUtil.class.getResourceAsStream("/db/refreshDemoUserData.sql"), StandardCharsets.UTF_8)));
            connection.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
