package com.revision.entity;

public class Dictionary {

    private int id;
    private int userId;
    private String name;
    private int wordsCount;

    public Dictionary (int id, int userId, String name) {
        this(id, userId, name, 0);
    }

    public Dictionary (int id, int userId, String name, int wordsCount) {
        this.name = name;
        this.id = id;
        this.userId = userId;
        this.wordsCount = wordsCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWordsCount() {
        return wordsCount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                '}';
    }
}
