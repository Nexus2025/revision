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

    public boolean deleteAllByDictionaryId(int dictionaryId, int userId) {
        return sectionDAO.deleteAllByDictionaryId(dictionaryId, userId);
    }

    public List<Section> getAll(int userId) {
        return sectionDAO.getAll(userId);
    }

    public Section get(int userId, int id) {
        return sectionDAO.get(userId, id);
    }

    public Section create(String sectionName, int dictionaryId, int userId) {
        return sectionDAO.create(sectionName, dictionaryId, userId);
    }

    public void delete(int id, int userId) {
        sectionDAO.delete(id, userId);
    }

    public void clear(int id, int userId) {
        wordService.deleteAllBySectionId(id, userId);
    }

    public void rename(String sectionNewName, int id, int userId) {
        sectionDAO.rename(sectionNewName, id, userId);
    }
}
