package com.revision.dao;

import com.revision.entity.Word;

import java.util.List;

public interface WordDao {

    Word create(String word, String translation, int sectionId, int userId, int dictionaryId);

    Word rename(String word, String translation, int id, int userId);

    Word delete(int id, int userId);

    List<Word> getAllBySectionId(int sectionId, int userId);

    List<Word> getAllByDictionaryId(int dictionaryId, int userId);

    boolean deleteAllBySectionId(int sectionId, int userId);

    void saveWordsList(List<Word> words, int userId, int dictionaryId);
}
