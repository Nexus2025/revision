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
    protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
            throws ServletException, IOException {
        logger.info("onSuccess");

        OAuthUtil.setCredential(credential);

        HttpSession session = req.getSession();
        session.setAttribute("access_token", credential.getAccessToken());
        session.setAttribute("credential", credential);
        session.setAttribute("refreshToken", credential.getRefreshToken());
        resp.sendRedirect(OAuthUtil.getSourceUrl());
    }

    @Override
    protected void onError(
            HttpServletRequest req, HttpServletResponse resp, AuthorizationCodeResponseUrl errorResponse)
            throws ServletException, IOException {
        logger.info("onError");
        resp.sendRedirect("/error");
    }

    @Override
    protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
        logger.info("getRedirectUri");
        return OAuthUtil.getRedirectUri(req);
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
