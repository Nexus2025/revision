package com.revision.entity;

public class Word{

    private int wordId;
    private int sectionId;
    private int userId;
    private String word;
    private String translation;

    public Word (int wordId, int sectionId, int userId, String word, String translation) {
        this.wordId = wordId;
        this.sectionId = sectionId;
        this.userId = userId;
        this.word = word;
        this.translation = translation;
    }

    public Word (String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    public Word (String word, String translation, int sectionId) {
        this.word = word;
        this.translation = translation;
        this.sectionId = sectionId;
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

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
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

        Word word1 = (Word) o;

        if (wordId != word1.wordId) return false;
        if (sectionId != word1.sectionId) return false;
        if (userId != word1.userId) return false;
        if (word != null ? !word.equals(word1.word) : word1.word != null) return false;
        return translation != null ? translation.equals(word1.translation) :  word1.translation != null;
    }

    @Override
    public int hashCode() {
        int result = wordId;
        result = 31 * result + sectionId;
        result = 31 * result + userId;
        result = 31 * result + (word != null ? word.hashCode() : 0);
        result = 31 * result + (translation != null ? translation.hashCode() : 0);
        return result;
    }
}
