package com.github.bpark.streamwatch;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("serial")
@WebServlet(name = "Config", urlPatterns = "/config/*")
public class ConfigServlet extends HttpServlet {

    private static final String CONFIG_TEMPLATE = "{\n" +
            "  \"streamingUrl\": \"%s://%s:%s/streams\",\n" +
            "  \"connectMethod\": \"WS\"\n" +
            "}";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String scheme = request.getScheme();             // http
        String serverName = request.getServerName();     // hostname.com
        String contextPath = request.getContextPath();   // /mywebapp
        int port = request.getServerPort();

        String config = String.format(CONFIG_TEMPLATE, scheme, serverName, port);

        response.addHeader("Content-Type", "application/json");
        response.getWriter().println(config);
    }
}
