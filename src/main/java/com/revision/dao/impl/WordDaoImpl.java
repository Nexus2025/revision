package com.revision.dao.impl;

import com.revision.dao.util.ConnectionFactory;
import com.revision.dao.WordDao;
import com.revision.entity.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WordDaoImpl implements WordDao {

    private static final Logger log = LoggerFactory.getLogger(WordDaoImpl.class);

    private static final String CREATE = "INSERT INTO words (word, translation, section_id, user_id, dictionary_id) VALUES (?, ?, ?, ?, ?)";
    private static final String GET = "SELECT * FROM words WHERE id= ? AND user_id= ?";
    private static final String RENAME = "UPDATE words SET word= ?, translation= ? WHERE id= ? AND user_id= ?";
    private static final String DELETE = "DELETE FROM words WHERE id= ? AND user_id= ?";
    private static final String DELETE_ALL_BY_SECTION_ID = "DELETE FROM words WHERE section_id= ? AND user_id= ?";
    private static final String GET_ALL_BY_SECTION_ID = "SELECT * FROM words WHERE section_id= ? AND user_id= ?";
    private static final String GET_ALL_BY_DICTIONARY_ID = "SELECT * FROM words WHERE dictionary_id= ? AND user_id= ?";
    private static final String SAVE_WORDS_LIST = "INSERT INTO words (word, translation, section_id, user_id, dictionary_id) VALUES(?, ?, ?, ?, ?)";

    public Word create(String word, String translation, int sectionId, int userId, int dictionaryId) {
        Word wd = null;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, word);
            statement.setString(2, translation);
            statement.setInt(3, sectionId);
            statement.setInt(4, userId);
            statement.setInt(5, dictionaryId);
            statement.execute();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                rs.next();
                wd = new Word(rs.getInt("id"), sectionId, dictionaryId, userId, word, translation);
            }
        } catch (SQLException e) {
            log.error(e.getSQLState());
        }
        return wd;
    }

    public Word rename(String word, String translation, int id, int userId) {
        Word wd = null;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statementRename = connection.prepareStatement(RENAME);
             PreparedStatement statementGet = connection.prepareStatement(GET)) {
            statementRename.setString(1, word);
            statementRename.setString(2, translation);
            statementRename.setInt(3, id);
            statementRename.setInt(4, userId);

            statementGet.setInt(1, id);
            statementGet.setInt(2, userId);

            statementRename.executeUpdate();
            try (ResultSet rs = statementGet.executeQuery()) {
                rs.next();
                wd = new Word(id, rs.getInt("section_id"), rs.getInt("dictionary_id"), userId, word, translation);
            }
        } catch (SQLException e) {
            log.error(e.getSQLState());
        }
        return wd;
    }

    public Word delete(int id, int userId) {
        Word wd = null;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statementDelete = connection.prepareStatement(DELETE);
             PreparedStatement statementGet = connection.prepareStatement(GET)) {

            statementDelete.setInt(1, id);
            statementDelete.setInt(2, userId);

            statementGet.setInt(1, id);
            statementGet.setInt(2, userId);

            try (ResultSet rs = statementGet.executeQuery()) {
                if (statementDelete.executeUpdate() != 0) {
                    rs.next();
                    String word = rs.getString("word");
                    String translation = rs.getString("translation");
                    int dictionaryId = rs.getInt("dictionary_id");
                    int sectionId = rs.getInt("section_id");
                    wd = new Word(id, sectionId, dictionaryId, userId, word, translation);
                }
            }
        } catch (SQLException e) {
            log.error(e.getSQLState());
        }
        return wd;
    }

    public List<Word> getAllBySectionId(int sectionId, int userId) {
        List<Word> words = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_SECTION_ID)) {
            statement.setInt(1, sectionId);
            statement.setInt(2, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Word wd = new Word(rs.getInt("id"), rs.getInt("section_id"), rs.getInt("dictionary_id"), rs.getInt("user_id"), rs.getString("word"), rs.getString("translation"));
                    words.add(wd);
                }
            }
        } catch (SQLException e) {
            log.error(e.getSQLState());
        }
        return words;
    }

    public List<Word> getAllByDictionaryId(int dictionaryId, int userId) {
        List<Word> words = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_DICTIONARY_ID)) {
            statement.setInt(1, dictionaryId);
            statement.setInt(2, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Word wd = new Word(rs.getInt("id"), rs.getInt("section_id"), rs.getInt("dictionary_id"), rs.getInt("user_id"), rs.getString("word"), rs.getString("translation"));
                    words.add(wd);
                }
            }
        } catch (SQLException e) {
            log.error(e.getSQLState());
        }
        return words;
    }

    public boolean deleteAllBySectionId(int sectionId, int userId) {
        boolean result = false;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ALL_BY_SECTION_ID)) {
            statement.setInt(1, sectionId);
            statement.setInt(2, userId);
            result = statement.executeUpdate() != 0;
        } catch (SQLException e) {
            log.error(e.getSQLState());
        }
        return result;
    }

    public void saveWordsList(List<Word> words, int userId, int dictionaryId) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_WORDS_LIST)) {
            for (Word wd : words) {
                statement.setString(1, wd.getWord());
                statement.setString(2, wd.getTranslation());
                statement.setInt(3, wd.getSectionId());
                statement.setInt(4, userId);
                statement.setInt(5, dictionaryId);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            log.error(e.getSQLState());
        }
    }
}
