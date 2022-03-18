package com.revision.service;

import com.revision.dao.DictionaryDAO;
import com.revision.entity.Dictionary;

import java.util.List;

public class DictionaryService {

    private final DictionaryDAO dictionaryDAO = new DictionaryDAO();
    private final SectionService sectionService = new SectionService();

    public List<Dictionary> getAll(int userId) {
        return dictionaryDAO.getAll(userId);
    }

    public Dictionary get(int userId, int id) {
        return dictionaryDAO.get(userId, id);
    }

    public void create(String dictionaryName, int userId) {
        dictionaryDAO.create(dictionaryName, userId);
    }

    public void delete(int userId, int id) {
        dictionaryDAO.delete(userId, id);
    }

    public static void main(String[] args) {
        DictionaryService service = new DictionaryService();
        service.delete(1, 1);
    }

    public void clear(int userId, int id) {
        sectionService.deleteAllByDictionaryId(id, userId);
    }

    public void rename(String dictionaryNewName, int id, int userId) {
        dictionaryDAO.rename(dictionaryNewName, id, userId);
    }
}
