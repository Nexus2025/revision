package com.revision.dao;

import com.ibatis.common.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;

public class DBUtil {
    private DBUtil() {
    }

    public static void main(String[] args) {
        refreshDemoUserData();
    }

    public static void refreshDemoUserData() {

        try (Connection connection = ConnectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            new ScriptRunner(connection, false, false)
                    .runScript(new BufferedReader(new InputStreamReader(
                            DBUtil.class.getResourceAsStream("/db/refreshDemoUserData.sql"), StandardCharsets.UTF_8)));
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
