package com.revision.service;

import com.revision.dao.DictionaryDAO;
import com.revision.dao.WordDAO;
import com.revision.entity.Dictionary;
import com.revision.entity.Section;

import java.util.List;

public class DictionaryManager {

    private List<Dictionary> dictionaryList;

    public List getDictionaryListByUserId (int userId) {
        DictionaryDAO dao = new DictionaryDAO();
        dictionaryList = dao.readDictionaryList(userId);

        WordDAO wmDao = new WordDAO();
        for (Dictionary dictionary : dictionaryList) {
            int dictionaryId = dictionary.getDictionaryId();
            dictionary.setWordsCount(wmDao.getWordsCountsByDictionaryId(dictionaryId));
        }

        return dictionaryList;
    }

    public Dictionary getDictionaryById(int userId, int dictionaryId) {

        DictionaryDAO dao = new DictionaryDAO();
        Dictionary dictionary = dao.getDictionaryById(userId, dictionaryId);

        return dictionary;
    }

    public List createDictionary(String dictionaryName, int userId) {
        DictionaryDAO dao = new DictionaryDAO();
        boolean result = dao.create(dictionaryName, userId);
        if (result) {
            dictionaryList = getDictionaryListByUserId(userId);
        }
        return dictionaryList;
    }

    public List deleteDictionary(int userId, int dictionaryId) {

        clearDictionary(userId, dictionaryId);

        DictionaryDAO dao = new DictionaryDAO();
        boolean result = dao.delete(userId, dictionaryId);
        if (result) {
            dictionaryList = getDictionaryListByUserId(userId);
        }
        return dictionaryList;
    }

    public List clearDictionary(int userId, int dictionaryId) {

        SectionManager sm = new SectionManager();

        List<Section> sectionList = sm.getSectionListByDictionaryId(dictionaryId, userId);

        for (Section section : sectionList) {
            sm.deleteSection(section.getSectionId(), section.getUserId());
        }

        dictionaryList = getDictionaryListByUserId(userId);

        return dictionaryList;
    }

    public List renameDictionary(String dictionaryNewName, int dictionaryId, int userId) {
        DictionaryDAO dao = new DictionaryDAO();
        boolean result = dao.rename(dictionaryNewName, dictionaryId, userId);
        if (result) {
            dictionaryList = getDictionaryListByUserId(userId);
        }
        return dictionaryList;
    }
}
