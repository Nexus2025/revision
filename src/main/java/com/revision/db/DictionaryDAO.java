package com.revision.db;

import com.revision.entities.Dictionary;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DictionaryDAO {

    public boolean create (String name, int userId) {
        boolean result = false;

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.createStatement();
            int returnValue = stmt.executeUpdate("INSERT INTO dictionaries (name, user_id) VALUES ('" + name + "'," + userId + ");");
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

    public List readDictionaryList (int userId) {

        List<Dictionary> dictionaryList = new ArrayList<>();

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM dictionaries WHERE user_id=" + userId + ";");
            while (rs.next()) {
                Dictionary dictionary = new Dictionary(rs.getInt("dictionary_id"), rs.getInt("user_id"), rs.getString("name"));
                dictionaryList.add(dictionary);
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

        return dictionaryList;
    }

    public boolean delete(int userId, int dictionaryId) {
        boolean result = false;

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.createStatement();
            int returnValue = stmt.executeUpdate("DELETE FROM dictionaries WHERE dictionary_id=" + dictionaryId + " AND user_id=" + userId + ";");
            result = true;


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

    public boolean rename (String newName, int dictionaryId, int userId) {
        boolean result = false;

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.createStatement();
            int returnValue = stmt.executeUpdate("UPDATE dictionaries SET name='" + newName + "' WHERE dictionary_id=" + dictionaryId + " AND user_id=" + userId + ";");
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

    public Dictionary getDictionaryById (int userId, int dictionaryId) {

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        Dictionary dictionary = new Dictionary(-1, -1,"empty dictionary");

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM dictionaries WHERE user_id=" + userId + " AND dictionary_id=" + dictionaryId +";");

            rs.next();
            dictionary = new Dictionary(rs.getInt("dictionary_id"), rs.getInt("user_id"), rs.getString("name"));


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

        return dictionary;
    }
}

