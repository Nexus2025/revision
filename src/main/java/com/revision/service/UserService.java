package com.revision.service;

import com.revision.dao.UserDao;
import com.revision.dao.impl.UserDaoImpl;
import com.revision.entity.Role;
import com.revision.entity.User;

public class UserService {

    private final UserDao userDao;

    public UserService() {
        this.userDao = new UserDaoImpl();
    }

    public int getCountOfWords(int id) {
        return userDao.getWordsCount(id);
    }

    public int getCountOfDictionaries(int id) {
        return userDao.getDictionariesCount(id);
    }

    public boolean checkExists(String userName) {
        return userDao.checkExists(userName);
    }

    public void create(String userName, String userPassword, Role role) {
        userDao.create(userName, userPassword, role);
    }

    public User get(String userName) {
        return userDao.get(userName);
    }
}
