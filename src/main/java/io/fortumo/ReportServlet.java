package io.fortumo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReportServlet extends HttpServlet {

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
        final String startDate = req.getParameter("start_date");
        final String endDate = req.getParameter("end_date");

        final String reportResult = this.searchEngine.report(merchant, startDate, endDate);

        if (reportResult != null) {
            resp.setContentLength(reportResult.getBytes().length);
            resp.getWriter().write(reportResult);
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
