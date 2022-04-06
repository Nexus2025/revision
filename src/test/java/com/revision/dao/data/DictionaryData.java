package com.revision.dao.data;

import com.revision.entity.Dictionary;

import java.util.ArrayList;
import java.util.List;

import static com.revision.dao.data.UserData.*;

public class DictionaryData {

    public static final int DICT_NEW_ID = 8;
    public static final int DICT_ID = 1;
    public static final int DICT_WRONG_ID = 999;

    public static final String DICT_NAME = "TEST NAME";
    public static final String DICT_DELETED_NAME = "ADMIN_DICT_1";
    public static final String DICT_EXPECTED_NAME_1 = "ADMIN_DICT_1";
    public static final String DICT_EXPECTED_NAME_2 = "ADMIN_DICT_2";
    public static final String DICT_RENAMED_NAME = "TEST RENAME";

    public static List<Dictionary> getExpectedList() {
        List<Dictionary> expected = new ArrayList<>();
        expected.add(new Dictionary(DICT_ID, USER_ID, DICT_EXPECTED_NAME_1, 5));
        expected.add(new Dictionary(2, USER_ID, DICT_EXPECTED_NAME_2, 4));

        return expected;
    }
}
