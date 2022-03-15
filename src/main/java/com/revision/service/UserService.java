package com.revision.service;

import com.revision.dao.UserDAO;
import com.revision.entity.Role;
import com.revision.entity.User;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public int getCountOfWords(int userId) {
        return userDAO.getWordsCount(userId);
    }

    public int getCountOfDictionaries(int userId) {
        return userDAO.getDictionariesCount(userId);
    }

    public boolean checkExists(String userLogin) {
        return userDAO.checkExists(userLogin);
    }

    public void create(String userLogin, String userPassword, Role role) {
        userDAO.create(userLogin, userPassword, role);
    }

    public User get(String login) {
        return userDAO.get(login);
    }
}
