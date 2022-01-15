package com.revision.model;

import com.revision.db.UserDAO;
import com.revision.entities.User;

public class UserManager {

    public int getCountOfWordsByUserId(int userId) {
        UserDAO dao = new UserDAO();
        int count = dao.getWordsCountByUserId(userId);

        return count;
    }

    public int getCountOfDictionariesByUserId(int userId) {
        UserDAO dao = new UserDAO();
        int count = dao.getDictionariesCountByUserId(userId);

        return count;
    }

    public boolean checkUserExists (String userLogin) {
        boolean result;

        UserDAO dao = new UserDAO();
        result = dao.checkUserExists(userLogin);

        return result;
    }

    public boolean createUser(String userLogin, String userPassword, int role) {
        UserDAO userDAO = new UserDAO();
        boolean result = userDAO.create(userLogin, userPassword, role);

        return result;
    }

    public User userRead (String login) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.read(login);

        return user;
    }
}
