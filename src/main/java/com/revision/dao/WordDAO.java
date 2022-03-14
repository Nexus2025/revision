package com.revision.dao;

import com.revision.entity.Word;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WordDAO {

    private List<Word> wordList = new ArrayList<>();

    public boolean deleteAllWordsById(int sectionId, int userId) {

        boolean result = false;

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.createStatement();
            int returnValue = stmt.executeUpdate("DELETE FROM words WHERE section_id=" + sectionId + " AND user_id=" + userId + ";");
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

    public List readWordListBySectionId(int sectionId, int userId) {

        List<Word> wordList = new ArrayList<>();

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM words WHERE section_id=" + sectionId + " AND user_id=" + userId + ";");
            while (rs.next()) {
                Word word = new Word(rs.getInt("word_id"), rs.getInt("section_id"), rs.getInt("user_id"), rs.getString("word"), rs.getString("translation"));
                wordList.add(word);
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

        return wordList;
    }

    public List readWordListByDictionaryId(int dictionaryId, int userId) {

        List<Word> wordList = new ArrayList<>();

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM words WHERE dictionary_id=" + dictionaryId + " AND user_id=" + userId + ";");
            while (rs.next()) {
                Word word = new Word(rs.getInt("word_id"), rs.getInt("section_id"), rs.getInt("user_id"), rs.getString("word"), rs.getString("translation"));
                wordList.add(word);
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

        return wordList;
    }

    public boolean deleteWord (int wordId, int userId) {
        boolean result = false;

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.createStatement();
            int returnValue = stmt.executeUpdate("DELETE FROM words WHERE word_id=" + wordId + " AND user_id=" + userId + ";");
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

    public boolean createWord (String word, String translation, int sectionId, int userId, int dictionaryId) {

        boolean result = false;

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.createStatement();
            int returnValue = stmt.executeUpdate("INSERT INTO words (word, translation, section_id, user_id, dictionary_id) VALUES ('" + word + "','" + translation + "'," + sectionId + "," + userId + "," + dictionaryId + ");");
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

    public boolean writeWordList (List<Word> wordList, int sectionId, int userId, int dictionaryId) {

        boolean result = false;

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO words (word, translation, section_id, user_id, dictionary_id) VALUES");

        for (int i = 0; i < wordList.size(); i++) {
            Word word = wordList.get(i);
            if (i == wordList.size() - 1) {
                sb.append(" ('" + word.getWord() + "','" + word.getTranslation() + "'," + sectionId + "," + userId + "," + dictionaryId +");");
                break;
            }
            sb.append(" ('" + word.getWord() + "','" + word.getTranslation() + "'," + sectionId + "," + userId + "," + dictionaryId +"),");
        }

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.createStatement();
            int returnValue = stmt.executeUpdate(sb.toString());
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

    public boolean renameWord (String word, String translation, int wordId, int userId) {

        boolean result = false;

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.createStatement();
            int returnValue = stmt.executeUpdate("UPDATE words SET word='"+ word +"', translation='" + translation + "' WHERE word_id=" + wordId + " AND user_id=" + userId + ";");
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

    public boolean importWords (List<Word> wordList, int userId, int dictionaryId) {

        boolean result = false;

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO words (word, translation, section_id, user_id, dictionary_id) VALUES");

        for (int i = 0; i < wordList.size(); i++) {
            Word word = wordList.get(i);
            if (i == wordList.size() - 1) {
                sb.append(" ('" + word.getWord() + "','" + word.getTranslation() + "'," + word.getSectionId() + "," + userId + "," + dictionaryId + ");");
                break;
            }
            sb.append(" ('" + word.getWord() + "','" + word.getTranslation() + "'," + word.getSectionId() + "," + userId + "," + dictionaryId +"),");
        }

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.createStatement();
            int returnValue = stmt.executeUpdate(sb.toString());
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

    public int getWordsCountsByDictionaryId (int dictionaryId) {

        int count = 0;

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Database db;

        try {
            db = new Database();
            conn = db.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT count(*) FROM words WHERE dictionary_id=" + dictionaryId + ";");
            rs.next();
            count = rs.getInt(1);

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

        return count;

    }
}
