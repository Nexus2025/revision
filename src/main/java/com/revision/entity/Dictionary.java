package com.revision.entity;

import java.util.ArrayList;
import java.util.List;

public class Dictionary {

    private int dictionaryId;
    private int userId;
    private String name;
    private List<Section> sectionList;
    private int wordsCount;

    public Dictionary (int dictionaryId, int userId, String name) {
        this.name = name;
        this.dictionaryId = dictionaryId;
        this.userId = userId;
        sectionList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    public int getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(int dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public int getWordsCount() {
        return wordsCount;
    }

    public void setWordsCount(int wordsCount) {
        this.wordsCount = wordsCount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
