package com.revision.dao.data;

import com.revision.entity.Word;

import java.util.ArrayList;
import java.util.List;

public class WordData {

    public static final int WORD_NEW_ID = 10;
    public static final int WORD_ID = 1;
    public static final int WORD_WRONG_ID = 999;

    public static final String NEW_WORD = "NEW WORD";
    public static final String NEW_TRANSLATION = "NEW TRANSLATION";
    public static final String WORD = "Hello";
    public static final String TRANSLATION = "Привет";

    public static List<Word> getExpectedWordsByDictionaryId() {
        List<Word> expected = new ArrayList<>();
        expected.add(new Word(1, 1, 1, 1, "Hello", "Привет"));
        expected.add(new Word(2, 1, 1, 1, "Get", "Получать"));
        expected.add(new Word(3, 1, 1, 1, "Give", "Давать"));
        expected.add(new Word(4, 2, 1, 1, "Move", "Двигаться"));
        expected.add(new Word(5, 2, 1, 1, "Run", "Бежать"));

        return expected;
    }

    public static List<Word> getExpectedWordsBySectionId() {
        List<Word> expected = new ArrayList<>();
        expected.add(new Word(1, 1, 1, 1, "Hello", "Привет"));
        expected.add(new Word(2, 1, 1, 1, "Get", "Получать"));
        expected.add(new Word(3, 1, 1, 1, "Give", "Давать"));

        return expected;
    }

    public static List<Word> getListToSave() {
        List<Word> words = new ArrayList<>();
        words.add(new Word(1, "TEST1", "TEST2"));
        words.add(new Word(1, "TEST11", "TEST22"));
        words.add(new Word(1, "TEST111", "TEST222"));

        return words;
    }

    public static List<Word> getExpectedWordsByDictionaryIdAfterImport() {
        List<Word> expected = new ArrayList<>();
        expected.add(new Word(1, 1, 1, 1, "Hello", "Привет"));
        expected.add(new Word(2, 1, 1, 1, "Get", "Получать"));
        expected.add(new Word(3, 1, 1, 1, "Give", "Давать"));
        expected.add(new Word(4, 2, 1, 1, "Move", "Двигаться"));
        expected.add(new Word(5, 2, 1, 1, "Run", "Бежать"));
        expected.add(new Word(10, 1, 1, 1, "TEST1", "TEST2"));
        expected.add(new Word(11, 1, 1, 1, "TEST11", "TEST22"));
        expected.add(new Word(12, 1, 1, 1, "TEST111", "TEST222"));

        return expected;
    }
}
