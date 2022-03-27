package com.revision.google.oauth2;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeCallbackServlet;
import com.revision.google.util.OAuthUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/oauth2callback")
public class GoogleCallbackServlet extends AbstractAuthorizationCodeCallbackServlet {
    private static final Logger logger = LoggerFactory.getLogger(GoogleCallbackServlet.class);

    @Override
    protected void onSuccess(HttpServletRequest request, HttpServletResponse response, Credential credential)
            throws ServletException, IOException {
        logger.info("onSuccess");

        HttpSession session = request.getSession();
        session.setAttribute("credential", credential);
        session.setAttribute("access_token", credential.getAccessToken());
        session.setAttribute("refreshToken", credential.getRefreshToken());
        response.sendRedirect(OAuthUtil.getSourceUrl());
    }

    @Override
    protected void onError(
            HttpServletRequest request, HttpServletResponse response, AuthorizationCodeResponseUrl errorResponse)
            throws ServletException, IOException {
        logger.info("onError");
        response.sendRedirect("/error");
    }

    @Override
    protected String getRedirectUri(HttpServletRequest request) throws ServletException, IOException {
        logger.info("getRedirectUri");
        return OAuthUtil.getRedirectUri(request);
    }

    @Override
    protected String getUserId(HttpServletRequest request) throws ServletException, IOException {
        String userId = "user" + request.getSession().getId();
        logger.info("getUserId: {}", userId);
        return userId;
    }

    @Override
    protected AuthorizationCodeFlow initializeFlow() throws ServletException, IOException {
        logger.info("initializeFlow");
        return OAuthUtil.newFlow();
    }
}
