package com.revision.service;

import com.revision.dao.WordDAO;
import com.revision.entity.Word;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordManager {

    private List<Word> sectionList;

    public boolean deleteAllWordsById(int sectionId, int userId) {
        boolean result = false;

        WordDAO dao = new WordDAO();
        result = dao.deleteAllWordsById(sectionId, userId);

        return result;
    }

    public List getWordListBySectionId(int sectionId, int userId) {

        WordDAO dao = new WordDAO();
        sectionList = dao.readWordListBySectionId(sectionId, userId);

        return sectionList;
    }

    public List getWordListByDictionaryId(int dictionaryId, int userId) {

        WordDAO dao = new WordDAO();
        sectionList = dao.readWordListByDictionaryId(dictionaryId, userId);

        return sectionList;
    }

    public boolean deleteWord (int wordId, int userId) {
        boolean result = false;

        WordDAO dao = new WordDAO();
        result = dao.deleteWord(wordId, userId);

        return result;
    }

    public boolean createWord (String word, String translation, int sectionId, int userId, int dictionaryId) {
        boolean result = false;

        WordDAO dao = new WordDAO();
        result = dao.createWord(word, translation, sectionId, userId, dictionaryId);

        return result;
    }

    public boolean renameWord (String word, String translation, int wordId, int userId) {
        boolean result = false;

        WordDAO dao = new WordDAO();
        result = dao.renameWord(word, translation, wordId, userId);

        return result;
    }

    public boolean writeWordsLineFromForm (String wordString, int sectionId, int userId, int dictionaryId) {
        boolean result = false;

        List<Word> wordList = prepareWordsLine(wordString);

        if (wordList.size() > 0) {
            WordDAO dao = new WordDAO();
            result = dao.writeWordList(wordList, sectionId, userId, dictionaryId);
        }

        return result;
    }

    private List<Word> prepareWordsLine(String wordString) {

        String stringToSplit = wordString;
        List<Word> wordList = new ArrayList<>();

        if (stringToSplit.contains(";")) {
            String[] arrayOfLines = stringToSplit.split(";");

            for (String line : arrayOfLines) {
                if (line.contains(" - ")) {

                    if (line.contains("\n")) {
                        String formatLine = line.replaceAll("\n", " ");
                        line = formatLine;
                    }

                    String[] arrayOfWords = line.split(" - ");
                    if (!arrayOfWords[0].equals("") && !arrayOfWords[1].equals("")) {
                        Word word = new Word(arrayOfWords[0].trim(), arrayOfWords[1].trim());
                        wordList.add(word);
                    }
                }
            }
        }

        return wordList;
    }

    public boolean importWords (BufferedReader reader, int dictionaryId, int userId) {

        boolean result = false;

        String lineFromCsv = "";

        Map<String, Integer> sections = new HashMap<>();
        List<Word> wordList = new ArrayList<>();

        try {

            String [] temp = reader.readLine().split(";");
            System.out.println(temp.length);

            if (!temp[0].equals("section") && !temp[1].equals("word") && !temp[2].equals("translation")) {
                throw new IOException();
            }

            while ((lineFromCsv = reader.readLine()) != null) {
                temp = lineFromCsv.split(";");

                if(temp[1].contains("'")) {
                    temp[1] = temp[1].replaceAll("'", "''");
                }

                if(temp[2].contains("'")) {
                    temp[2] = temp[2].replaceAll("'", "''");
                }

                if (sections.containsKey(temp[0])) {
                    int sectionId = sections.get(temp[0]);

                    Word word = new Word(temp[1], temp[2], sectionId);
                    wordList.add(word);

                } else {
                    SectionManager sm = new SectionManager();
                    int sectionId = sm.createSection(temp[0], dictionaryId, userId);

                    if (sectionId != -1) {
                        sections.put(temp[0], sectionId);

                        Word word = new Word(temp[1], temp[2], sectionId);
                        wordList.add(word);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!wordList.isEmpty()) {
            WordDAO dao = new WordDAO();
            result = dao.importWords(wordList, userId, dictionaryId);
        }

        return result;
    }
}
