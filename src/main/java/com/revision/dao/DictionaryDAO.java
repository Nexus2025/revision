package com.revision.dao;

import com.revision.entity.Dictionary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DictionaryDAO {

    private static final String CREATE = "INSERT INTO dictionaries (name, user_id) VALUES (?, ?) RETURNING id";
    private static final String DELETE = "DELETE FROM dictionaries WHERE id= ? AND user_id= ? RETURNING name";
    private static final String RENAME = "UPDATE dictionaries SET name= ? WHERE id= ? AND user_id= ?";
    private static final String GET = "SELECT * FROM dictionaries WHERE user_id= ? AND id= ?";

    private static final String GET_ALL = "SELECT dictionaries.*, COUNT(words.dictionary_id) FROM dictionaries " +
            "LEFT OUTER JOIN words ON words.dictionary_id=dictionaries.id WHERE dictionaries.user_id=? " +
            "GROUP BY dictionaries.id";

    public Dictionary create(String name, int userId) {
        Dictionary dictionary = null;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE)) {
            statement.setString(1, name);
            statement.setInt(2, userId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                dictionary = new Dictionary(rs.getInt("id"), userId, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dictionary;
    }

    public List<Dictionary> getAll(int userId) {
        List<Dictionary> dictionaries = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Dictionary dictionary = new Dictionary(rs.getInt("id"), rs.getInt("user_id")
                            , rs.getString("name"), rs.getInt("count"));
                    dictionaries.add(dictionary);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dictionaries;
    }

    public Dictionary delete(int userId, int id) {
        Dictionary dictionary = null;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.setInt(2, userId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                dictionary = new Dictionary(id, userId, rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dictionary;
    }

    public Dictionary rename(String newName, int id, int userId) {
        Dictionary dictionary = null;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(RENAME)) {
            statement.setString(1, newName);
            statement.setInt(2, id);
            statement.setInt(3, userId);
            statement.executeUpdate();
            dictionary = new Dictionary(id, userId, newName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dictionary;
    }

    public Dictionary get(int userId, int id) {
        Dictionary dictionary = null;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET)) {
            statement.setInt(1, userId);
            statement.setInt(2, id);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                dictionary = new Dictionary(rs.getInt("id"), rs.getInt("user_id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dictionary;
    }
}

