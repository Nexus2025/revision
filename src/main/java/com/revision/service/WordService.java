package com.revision.service;

import com.revision.dao.WordDAO;
import com.revision.entity.Word;

import java.util.*;

public class WordService {

    private final WordDAO wordDAO = new WordDAO();

    public void deleteAllBySectionId(int sectionId, int userId) {
        wordDAO.deleteAllBySectionId(sectionId, userId);
    }

    public List<Word> getAllBySectionId(int sectionId, int userId) {
        return wordDAO.getAllBySectionId(sectionId, userId);
    }

    public List<Word> getAllByDictionaryId(int dictionaryId, int userId) {
        return wordDAO.getAllByDictionaryId(dictionaryId, userId);
    }

    public void delete(int wordId, int userId) {
        wordDAO.delete(wordId, userId);
    }

    public void create(String word, String translation, int sectionId, int userId, int dictionaryId) {
        wordDAO.create(word, translation, sectionId, userId, dictionaryId);
    }

    public void rename(String word, String translation, int wordId, int userId) {
        wordDAO.rename(word, translation, wordId, userId);
    }

    public void saveList(List<Word> words, int userId, int dictionaryId) {
        wordDAO.saveWordsList(words, userId, dictionaryId);
    }
}
