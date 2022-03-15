package com.revision.entity;

public class Section {

    private int id;
    private int dictionaryId;
    private int userId;
    private String name;

    public Section(int sectionId, int dictionaryId, int userId, String name) {
        this.id = sectionId;
        this.dictionaryId = dictionaryId;
        this.userId = userId;
        this.name = name;
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

    public int getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(int dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", dictionaryId=" + dictionaryId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                '}';
    }
}
