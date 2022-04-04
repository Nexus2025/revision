package com.revision.service;

import com.revision.dao.WordDao;
import com.revision.dao.impl.WordDaoImpl;
import com.revision.entity.Word;

import java.util.*;

public class WordService {

    private final WordDao wordDao;

    public WordService() {
        this.wordDao = new WordDaoImpl();
    }

    public boolean deleteAllBySectionId(int sectionId, int userId) {
        return wordDao.deleteAllBySectionId(sectionId, userId);
    }

    public List<Word> getAllBySectionId(int sectionId, int userId) {
        return wordDao.getAllBySectionId(sectionId, userId);
    }

    public List<Word> getAllByDictionaryId(int dictionaryId, int userId) {
        return wordDao.getAllByDictionaryId(dictionaryId, userId);
    }

    public void delete(int id, int userId) {
        wordDao.delete(id, userId);
    }

    public void create(String word, String translation, int sectionId, int userId, int dictionaryId) {
        wordDao.create(word, translation, sectionId, userId, dictionaryId);
    }

    public void rename(String word, String translation, int id, int userId) {
        wordDao.rename(word, translation, id, userId);
    }

    public void saveList(List<Word> words, int userId, int dictionaryId) {
        wordDao.saveWordsList(words, userId, dictionaryId);
    }
}
