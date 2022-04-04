package com.revision.service;

import com.revision.dao.DictionaryDao;
import com.revision.dao.impl.DictionaryDaoImpl;
import com.revision.entity.Dictionary;

import java.util.List;
import java.util.stream.Collectors;

public class DictionaryService {

    private final DictionaryDao dictionaryDao;
    private final SectionService sectionService;

    public DictionaryService() {
        this.dictionaryDao = new DictionaryDaoImpl();
        this.sectionService = new SectionService();
    }

    public List<Dictionary> getAllContainWords(int userId) {
        return dictionaryDao.getAll(userId)
                .stream()
                .filter(dictionary -> dictionary.getWordsCount() != 0)
                .collect(Collectors.toList());
    }

    public List<Dictionary> getAll(int userId) {
        return dictionaryDao.getAll(userId);
    }

    public Dictionary get(int userId, int id) {
        return dictionaryDao.get(userId, id);
    }

    public void create(String dictionaryName, int userId) {
        dictionaryDao.create(dictionaryName, userId);
    }

    public void delete(int userId, int id) {
        dictionaryDao.delete(userId, id);
    }

    public void clear(int userId, int id) {
        sectionService.deleteAllByDictionaryId(id, userId);
    }

    public void rename(String dictionaryNewName, int id, int userId) {
        dictionaryDao.rename(dictionaryNewName, id, userId);
    }
}
