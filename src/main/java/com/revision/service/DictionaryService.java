package com.revision.service;

import com.revision.dao.DictionaryDAO;
import com.revision.dao.WordDAO;
import com.revision.entity.Dictionary;
import com.revision.entity.Section;

import java.util.List;

public class DictionaryService {

    private final DictionaryDAO dictionaryDAO = new DictionaryDAO();
    private final SectionService sectionService = new SectionService();

    public List<Dictionary> getAll(int userId) {
        List<Dictionary> dictionaryList = dictionaryDAO.getAll(userId);
        WordDAO wordDAO = new WordDAO();
        for (Dictionary dictionary : dictionaryList) {
            dictionary.setWordsCount(wordDAO.getWordsCountByDictionaryId(dictionary.getId()));
        }
        return dictionaryList;
    }

    public Dictionary get(int userId, int dictionaryId) {
        return dictionaryDAO.get(userId, dictionaryId);
    }

    public void create(String dictionaryName, int userId) {
        dictionaryDAO.create(dictionaryName, userId);
    }

    public void delete(int userId, int dictionaryId) {
        clear(userId, dictionaryId);
        dictionaryDAO.delete(userId, dictionaryId);
    }

    public void clear(int userId, int dictionaryId) {
        List<Section> sectionList = sectionService.getAllByDictionaryId(dictionaryId, userId);
        for (Section section : sectionList) {
            sectionService.delete(section.getId(), section.getUserId());
        }
    }

    public void rename(String dictionaryNewName, int dictionaryId, int userId) {
        dictionaryDAO.rename(dictionaryNewName, dictionaryId, userId);
    }
}
