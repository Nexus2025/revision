package com.revision.db;

import com.revision.entities.User;
import com.revision.model.Encryptor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {

    public User read (String login) {

        User user = null;

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM users WHERE login='" + login + "'");
            while (rs.next()) {
                user = new User(rs.getInt("user_id"), rs.getInt("role"), rs.getString("login"), rs.getString("password"), rs.getString("secret_key"));
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stmt!=null) stmt.close();
                if (rs!=null)rs.close();
                if (conn!=null)conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return user;
    }

    public boolean create (String login, String password, int role) {
        boolean result = false;

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        Encryptor pe = new Encryptor();
        String secretKey = pe.encrypt(String.valueOf((Math.random()*(600+1)) - 200));
        password = pe.encrypt(password);

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.createStatement();
            int returnValue = stmt.executeUpdate("INSERT INTO users (login, password, role, secret_key) " +
                    "VALUES ('" + login + "', '" + password + "'," + role + ", '"+ secretKey +"')");
            result = true;


        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stmt!=null) stmt.close();
                if (rs!=null)rs.close();
                if (conn!=null)conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return result;
    }

    public int getWordsCountByUserId (int userId) {

        int count = 0;

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT count(*) FROM words WHERE user_id=" + userId + ";");
            rs.next();
            count = rs.getInt(1);

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return count;
    }

    public int getDictionariesCountByUserId (int userId) {

        int count = 0;

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT count(*) FROM dictionaries WHERE user_id=" + userId + ";");
            rs.next();
            count = rs.getInt(1);

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return count;
    }

    public boolean checkUserExists (String userLogin) {

        boolean result = true;

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.createStatement();
//            rs = stmt.executeQuery("SELECT count(login) FROM users WHERE login='" + userLogin + "';");
//            rs.next();
//            result = rs.getInt("count(login)");

            rs = stmt.executeQuery("SELECT EXISTS (SELECT 1 FROM users WHERE login='" + userLogin + "');");
            rs.next();
            result = rs.getBoolean(1);

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return result;
    }
}
