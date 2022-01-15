package com.revision.db;

import com.revision.entities.Section;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SectionDAO {

    public int createSection(String sectionName, int dictionaryId, int userId) {
        int result = -1;

        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.prepareStatement("INSERT INTO sections (name, dictionary_id, user_id) VALUES ('" + sectionName + "'," + dictionaryId + "," + userId + ");", Statement.RETURN_GENERATED_KEYS);
            int returnValue = stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();

            if(generatedKeys.next()) {
                result = generatedKeys.getInt(1);
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

        return result;
    }

    public List readSectionListByDictionaryId(int dictionaryId, int userId) {

        List<Section> sectionList = new ArrayList<>();

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM sections WHERE dictionary_id=" + dictionaryId + " AND user_id=" + userId + ";");
            while (rs.next()) {
                Section section = new Section(rs.getInt("section_id"), rs.getInt("dictionary_id"), rs.getInt("user_id"), rs.getString("name"));
                sectionList.add(section);
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

        return sectionList;
    }

    public List readSectionListByUserId(int userId) {

        List<Section> sectionList = new ArrayList<>();

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM sections WHERE user_id=" + userId + ";");
            while (rs.next()) {
                Section section = new Section(rs.getInt("section_id"), rs.getInt("dictionary_id"), rs.getInt("user_id"), rs.getString("name"));
                sectionList.add(section);
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

        return sectionList;
    }

    public boolean deleteSection(int sectionId, int userId) {
        boolean result = false;

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.createStatement();
            int returnValue = stmt.executeUpdate("DELETE FROM sections WHERE section_id=" + sectionId + " AND user_id=" + userId + ";");
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

    public boolean renameSection(String sectionNewName, int sectionId, int userId) {
        boolean result = false;

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.createStatement();
            int returnValue = stmt.executeUpdate("UPDATE sections SET name='" + sectionNewName + "' WHERE section_id=" + sectionId + " AND user_id=" + userId + ";");
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

        return  result;
    }

    public Section getSectionById (int userId, int sectionId) {

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        Section section = new Section(-1, -1, -1, "empty section");

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM sections WHERE user_id=" + userId + " AND section_id=" + sectionId +";");

            rs.next();
            section = new Section(rs.getInt("section_id"), rs.getInt("dictionary_id"), rs.getInt("user_id"), rs.getString("name"));


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

        return section;
    }
}
