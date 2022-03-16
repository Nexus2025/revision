package com.revision.dao;

import com.revision.entity.Section;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SectionDAO {

    private final Connection connection = Database.getConnection();

    private static final String CREATE = "INSERT INTO sections (name, dictionary_id, user_id) VALUES (?, ?, ?) RETURNING id";
    private static final String GET_ALL_BY_DICTIONARY_ID = "SELECT * FROM sections WHERE dictionary_id= ? AND user_id= ?";
    private static final String GET_ALL = "SELECT * FROM sections WHERE user_id= ?";
    private static final String DELETE = "DELETE FROM sections WHERE id= ? AND user_id= ? RETURNING name, dictionary_id";
    private static final String DELETE_ALL_BY_DICTIONARY_ID = "DELETE FROM words WHERE dictionary_id= ? AND user_id= ?";
    private static final String RENAME = "UPDATE sections SET name= ? WHERE id= ? AND user_id= ? RETURNING dictionary_id";
    private static final String GET = "SELECT * FROM sections WHERE user_id= ? AND id= ?";

    public Section create(String name, int dictionaryId, int userId) {
        Section section = null;
        try (PreparedStatement statement = connection.prepareStatement(CREATE)) {
            statement.setString(1, name);
            statement.setInt(2, dictionaryId);
            statement.setInt(3, userId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                section = new Section(rs.getInt("id"), dictionaryId, userId, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return section;
    }

    public List<Section> getAllByDictionaryId(int dictionaryId, int userId) {
        List<Section> sections = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_DICTIONARY_ID)) {
            statement.setInt(1, dictionaryId);
            statement.setInt(2, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Section section = new Section(rs.getInt("id"), rs.getInt("dictionary_id"), rs.getInt("user_id"), rs.getString("name"));
                    sections.add(section);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sections;
    }

    public List<Section> getAll(int userId) {
        List<Section> sections = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Section section = new Section(rs.getInt("id"), rs.getInt("dictionary_id"), rs.getInt("user_id"), rs.getString("name"));
                    sections.add(section);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sections;
    }

    public boolean deleteAllByDictionaryId(int dictionaryId, int userId) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ALL_BY_DICTIONARY_ID)) {
            statement.setInt(1, dictionaryId);
            statement.setInt(2, userId);
            result = statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Section delete(int id, int userId) {
        Section section = null;
        try (PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.setInt(2, userId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                section = new Section(id, rs.getInt("dictionary_id"), userId, rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return section;
    }

    public Section rename(String newName, int id, int userId) {
        Section section = null;
        try (PreparedStatement statement = connection.prepareStatement(RENAME)) {
            statement.setString(1, newName);
            statement.setInt(2, id);
            statement.setInt(3, userId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                section = new Section(id, rs.getInt("dictionary_id"), userId, newName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return section;
    }

    public Section get(int userId, int id) {
        Section section = null;
        try (PreparedStatement statement = connection.prepareStatement(GET)) {
            statement.setInt(1, userId);
            statement.setInt(2, id);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                section = new Section(rs.getInt("id"), rs.getInt("dictionary_id"), rs.getInt("user_id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return section;
    }
}
