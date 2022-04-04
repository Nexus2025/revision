package com.revision.dao;

import com.revision.entity.Dictionary;

import java.util.List;

public interface DictionaryDao {

    Dictionary create(String name, int userId);

    List<Dictionary> getAll(int userId);

    Dictionary delete(int userId, int id);

    Dictionary rename(String newName, int id, int userId);

    Dictionary get(int userId, int id);


}
