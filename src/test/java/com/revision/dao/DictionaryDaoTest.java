package com.revision.dao;

import com.ibatis.common.jdbc.ScriptRunner;
import com.revision.dao.impl.DictionaryDaoImpl;
import com.revision.dao.util.ConnectionFactory;
import com.revision.dao.util.DBUtil;
import com.revision.dao.util.DictionaryData;
import com.revision.entity.Dictionary;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.assertj.core.api.Assertions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.List;

import static com.revision.dao.util.DictionaryData.*;
import static com.revision.dao.util.UserData.*;

public class DictionaryDaoTest {

    private final DictionaryDao dictionaryDao;

    private static final Logger log = LoggerFactory.getLogger(DictionaryDao.class);

    public DictionaryDaoTest() {
        dictionaryDao = new DictionaryDaoImpl();
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
        Dictionary actual = dictionaryDao.create(DICT_NAME, USER_ID);
        Dictionary expected = new Dictionary(DICT_NEW_ID, USER_ID, DICT_NAME);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void createUserNotExist() {
        Dictionary actual = dictionaryDao.create(DICT_NAME, USER_WRONG_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void delete() {
        Dictionary actual = dictionaryDao.delete(USER_ID, DICT_ID);
        Dictionary expected = new Dictionary(DICT_ID, USER_ID, DICT_DELETED_NAME);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);

        Assert.assertNull(dictionaryDao.get(USER_ID, DICT_ID));
    }

    @Test
    public void deleteWrongUserId() {
        Dictionary actual = dictionaryDao.delete(USER_WRONG_ID, DICT_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void deleteWrongDictionaryId() {
        Dictionary actual = dictionaryDao.delete(USER_ID, DICT_WRONG_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void rename() {
        Dictionary actual = dictionaryDao.rename(DICT_RENAMED_NAME, DICT_ID, USER_ID);
        Dictionary expected = new Dictionary(DICT_ID, USER_ID, DICT_RENAMED_NAME);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void renameWrongUserId() {
        Dictionary actual = dictionaryDao.rename(DICT_RENAMED_NAME, DICT_ID, USER_WRONG_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void renameWrongDictionaryId() {
        Dictionary actual = dictionaryDao.rename(DICT_RENAMED_NAME, DICT_WRONG_ID, USER_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void get() {
        Dictionary actual = dictionaryDao.get(USER_ID, DICT_ID);
        Dictionary expected = new Dictionary(DICT_ID, USER_ID, DICT_EXPECTED_NAME_1);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void getWrongUserId() {
        Dictionary actual = dictionaryDao.get(USER_WRONG_ID, DICT_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void getWrongDictionaryId() {
        Dictionary actual = dictionaryDao.get(USER_ID, DICT_WRONG_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void getAll() {
        List<Dictionary> actual = dictionaryDao.getAll(USER_ID);
        List<Dictionary> expected = getExpectedList();

        Assertions.assertThat(actual)
                .hasSize(expected.size())
                .usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(expected);
    }
}
