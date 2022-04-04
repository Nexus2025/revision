package com.revision.service;

import com.revision.dao.SectionDao;
import com.revision.dao.impl.SectionDaoImpl;
import com.revision.entity.Section;

import java.util.List;
import java.util.stream.Collectors;

public class SectionService {

    private final SectionDao sectionDao;
    private final WordService wordService;

    public SectionService() {
        this.sectionDao = new SectionDaoImpl();
        this.wordService = new WordService();
    }

    public List<Section> getAllContainWords(int userId) {
        return sectionDao.getAll(userId)
                .stream()
                .filter(section -> section.getWordsCount() != 0)
                .collect(Collectors.toList());
    }

    public List<Section> getAllByDictionaryId(int dictionaryId, int userId) {
        return sectionDao.getAllByDictionaryId(dictionaryId, userId);
    }

    public boolean deleteAllByDictionaryId(int dictionaryId, int userId) {
        return sectionDao.deleteAllByDictionaryId(dictionaryId, userId);
    }

    public List<Section> getAll(int userId) {
        return sectionDao.getAll(userId);
    }

    public Section get(int userId, int id) {
        return sectionDao.get(userId, id);
    }

    public Section create(String sectionName, int dictionaryId, int userId) {
        return sectionDao.create(sectionName, dictionaryId, userId);
    }

    public void delete(int id, int userId) {
        sectionDao.delete(id, userId);
    }

    public void clear(int id, int userId) {
        wordService.deleteAllBySectionId(id, userId);
    }

    public void rename(String sectionNewName, int id, int userId) {
        sectionDao.rename(sectionNewName, id, userId);
    }
}
