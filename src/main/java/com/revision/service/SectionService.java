package com.revision.service;

import com.revision.dao.SectionDAO;
import com.revision.entity.Section;

import java.util.List;

public class SectionService {

    private final SectionDAO sectionDAO = new SectionDAO();
    private final WordService wordService = new WordService();

    public List<Section> getAllByDictionaryId(int dictionaryId, int userId) {
        return sectionDAO.getAllByDictionaryId(dictionaryId, userId);
    }

    public List<Section> getAll(int userId) {
        return sectionDAO.getAll(userId);
    }

    public Section get(int userId, int sectionId) {
        return sectionDAO.get(userId, sectionId);
    }

    public Section create(String sectionName, int dictionaryId, int userId) {
        return sectionDAO.create(sectionName, dictionaryId, userId);
    }

    public void delete(int sectionId, int userId) {
        clear(sectionId, userId);
        sectionDAO.delete(sectionId, userId);
    }

    public void clear(int sectionId, int userId) {
        wordService.deleteAllBySectionId(sectionId, userId);
    }

    public void rename(String sectionNewName, int sectionId, int userId) {
        sectionDAO.rename(sectionNewName, sectionId, userId);
    }
}
