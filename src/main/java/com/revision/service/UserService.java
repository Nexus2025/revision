package com.revision.service;

import com.revision.dao.UserDAO;
import com.revision.entity.Role;
import com.revision.entity.User;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public int getCountOfWords(int id) {
        return userDAO.getWordsCount(id);
    }

    public int getCountOfDictionaries(int id) {
        return userDAO.getDictionariesCount(id);
    }

    public boolean checkExists(String userName) {
        return userDAO.checkExists(userName);
    }

    public void create(String userName, String userPassword, Role role) {
        userDAO.create(userName, userPassword, role);
    }

    public User get(String userName) {
        return userDAO.get(userName);
    }
}
