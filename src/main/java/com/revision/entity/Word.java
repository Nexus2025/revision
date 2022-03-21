package com.revision.entity;

public class Word{

    private int id;
    private int sectionId;
    private int dictionaryId;
    private int userId;
    private String word;
    private String translation;

    public Word(int id, int sectionId, int dictionaryId, int userId, String word, String translation) {
        this.id = id;
        this.sectionId = sectionId;
        this.dictionaryId = dictionaryId;
        this.userId = userId;
        this.word = word;
        this.translation = translation;
    }

    public Word (int sectionId, String word, String translation) {
        this.sectionId = sectionId;
        this.word = word;
        this.translation = translation;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSectionId() {
        return sectionId;
    }

    public int getDictionaryId() {
        return dictionaryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        if (id != word.id) return false;
        if (sectionId != word.sectionId) return false;
        if (userId != word.userId) return false;
        if (this.word != null ? !this.word.equals(word.word) : word.word != null) return false;
        return translation != null ? translation.equals(word.translation) :  word.translation != null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + sectionId;
        result = 31 * result + userId;
        result = 31 * result + (word != null ? word.hashCode() : 0);
        result = 31 * result + (translation != null ? translation.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", sectionId=" + sectionId +
                ", dictionaryId=" + dictionaryId +
                ", userId=" + userId +
                ", word='" + word + '\'' +
                ", translation='" + translation + '\'' +
                '}';
    }
}
