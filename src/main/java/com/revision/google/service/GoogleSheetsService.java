package com.revision.google.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.revision.entity.Section;
import com.revision.entity.Word;
import com.revision.google.util.OAuthUtil;
import com.revision.service.SectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoogleSheetsService {

    private static final Logger logger = LoggerFactory.getLogger(GoogleSheetsService.class);

    public List<Word> getWords(int userId, int dictionaryId, String spreadsheetId, Credential credential) {
        List<Word> words = new ArrayList<>();
        Map<String, Integer> sections = new HashMap<>();
        SectionService sectionService = new SectionService();

        try {
            List<List<Object>> values = readTable(spreadsheetId, credential);

            if (values.get(0).get(0).equals("section")
                    && values.get(0).get(1).equals("word")
                    && values.get(0).get(2).equals("translation")) {

                for (int i = 1; i < values.size(); i++) {
                    List<Object> row = values.get(i);

                    int sectionId;
                    if (!sections.containsKey(row.get(0))) {
                        Section section = sectionService.create((String)row.get(0), dictionaryId, userId);
                        sections.put(section.getName(), section.getId());
                        sectionId = section.getId();

                    } else {
                        sectionId = sections.get(row.get(0));
                    }
                    Word word = new Word(sectionId, (String) row.get(1), (String) row.get(2));
                    words.add(word);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        logger.info("getWords | word's size = {}", words.size());
        return words;
    }

    private List<List<Object>> readTable(String spreadsheetId, Credential credential) throws IOException {
        Sheets service = getSheetsService(credential);
        ValueRange table = service.spreadsheets().values().get(spreadsheetId, "A:C").execute();

        return table.getValues();
    }

    private Sheets getSheetsService(Credential credential) {

        return new Sheets.Builder(OAuthUtil.HTTP_TRANSPORT, OAuthUtil.JSON_FACTORY, credential)
                .setApplicationName("revision")
                .build();
    }
}
