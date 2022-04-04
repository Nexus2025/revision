package com.revision.dao;

import com.revision.entity.Section;

import java.util.List;

public interface SectionDao {

    Section create(String name, int dictionaryId, int userId);

    List<Section> getAllByDictionaryId(int dictionaryId, int userId);

    List<Section> getAll(int userId);

    boolean deleteAllByDictionaryId(int dictionaryId, int userId);

    Section delete(int id, int userId);

    Section rename(String newName, int id, int userId);

    Section get(int userId, int id);
}
