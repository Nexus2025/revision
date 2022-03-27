package com.revision.google.util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.DriveScopes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class OAuthUtil {
    private static final String CLIENT_SECRETS = "/client-secrets.json";

    private static GoogleClientSecrets clientSecrets;

    private static String sourceUrl = "/import-sheets";

    public static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE_READONLY);

    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static final java.io.File DATA_STORE_DIR = new java.io.File("tokens");

    public static FileDataStoreFactory DATA_STORE_FACTORY;

    public static HttpTransport HTTP_TRANSPORT;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static GoogleAuthorizationCodeFlow newFlow() throws IOException {
        return new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                getClientSecrets(), SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline").build();
    }

    public static String getSourceUrl() {
        return sourceUrl;
    }

    public static String getRedirectUri(HttpServletRequest req) {
        GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath("/oauth2callback");
        return url.build();
    }

    private static InputStream getSecretFile() throws IOException {
        return OAuthUtil.class.getResourceAsStream(CLIENT_SECRETS);
    }

    public static GoogleClientSecrets getClientSecrets() {
        if (clientSecrets == null) {
            try {
                InputStreamReader clientSecretsReader = new InputStreamReader(getSecretFile());
                clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, clientSecretsReader);

                Preconditions.checkArgument(
                        !clientSecrets.getDetails().getClientId().startsWith("Enter ") &&
                                !clientSecrets.getDetails().getClientSecret().startsWith("Enter "),
                        "Download client_secrets.json file");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return clientSecrets;
    }
}
