package com.revision.dao;

import com.ibatis.common.jdbc.ScriptRunner;
import com.revision.dao.impl.SectionDaoImpl;
import com.revision.dao.util.ConnectionFactory;
import com.revision.dao.util.DBUtil;
import com.revision.entity.Section;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.List;

import static com.revision.dao.data.DictionaryData.*;
import static com.revision.dao.data.UserData.*;
import static com.revision.dao.data.SectionData.*;

public class SectionDaoTest {

    private final SectionDao sectionDao;

    private static final Logger log = LoggerFactory.getLogger(DictionaryDao.class);

    public SectionDaoTest() {
        this.sectionDao = new SectionDaoImpl();
    }

    @Before
    public void prepareDatabase() {
        try (Connection connection = ConnectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            new ScriptRunner(connection, false, false)
                    .runScript(new BufferedReader(new InputStreamReader(
                            DBUtil.class.getResourceAsStream("/db/prepareDatabase.sql"), StandardCharsets.UTF_8)));
            connection.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Test
    public void create() {
        Section actual = sectionDao.create(SECT_NAME, DICT_ID, USER_ID);
        Section expected = new Section(SECT_NEW_ID, DICT_ID, USER_ID, SECT_NAME);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void createUserNotExist() {
        Section actual = sectionDao.create(SECT_NAME, DICT_ID, USER_WRONG_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void createDictionaryNotExist() {
        Section actual = sectionDao.create(SECT_NAME, DICT_WRONG_ID, USER_WRONG_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void delete() {
        Section actual = sectionDao.delete(SECT_ID, USER_ID);
        Section expected = new Section(SECT_ID, DICT_ID, USER_ID, SECT_NAME);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);

        Assert.assertNull(sectionDao.get(USER_ID, SECT_ID));
    }

    @Test
    public void deleteWrongUserId() {
        Section actual = sectionDao.delete(SECT_ID, USER_WRONG_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void deleteWrongSectionId() {
        Section actual = sectionDao.delete(SECT_WRONG_ID, USER_WRONG_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void rename() {
        Section actual = sectionDao.rename(SECT_NEW_NAME, SECT_ID, USER_ID);
        Section expected = new Section(SECT_ID, DICT_ID, USER_ID, SECT_NEW_NAME);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void renameWrongUserId() {
        Section actual = sectionDao.rename(SECT_NEW_NAME, SECT_ID, USER_WRONG_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void renameSectionId() {
        Section actual = sectionDao.rename(SECT_NEW_NAME, SECT_WRONG_ID, USER_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void get() {
        Section actual = sectionDao.get(USER_ID, SECT_ID);
        Section expected = new Section(SECT_ID, DICT_ID, USER_ID, SECT_NAME);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void getAllByDictionaryId() {
        List<Section> actual = sectionDao.getAllByDictionaryId(DICT_ID, USER_ID);
        List<Section> expected = getExpectedListByDictionaryId();

        Assertions.assertThat(actual)
                .hasSize(expected.size())
                .usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(expected);
    }

    @Test
    public void getAllByDictionaryIdWrongUserId() {
        List<Section> actual = sectionDao.getAllByDictionaryId(DICT_ID, USER_WRONG_ID);

        Assertions.assertThat(actual)
                .isEmpty();
    }

    @Test
    public void getAllByDictionaryIdWrongDictionaryId() {
        List<Section> actual = sectionDao.getAllByDictionaryId(DICT_WRONG_ID, USER_ID);

        Assertions.assertThat(actual)
                .isEmpty();
    }

    @Test
    public void getAll() {
        List<Section> actual = sectionDao.getAll(USER_ID);
        List<Section> expected = getExpectedListByUserId();

        Assertions.assertThat(actual)
                .hasSize(expected.size())
                .usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(expected);
    }

    @Test
    public void getAllWrongUserId() {
        List<Section> actual = sectionDao.getAll(USER_WRONG_ID);

        Assertions.assertThat(actual)
                .isEmpty();
    }

    @Test
    public void deleteAllByDictionaryId() {
        boolean actual = sectionDao.deleteAllByDictionaryId(DICT_ID, USER_ID);
        Assertions.assertThat(actual).isTrue();

        Assertions.assertThat(sectionDao.getAllByDictionaryId(DICT_ID, USER_ID))
                .isEmpty();
    }

    @Test
    public void deleteAllByDictionaryIdWrongDictionaryId() {
        boolean actual = sectionDao.deleteAllByDictionaryId(DICT_WRONG_ID, USER_ID);
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    public void deleteAllByDictionaryIdWrongUserId() {
        boolean actual = sectionDao.deleteAllByDictionaryId(DICT_ID, USER_WRONG_ID);
        Assertions.assertThat(actual).isFalse();
    }
}
