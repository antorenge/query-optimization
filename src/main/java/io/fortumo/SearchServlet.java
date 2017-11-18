package io.fortumo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logRequestStart(req);
        resp.getWriter().write("Here be search results");
        logRequestEnd(req);
    }

    private void logRequestEnd(HttpServletRequest req) {
        this.log("Request end " + req + " from " + req.getRemoteAddr() + ":" + req.getRemotePort());
    }

    private void logRequestStart(HttpServletRequest req) {
        this.log("Request start " + req + " from " + req.getRemoteAddr() + ":" + req.getRemotePort());
    }
}
