package com.revision.entity;

import java.util.List;

public class User {

    private int userId;
    private int role; //1 - user, 2 - admin
    private String login;
    private String password;
    private String secretKey;
    private List<Dictionary> dictionaryList;

    public User (int userId, int role, String login, String password, String secretKey) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.role = role;
        this.secretKey = secretKey;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public List<Dictionary> getDictionaryList() {
        return dictionaryList;
    }

    public void setDictionaryList(List<Dictionary> dictionaryList) {
        this.dictionaryList = dictionaryList;
    }
}
