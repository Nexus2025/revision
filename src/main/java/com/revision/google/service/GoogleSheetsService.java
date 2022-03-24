package com.revision.google.service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.revision.google.util.OAuthUtil;

import java.io.IOException;
import java.util.List;

public class GoogleSheetsService {

    String sheetName = "Лист1!A:A";

    private Sheets sheetsService = null;

    public List<List<Object>> readTable(String spreadsheetId)  throws IOException {
        Sheets service = getSheetsService();
        return readTable(service, spreadsheetId, sheetName);
    }

    private List<List<Object>> readTable(Sheets service, String spreadsheetId, String sheetName)  throws IOException {
        ValueRange table = service.spreadsheets().values().get(spreadsheetId, sheetName).execute();

        List<List<Object>> values = table.getValues();
        printTable(values);

        return values;
    }

    private void printTable(List<List<Object>> values) {
        if (values == null || values.size() == 0) {
            System.out.println("No data found.");
        }

        else {
            System.out.println("read data");
            for (List<Object> row : values) {
                for (int c = 0; c < row.size(); c++) {
                    System.out.printf("%s ", row.get(c));
                }
                System.out.println();
            }
        }
    }

    private Sheets getSheetsService() throws IOException {
        if (sheetsService == null) {
            sheetsService = new Sheets.Builder(OAuthUtil.HTTP_TRANSPORT, OAuthUtil.JSON_FACTORY, OAuthUtil.getCredentials())
                    .setApplicationName("revision")
                    .build();
        }
        return sheetsService;
    }
}
