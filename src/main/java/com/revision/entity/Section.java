package com.revision.entity;

import java.util.ArrayList;
import java.util.List;

public class Section {

    private int sectionId;
    private int dictionaryId;
    private int userId;
    private String name;
    private List<Word> wordList;
    private int wordsCount;

    public Section(int sectionId, int dictionaryId, int userId, String name) {
        this.sectionId = sectionId;
        this.dictionaryId = dictionaryId;
        this.userId = userId;
        this.name = name;
        wordList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Word> getWordList() {
        return wordList;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
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

    public int getWordsCount() {
        return wordsCount;
    }

    public void setWordsCount(int wordsCount) {
        this.wordsCount = wordsCount;
    }

    public void setWordList(List<Word> wordList) {
        this.wordList = wordList;
    }
}
