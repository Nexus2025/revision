package com.revision.dao;

import com.revision.entity.Word;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WordDAO {

    private final Connection connection = Database.getConnection();

    private static final String CREATE = "INSERT INTO words (word, translation, section_id, user_id, dictionary_id) VALUES (?, ?, ?, ?, ?) RETURNING id";
    private static final String RENAME = "UPDATE words SET word= ?, translation= ? WHERE id= ? AND user_id= ? RETURNING dictionary_id, section_id";
    private static final String DELETE = "DELETE FROM words WHERE id= ? AND user_id= ? RETURNING dictionary_id, section_id, word, translation";
    private static final String DELETE_ALL_BY_SECTION_ID = "DELETE FROM words WHERE section_id= ? AND user_id= ?";
    private static final String GET_ALL_BY_SECTION_ID = "SELECT * FROM words WHERE section_id= ? AND user_id= ?";
    private static final String GET_ALL_BY_DICTIONARY_ID = "SELECT * FROM words WHERE dictionary_id= ? AND user_id= ?";
    private static final String GET_WORDS_COUNT_BY_DICTIONARY_ID = "SELECT count(*) FROM words WHERE dictionary_id= ?";
    private static final String SAVE_WORDS_LIST = "INSERT INTO words (word, translation, section_id, user_id, dictionary_id) VALUES(?, ?, ?, ?, ?)";

    public Word create(String word, String translation, int sectionId, int userId, int dictionaryId) {
        Word wd = null;
        try (PreparedStatement statement = connection.prepareStatement(CREATE)) {
            statement.setString(1, word);
            statement.setString(2, translation);
            statement.setInt(3, sectionId);
            statement.setInt(4, userId);
            statement.setInt(5, dictionaryId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                wd = new Word(rs.getInt("id"), sectionId, dictionaryId, userId, word, translation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wd;
    }

    public Word rename(String word, String translation, int id, int userId) {
        Word wd = null;
        try (PreparedStatement statement = connection.prepareStatement(RENAME)) {
            statement.setString(1, word);
            statement.setString(2, translation);
            statement.setInt(3, id);
            statement.setInt(4, userId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                wd = new Word(id, rs.getInt("section_id"), rs.getInt("dictionary_id"), userId, word, translation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wd;
    }

    public Word delete(int id, int userId) {
        Word wd = null;
        try (PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.setInt(2, userId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                String word = rs.getString("word");
                String translation = rs.getString("translation");
                int dictionaryId = rs.getInt("dictionary_id");
                int sectionId = rs.getInt("section_id");
                wd = new Word(id, sectionId, dictionaryId, userId, word, translation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wd;
    }

    public List<Word> getAllBySectionId(int sectionId, int userId) {
        List<Word> words = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_SECTION_ID)) {
            statement.setInt(1, sectionId);
            statement.setInt(2, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Word wd = new Word(rs.getInt("id"), rs.getInt("section_id"), rs.getInt("dictionary_id"), rs.getInt("user_id"), rs.getString("word"), rs.getString("translation"));
                    words.add(wd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return words;
    }

    public List<Word> getAllByDictionaryId(int dictionaryId, int userId) {
        List<Word> words = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_DICTIONARY_ID)) {
            statement.setInt(1, dictionaryId);
            statement.setInt(2, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Word wd = new Word(rs.getInt("id"), rs.getInt("section_id"), rs.getInt("dictionary_id"), rs.getInt("user_id"), rs.getString("word"), rs.getString("translation"));
                    words.add(wd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return words;
    }

    public boolean deleteAllBySectionId(int sectionId, int userId) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ALL_BY_SECTION_ID)) {
            statement.setInt(1, sectionId);
            statement.setInt(2, userId);
            result = statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getWordsCountByDictionaryId(int dictionaryId) {
        int count = 0;
        try (PreparedStatement statement = connection.prepareStatement(GET_WORDS_COUNT_BY_DICTIONARY_ID)) {
            statement.setInt(1, dictionaryId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public void saveWordsList(List<Word> words, int userId, int dictionaryId) {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_WORDS_LIST)) {
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
            e.printStackTrace();
        }
    }
}
