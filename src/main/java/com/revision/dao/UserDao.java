package com.revision.dao;

import com.revision.entity.Role;
import com.revision.entity.User;

public interface UserDao {

    User get(String userName);

    User create(String userName, String password, Role role);

    int getWordsCount(int id);

    int getDictionariesCount(int id);

    boolean checkExists(String userName);
}
