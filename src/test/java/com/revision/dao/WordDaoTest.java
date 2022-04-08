package com.revision.dao;

import com.ibatis.common.jdbc.ScriptRunner;
import com.revision.dao.impl.WordDaoImpl;
import com.revision.dao.util.ConnectionFactory;
import com.revision.dao.util.DBUtil;
import com.revision.entity.Word;
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
import static com.revision.dao.data.WordData.*;

public class WordDaoTest {

    private final WordDao wordDao;

    private static final Logger log = LoggerFactory.getLogger(DictionaryDao.class);

    public WordDaoTest() {
        this.wordDao = new WordDaoImpl();
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
        Word actual = wordDao.create(NEW_WORD, NEW_TRANSLATION, SECT_ID, USER_ID, DICT_ID);
        Word expected = new Word(WORD_NEW_ID, SECT_ID, DICT_ID, USER_ID, NEW_WORD, NEW_TRANSLATION);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void createUserNotExist() {
        Word actual = wordDao.create(NEW_WORD, NEW_TRANSLATION, SECT_ID, USER_WRONG_ID, DICT_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void createSectionNotExist() {
        Word actual = wordDao.create(NEW_WORD, NEW_TRANSLATION, SECT_WRONG_ID, USER_ID, DICT_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void createDictionaryNotExist() {
        Word actual = wordDao.create(NEW_WORD, NEW_TRANSLATION, SECT_ID, USER_ID, DICT_WRONG_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void rename() {
        Word actual = wordDao.rename(NEW_WORD, NEW_TRANSLATION, WORD_ID, USER_ID);
        Word expected = new Word(WORD_ID, SECT_ID, DICT_ID, USER_ID, NEW_WORD, NEW_TRANSLATION);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void renameWrongWordId() {
        Word actual = wordDao.rename(NEW_WORD, NEW_TRANSLATION, WORD_WRONG_ID, USER_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void renameWrongUserId() {
        Word actual = wordDao.rename(NEW_WORD, NEW_TRANSLATION, WORD_ID, USER_WRONG_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void delete() {
        Word actual = wordDao.delete(WORD_ID, USER_ID);
        Word expected = new Word(WORD_ID, SECT_ID, DICT_ID, USER_ID, WORD, TRANSLATION);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void deleteWrongWordId() {
        Word actual = wordDao.delete(WORD_WRONG_ID, USER_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void deleteWrongUserId() {
        Word actual = wordDao.delete(WORD_ID, USER_WRONG_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void getAllByDictionaryId() {
        List<Word> actual = wordDao.getAllByDictionaryId(DICT_ID, USER_ID);
        List<Word> expected = getExpectedWordsByDictionaryId();

        Assertions.assertThat(actual)
                .hasSize(expected.size())
                .usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(expected);
    }

    @Test
    public void getAllByDictionaryIdWrongUserId() {
        List<Word> actual = wordDao.getAllByDictionaryId(DICT_ID, USER_WRONG_ID);

        Assertions.assertThat(actual)
                .isEmpty();
    }

    @Test
    public void getAllByDictionaryIdWrongDictionaryId() {
        List<Word> actual = wordDao.getAllByDictionaryId(DICT_WRONG_ID, USER_ID);

        Assertions.assertThat(actual)
                .isEmpty();
    }

    @Test
    public void getAllBySectionId() {
        List<Word> actual = wordDao.getAllBySectionId(SECT_ID, USER_ID);
        List<Word> expected = getExpectedWordsBySectionId();

        Assertions.assertThat(actual)
                .hasSize(expected.size())
                .usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(expected);
    }

    @Test
    public void getAllBySectionIdWrongUserId() {
        List<Word> actual = wordDao.getAllBySectionId(SECT_ID, USER_WRONG_ID);

        Assertions.assertThat(actual)
                .isEmpty();
    }

    @Test
    public void getAllBySectionIdWrongSectionId() {
        List<Word> actual = wordDao.getAllBySectionId(SECT_WRONG_ID, USER_ID);

        Assertions.assertThat(actual)
                .isEmpty();
    }

    @Test
    public void deleteAllBySectionId() {
        boolean actual = wordDao.deleteAllBySectionId(SECT_ID, USER_ID);
        Assertions.assertThat(actual).isTrue();

        List<Word> actualList = wordDao.getAllBySectionId(SECT_ID, USER_ID);
        Assertions.assertThat(actualList)
                .isEmpty();
    }

    @Test
    public void deleteAllBySectionIdWrongSectionId() {
        boolean actual = wordDao.deleteAllBySectionId(SECT_WRONG_ID, USER_ID);
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    public void deleteAllBySectionIdWrongUserId() {
        boolean actual = wordDao.deleteAllBySectionId(SECT_ID, USER_WRONG_ID);
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    public void saveWordsList() {
        wordDao.saveWordsList(getListToSave(), USER_ID, DICT_ID);
        List<Word> actual = wordDao.getAllByDictionaryId(DICT_ID, USER_ID);
        List<Word> expected = getExpectedWordsByDictionaryIdAfterImport();

        Assertions.assertThat(actual)
                .hasSize(expected.size())
                .usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(expected);
    }
}
