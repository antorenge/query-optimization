package io.fortumo;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class SearchEngine {

    private DbHelper dbHelper;

    public String search(String merchant, String msisdn, String country, String operator) {
        final AtomicReference<String> theResult = new AtomicReference<>();
        this.dbHelper.runQuery((r) -> {
            try {
                r.next();
                final String id = r.getString("id");
                final String createdAt = r.getTimestamp("created_at").toLocalDateTime().toString();
                theResult.set("Found record " + id + " created at " + createdAt + "\n");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, "SELECT * FROM payments "
                + "WHERE merchant_uuid = COALESCE(?, merchant_uuid) "
                + "AND msisdn = COALESCE(?, msisdn) "
                + "AND country_code = COALESCE(?, country_code) "
                + "AND operator_code = COALESCE(?, operator_code) "
                        + " LIMIT 1",
                merchant,
                msisdn,
                country,
                operator);

        return theResult.get();
    }

    public void setDbHelper(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
}
