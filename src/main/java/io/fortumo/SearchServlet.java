package io.fortumo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet {

    private SearchEngine searchEngine;

    @Override
    public void init() throws ServletException {
        super.init();
        this.searchEngine = new SearchEngine();
        this.searchEngine.setDbHelper(new DbHelper());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logRequestStart(req);
        final String merchant = req.getParameter("merchant");
        final String msisdn = req.getParameter("msisdn");
        final String country = req.getParameter("country");
        final String operator = req.getParameter("operator");

        final String searchResult = this.searchEngine.search(merchant, msisdn, country, operator);

        if (searchResult != null) {
            resp.setContentLength(searchResult.getBytes().length);
            resp.getWriter().write(searchResult);
        }

        logRequestEnd(req);
    }

    private void logRequestEnd(HttpServletRequest req) {
        this.log("Request end " + req + " from " + req.getRemoteAddr() + ":" + req.getRemotePort());
    }

    private void logRequestStart(HttpServletRequest req) {
        this.log("Request start " + req + " from " + req.getRemoteAddr() + ":" + req.getRemotePort());
    }
}
