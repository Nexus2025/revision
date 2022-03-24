package com.revision.google.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import com.revision.google.entity.SheetTO;
import com.revision.google.util.OAuthUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class GoogleDriveService{

    private static final Logger logger = LoggerFactory.getLogger(GoogleDriveService.class);

    private static String TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static String TYPE_SPREADSHEET = "application/vnd.google-apps.spreadsheet";

    private Drive driveService = null;

    public List<SheetTO> getSheets(Credential credential) {
        logger.info("getSheets");
        List<SheetTO> sheets = new ArrayList<>();
        String pageToken = null;
        do {
            try {
                FileList result = getDriveService(credential).files().list()
                        .setQ("trashed=false")
                        .setQ("'me' in owners")
                        .setFields("nextPageToken, files(id, name, mimeType)")
                        .setPageSize(100)
                        .setPageToken(pageToken)
                        .execute();

                for (File file : result.getFiles()) {
                    if(file.getMimeType().equals(TYPE_SPREADSHEET)){
                        sheets.add(new SheetTO(file.getId(), file.getName(), SheetTO.Extension.SPREADSHEET));
                    }
                }
                pageToken = result.getNextPageToken();
            } catch (GeneralSecurityException | IOException e) {
                logger.error(e.getMessage());
            }
        } while (pageToken != null);

        return sheets;
    }

    private Drive getDriveService(Credential credential) throws GeneralSecurityException, IOException {
        logger.info("getDriveService");

        if(driveService == null) {
            driveService = new Drive.Builder(OAuthUtil.HTTP_TRANSPORT, OAuthUtil.JSON_FACTORY, credential)
                    .setApplicationName("revision")
                    .build();
        }
        return driveService;
    }
}
