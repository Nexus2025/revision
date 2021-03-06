package com.revision.dao.impl;

import com.revision.dao.util.ConnectionFactory;
import com.revision.dao.SectionDao;
import com.revision.entity.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SectionDaoImpl implements SectionDao {

    private static final Logger log = LoggerFactory.getLogger(SectionDaoImpl.class);

    private static final String CREATE = "INSERT INTO sections (name, dictionary_id, user_id) VALUES (?, ?, ?)";
    private static final String DELETE = "DELETE FROM sections WHERE id= ? AND user_id= ?";
    private static final String DELETE_ALL_BY_DICTIONARY_ID = "DELETE FROM sections WHERE dictionary_id= ? AND user_id= ?";
    private static final String RENAME = "UPDATE sections SET name= ? WHERE id= ? AND user_id= ?";
    private static final String GET = "SELECT * FROM sections WHERE user_id= ? AND id= ?";

    private static final String GET_ALL_BY_DICTIONARY_ID = "SELECT sections.*, COUNT(words.section_id) AS count FROM sections " +
            "LEFT OUTER JOIN words ON words.section_id=sections.id WHERE sections.dictionary_id=? " +
            "AND sections.user_id=? GROUP BY sections.id";

    private static final String GET_ALL =
            "SELECT sections.*, COUNT(words.section_id) AS count FROM sections " +
                    "LEFT OUTER JOIN words ON words.section_id=sections.id WHERE sections.user_id=? GROUP BY sections.id";

    public Section create(String name, int dictionaryId, int userId) {
        Section section = null;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.setInt(2, dictionaryId);
            statement.setInt(3, userId);
            statement.execute();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                rs.next();
                section = new Section(rs.getInt("id"), dictionaryId, userId, name);
            }
        } catch (SQLException e) {
            log.error(e.getSQLState());
        }
        return section;
    }

    public List<Section> getAllByDictionaryId(int dictionaryId, int userId) {
        List<Section> sections = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_DICTIONARY_ID)) {
            statement.setInt(1, dictionaryId);
            statement.setInt(2, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Section section = new Section(rs.getInt("id"), rs.getInt("dictionary_id"), rs.getInt("user_id")
                            , rs.getString("name"), rs.getInt("count"));
                    sections.add(section);
                }
            }
        } catch (SQLException e) {
            log.error(e.getSQLState());
        }
        return sections;
    }

    public List<Section> getAll(int userId) {
        List<Section> sections = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Section section = new Section(rs.getInt("id"), rs.getInt("dictionary_id"), rs.getInt("user_id")
                            , rs.getString("name"), rs.getInt("count"));
                    sections.add(section);
                }
            }
        } catch (SQLException e) {
            log.error(e.getSQLState());
        }

        return sections;
    }

    public boolean deleteAllByDictionaryId(int dictionaryId, int userId) {
        boolean result = false;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ALL_BY_DICTIONARY_ID)) {
            statement.setInt(1, dictionaryId);
            statement.setInt(2, userId);
            result = statement.executeUpdate() != 0;
        } catch (SQLException e) {
            log.error(e.getSQLState());
        }
        return result;
    }

    public Section delete(int id, int userId) {
        Section section = null;
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
                    section = new Section(id, rs.getInt("dictionary_id"), userId, rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            log.error(e.getSQLState());
        }
        return section;
    }

    public Section rename(String newName, int id, int userId) {
        Section section = null;
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
                section = new Section(id, rs.getInt("dictionary_id"), userId, newName);
            }
        } catch (SQLException e) {
            log.error(e.getSQLState());
        }
        return section;
    }

    public Section get(int userId, int id) {
        Section section = null;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET)) {
            statement.setInt(1, userId);
            statement.setInt(2, id);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                section = new Section(rs.getInt("id"), rs.getInt("dictionary_id"), rs.getInt("user_id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            log.error(e.getSQLState());
        }
        return section;
    }
}
