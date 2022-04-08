package com.revision.dao.impl;

import com.revision.dao.util.ConnectionFactory;
import com.revision.dao.DictionaryDao;
import com.revision.entity.Dictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DictionaryDaoImpl implements DictionaryDao {

    private static final Logger log = LoggerFactory.getLogger(DictionaryDaoImpl.class);

    private static final String CREATE = "INSERT INTO dictionaries (name, user_id) VALUES (?, ?)";
    private static final String DELETE = "DELETE FROM dictionaries WHERE id= ? AND user_id= ?";
    private static final String RENAME = "UPDATE dictionaries SET name= ? WHERE id= ? AND user_id= ?";
    private static final String GET = "SELECT * FROM dictionaries WHERE user_id= ? AND id= ?";

    private static final String GET_ALL = "SELECT dictionaries.*, COUNT(words.dictionary_id) AS count FROM dictionaries " +
            "LEFT OUTER JOIN words ON words.dictionary_id=dictionaries.id WHERE dictionaries.user_id=? " +
            "GROUP BY dictionaries.id";

    public Dictionary create(String name, int userId) {
        Dictionary dictionary = null;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.setInt(2, userId);
            statement.execute();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                rs.next();
                dictionary = new Dictionary(rs.getInt("id"), userId, name);
            }
        } catch (SQLException e) {
            log.error(e.getSQLState());
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
            log.error(e.getSQLState());
        }
        return dictionaries;
    }

    public Dictionary delete(int userId, int id) {
        Dictionary dictionary = null;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statementDelete = connection.prepareStatement(DELETE);
             PreparedStatement statementGet = connection.prepareStatement(GET)) {

            statementDelete.setInt(1, id);
            statementDelete.setInt(2, userId);

            statementGet.setInt(1, userId);
            statementGet.setInt(2, id);

            try (ResultSet rs = statementGet.executeQuery()) {
                if (statementDelete.executeUpdate() != 0) {
                    rs.next();
                    dictionary = new Dictionary(id, userId, rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            log.error(e.getSQLState());
        }
        return dictionary;
    }

    public Dictionary rename(String newName, int id, int userId) {
        Dictionary dictionary = null;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statementRename = connection.prepareStatement(RENAME);
             PreparedStatement statementGet = connection.prepareStatement(GET)) {
            statementRename.setString(1, newName);
            statementRename.setInt(2, id);
            statementRename.setInt(3, userId);

            statementGet.setInt(1, userId);
            statementGet.setInt(2, id);

            statementRename.executeUpdate();
            try (ResultSet rs = statementGet.executeQuery()) {
                rs.next();
                dictionary = new Dictionary(rs.getInt("id"), rs.getInt("user_id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            log.error(e.getSQLState());
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
            log.error(e.getSQLState());
        }
        return dictionary;
    }
}

