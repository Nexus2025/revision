package com.revision.dao;

import com.ibatis.common.jdbc.ScriptRunner;
import com.revision.dao.impl.UserDaoImpl;
import com.revision.dao.util.ConnectionFactory;
import com.revision.dao.util.DBUtil;
import com.revision.entity.User;
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

import static com.revision.dao.data.UserData.*;

public class UserDaoTest {

    private final UserDao userDao;

    private static final Logger log = LoggerFactory.getLogger(DictionaryDao.class);

    public UserDaoTest() {
        this.userDao = new UserDaoImpl();
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
    public void get() {
        User actual = userDao.get(USER_NAME);
        User expected = new User(USER_ID, ROLE_ADMIN, USER_NAME, USER_PASSWORD);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void getNotExist() {
        User actual = userDao.get(USER_NAME_NOT_EXIST);
        Assert.assertNull(actual);
    }

    @Test
    public void create() {
        User actual = userDao.create(USER_NEW_NAME, USER_PASSWORD, ROLE_ADMIN);
        User expected = new User(USER_NEW_ID, ROLE_ADMIN, USER_NEW_NAME, USER_PASSWORD);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void createUserNameExists() {
        User actual = userDao.create(USER_NAME, USER_PASSWORD, ROLE_ADMIN);
        Assert.assertNull(actual);
    }

    @Test
    public void getWordsCount() {
        int actual = userDao.getWordsCount(USER_ID);
        Assert.assertEquals(actual, USER_WORDS_COUNT);
    }

    @Test
    public void getWordsCountIfUserNotExist() {
        int actual = userDao.getWordsCount(USER_WRONG_ID);
        Assert.assertEquals(actual, 0);
    }

    @Test
    public void getDictionariesCount() {
        int actual = userDao.getDictionariesCount(USER_ID);
        Assert.assertEquals(actual, USER_DICTIONARIES_COUNT);
    }

    @Test
    public void getDictionariesCountIfUserNotExist() {
        int actual = userDao.getDictionariesCount(USER_WRONG_ID);
        Assert.assertEquals(actual, 0);
    }

    @Test
    public void checkExists() {
        boolean actual = userDao.checkExists(USER_NAME);
        Assertions.assertThat(actual)
                .isTrue();
    }

    @Test
    public void checkExistsIfUserNotExist() {
        boolean actual = userDao.checkExists(USER_NAME_NOT_EXIST);
        Assertions.assertThat(actual)
                .isFalse();
    }
}
