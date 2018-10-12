package io.fortumo;

import java.util.concurrent.atomic.AtomicReference;

public class SearchEngine {

    private DbHelper dbHelper;

    public String search(String merchant, String msisdn, String country, String operator) {
        final AtomicReference<String> theResult = new AtomicReference<>();
        this.dbHelper.runSearchQuery((qr) -> {
            for (int i = 0; i < qr.getRows().size(); i++) {
                Object[] row = qr.getRows().get(i);
                String id = row[0].toString();
                String createdAt = row[1].toString();
                theResult.set("Found record " + id + " created at " + createdAt + "\n");
            }
        }, "SELECT * FROM payments "
                + "WHERE merchant_uuid = COALESCE(?, merchant_uuid) "
                + "AND msisdn = COALESCE(?, msisdn) "
                + "AND country_code = COALESCE(?, country_code) "
                + "AND operator_code = COALESCE(?, operator_code) "
                + " LIMIT 1", merchant, msisdn, country, operator);

        return theResult.get();

    }

    public String report(String merchant, String startDate, String endDate) {
        final AtomicReference<String> theResult = new AtomicReference<>();

        this.dbHelper.runReportQuery((qr) -> {
            final int rowsCount = qr.getRows().size();
            theResult.set("Found " + rowsCount + " records \n");
        }, "SELECT * FROM payments "
                + "WHERE merchant_uuid = COALESCE(?, merchant_uuid) "
                + "AND created_at >= ?"
                + "AND created_at < ?", merchant, startDate, endDate);

        return theResult.get();

    }

    public void setDbHelper(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
}
