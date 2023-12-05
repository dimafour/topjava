package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
        request.getRequestDispatcher("users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String value = request.getParameter("profile");
        if (value.equals("admin")) {
            SecurityUtil.setAuthUserId(1);
            log.info("profile set to admin (userId = 1)");
        }
        if (value.equals("user")) {
            SecurityUtil.setAuthUserId(2);
            log.info("profile set to user (userId = 2)");
        }
        request.getRequestDispatcher("index.html").forward(request, response);
    }
}


