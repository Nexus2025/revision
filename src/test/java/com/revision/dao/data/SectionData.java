package com.revision.dao.data;

import com.revision.entity.Section;

import java.util.ArrayList;
import java.util.List;

import static com.revision.dao.data.DictionaryData.*;
import static com.revision.dao.data.UserData.*;

public class SectionData {

    public static final int SECT_NEW_ID = 7;
    public static final int SECT_ID = 1;
    public static final int SECT_WRONG_ID = 999;

    public static final String SECT_NAME = "ADMIN_SECT_1";
    public static final String SECT_NEW_NAME = "SECT NEW NAME";

    public static List<Section> getExpectedListByUserId() {
        List<Section> expected = new ArrayList<>();
        expected.add(new Section(1, 1, USER_ID, "ADMIN_SECT_1", 3));
        expected.add(new Section(2, 1, USER_ID, "ADMIN_SECT_2", 2));
        expected.add(new Section(3, 2, USER_ID, "ADMIN_SECT_3", 4));
        expected.add(new Section(4, 2, USER_ID, "ADMIN_SECT_4", 0));

        return expected;
    }

    public static List<Section> getExpectedListByDictionaryId() {
        List<Section> expected = new ArrayList<>();
        expected.add(new Section(1, DICT_ID, USER_ID, "ADMIN_SECT_1", 3));
        expected.add(new Section(2, DICT_ID, USER_ID, "ADMIN_SECT_2", 2));

        return expected;
    }
}
