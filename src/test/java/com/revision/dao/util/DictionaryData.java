package com.revision.dao.util;

import com.revision.entity.Dictionary;

import java.util.ArrayList;
import java.util.List;

public class DictionaryData {

    public static final int NEW_ID = 8;
    public static final int ID_1 = 1;
    public static final int ID_2 = 2;
    public static final int WRONG_ID = 999;
    public static final int WORDS_COUNT_1 = 5;
    public static final int WORDS_COUNT_2 = 4;

    public static final String NAME = "TEST NAME";
    public static final String DELETED_NAME = "ADMIN_DICT_1";
    public static final String EXPECTED_NAME_1 = "ADMIN_DICT_1";
    public static final String EXPECTED_NAME_2 = "ADMIN_DICT_2";
    public static final String RENAMED_NAME = "TEST RENAME";

    public static List<Dictionary> getExpectedList() {
        List<Dictionary> expected = new ArrayList<>();
        expected.add(new Dictionary(DictionaryData.ID_1, UserData.ID, DictionaryData.EXPECTED_NAME_1,
                DictionaryData.WORDS_COUNT_1));
        expected.add(new Dictionary(DictionaryData.ID_2, UserData.ID, DictionaryData.EXPECTED_NAME_2,
                DictionaryData.WORDS_COUNT_2));

        return expected;
    }
}
