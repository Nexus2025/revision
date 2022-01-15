package com.revision.model;

import com.revision.db.SectionDAO;
import com.revision.entities.Section;

import java.util.List;

public class SectionManager {

    private List<Section> sectionList;

    public List getSectionListByDictionaryId(int dictionaryId, int userId) {
        SectionDAO dao = new SectionDAO();
        sectionList = dao.readSectionListByDictionaryId(dictionaryId, userId);

        return sectionList;
    }


    public List getSectionListByUserId(int userId) {
        SectionDAO dao = new SectionDAO();
        sectionList = dao.readSectionListByUserId(userId);

        return sectionList;
    }

    public Section getSectionById(int userId, int sectionId) {

        SectionDAO dao = new SectionDAO();
        Section section = dao.getSectionById(userId, sectionId);

        return section;
    }

    public int createSection(String sectionName, int dictionaryId, int userId) {

        SectionDAO dao = new SectionDAO();
        int sectionId = dao.createSection(sectionName, dictionaryId, userId);

        return sectionId;
    }

    public boolean deleteSection(int sectionId, int userId) {
        boolean result = false;

        result = clearSection(sectionId, userId); // clear the word list

        SectionDAO dao = new SectionDAO();
        result = dao.deleteSection(sectionId, userId);

        return result;
    }

    public boolean clearSection(int sectionId, int userId) {
        boolean result = false;

        WordManager wm = new WordManager();
        result = wm.deleteAllWordsById(sectionId, userId);

        return result;
    }

    public boolean renameSection(String sectionNewName, int sectionId, int userId) {
        boolean result = false;

        SectionDAO dao = new SectionDAO();
        result = dao.renameSection(sectionNewName, sectionId, userId);

        return result;
    }
}
