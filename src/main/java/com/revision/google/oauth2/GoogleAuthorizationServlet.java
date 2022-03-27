package com.revision.google.oauth2;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;

import com.revision.google.util.OAuthUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebServlet("/oauth2ask")
public class GoogleAuthorizationServlet extends AbstractAuthorizationCodeServlet {
    private static final Logger logger = LoggerFactory.getLogger(GoogleAuthorizationServlet.class);

    @Override
    protected String getRedirectUri(HttpServletRequest request) throws ServletException, IOException {
        logger.info("getRedirectUri");
        return OAuthUtil.getRedirectUri(request);
    }

    @Override
    protected String getUserId(HttpServletRequest request) throws ServletException, IOException {
        String userId = "user" + request.getSession().getId();
        logger.info("getUserId: userId = {}", userId);
        return userId;
    }

    @Override
    protected AuthorizationCodeFlow initializeFlow() throws ServletException, IOException {
        logger.info("initializeFlow");
        return OAuthUtil.newFlow();
    }
}
