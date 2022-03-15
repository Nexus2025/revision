package com.revision.util;

import com.revision.entity.Section;
import com.revision.entity.Word;
import com.revision.service.SectionService;
import com.revision.service.WordService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ImportUtil {

    public static void importWordsFromForm(String wordsLine, int sectionId, int userId, int dictionaryId) {
        List<Word> wordsList = splitInWordsList(wordsLine, sectionId);
        if (wordsList.size() != 0) {
            WordService wordService = new WordService();
            wordService.saveList(wordsList, userId, dictionaryId);
        }
    }

    private static List<Word> splitInWordsList(String wordsLine, int sectionId) {
        return Arrays.stream(wordsLine.split(";"))
                .filter(str -> str.contains(" - "))
                .map(str -> str.replaceAll("\n", " "))
                .map(str -> {
                    String[] pair = str.split(" - ");
                    return new Word(sectionId, pair[0].trim(), pair[1].trim());
                }).collect(Collectors.toList());
    }

    public static void importWordsFromCSV(BufferedReader reader, int dictionaryId, int userId) throws IOException {
        String row;
        String[] columns = reader.readLine().split(";");
        List<Word> words = new ArrayList<>();
        Map<String, Integer> sections = new HashMap<>();
        SectionService sectionService = new SectionService();
        WordService wordService = new WordService();

        if (columns[0].equals("section") && columns[1].equals("word") && columns[2].equals("translation")) {
            while ((row = reader.readLine()) != null) {
                columns = row.split(";");
                int sectionId;
                if (!sections.containsKey(columns[0])) {
                    Section section = sectionService.create(columns[0], dictionaryId, userId);
                    sections.put(section.getName(), section.getId());
                    sectionId = section.getId();
                } else {
                    sectionId = sections.get(columns[0]);
                }
                Word word = new Word(sectionId, columns[1], columns[2]);
                words.add(word);
            }
        }

        if (!words.isEmpty()) {
            wordService.saveList(words, userId, dictionaryId);
        }
    }

    private ImportUtil() {
    }
}
