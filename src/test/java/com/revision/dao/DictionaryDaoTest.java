package com.revision.dao;

import com.ibatis.common.jdbc.ScriptRunner;
import com.revision.dao.impl.DictionaryDaoImpl;
import com.revision.dao.util.ConnectionFactory;
import com.revision.dao.util.DBUtil;
import com.revision.dao.util.DictionaryData;
import com.revision.dao.util.UserData;
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
        Dictionary actual = dictionaryDao.create(DictionaryData.NAME, UserData.ID);
        Dictionary expected = new Dictionary(DictionaryData.NEW_ID, UserData.ID, DictionaryData.NAME);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void createUserNotExist() {
        Dictionary actual = dictionaryDao.create(DictionaryData.NAME, UserData.WRONG_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void delete() {
        Dictionary actual = dictionaryDao.delete(UserData.ID, DictionaryData.ID_1);
        Dictionary expected = new Dictionary(DictionaryData.ID_1, UserData.ID, DictionaryData.DELETED_NAME);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);

        Assert.assertNull(dictionaryDao.get(UserData.ID, DictionaryData.ID_1));
    }

    @Test
    public void deleteWrongUserId() {
        Dictionary actual = dictionaryDao.delete(UserData.WRONG_ID, DictionaryData.ID_1);
        Assert.assertNull(actual);
    }

    @Test
    public void deleteWrongDictionaryId() {
        Dictionary actual = dictionaryDao.delete(UserData.ID, DictionaryData.WRONG_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void rename() {
        Dictionary actual = dictionaryDao.rename(DictionaryData.RENAMED_NAME, DictionaryData.ID_1, UserData.ID);
        Dictionary expected = new Dictionary(DictionaryData.ID_1, UserData.ID, DictionaryData.RENAMED_NAME);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void renameWrongUserId() {
        Dictionary actual = dictionaryDao.rename(DictionaryData.RENAMED_NAME, DictionaryData.ID_1, UserData.WRONG_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void renameDictionaryUserId() {
        Dictionary actual = dictionaryDao.rename(DictionaryData.RENAMED_NAME, DictionaryData.WRONG_ID, UserData.ID);
        Assert.assertNull(actual);
    }

    @Test
    public  void get() {
        Dictionary actual = dictionaryDao.get(UserData.ID, DictionaryData.ID_1);
        Dictionary expected = new Dictionary(DictionaryData.ID_1, UserData.ID, DictionaryData.EXPECTED_NAME_1);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void getWrongUserId() {
        Dictionary actual = dictionaryDao.get(UserData.WRONG_ID, DictionaryData.ID_1);
        Assert.assertNull(actual);
    }

    @Test
    public void getWrongDictionaryId() {
        Dictionary actual = dictionaryDao.get(UserData.ID, DictionaryData.WRONG_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void getAll() {
        List<Dictionary> actual = dictionaryDao.getAll(UserData.ID);
        List<Dictionary> expected = DictionaryData.getExpectedList();

        Assertions.assertThat(actual)
                .hasSize(expected.size())
                .usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(expected);
    }
}
