package com.danielflower.apprunner;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class ApprunnerUrlNormalisationRedirector extends AbstractHandler {
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String proto = request.getHeader("X-Forwarded-Proto");
        if ("http".equalsIgnoreCase(proto) || "www.apprunner.co.nz".equalsIgnoreCase(request.getHeader("X-Forwarded-Host"))) {
            String url = "https://apprunner.co.nz" + request.getRequestURI();
            String qs = request.getQueryString();
            if (StringUtils.isNotBlank(qs)) {
                url += "?" + qs;
            }
            baseRequest.setHandled(true);
            response.sendRedirect(url);
        }
    }
}
