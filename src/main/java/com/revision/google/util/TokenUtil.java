package com.revision.google.util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;

public class TokenUtil {

    public static boolean isTokenExpired(Credential credential) {
        return credential.getExpiresInSeconds() < 0;
    }

    public static String refreshAccessToken(String refreshToken) throws IOException {
        String clientId = OAuthUtil.getClientSecrets().getDetails().getClientId();
        String clientSecret = OAuthUtil.getClientSecrets().getDetails().getClientSecret();

        TokenResponse response = new GoogleRefreshTokenRequest(
                new NetHttpTransport(),
                new JacksonFactory(), refreshToken, clientId, clientSecret)
                .execute();

        return response.getAccessToken();
    }
}
