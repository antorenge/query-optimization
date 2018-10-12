package io.fortumo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.cacheonix.cache.Cache;
import org.cacheonix.Cacheonix;

public class DbHelper {

    public Connection getDbConnection() throws Exception {
        final String url = "jdbc:postgresql://127.0.0.1/fortumotest";
        final Properties props = new Properties();
        props.setProperty("user", "fortumo_test_user");
        props.setProperty("password", "fortumo_test_password");
        props.setProperty("ssl", "false");
        final Connection conn = DriverManager.getConnection(url, props);

        return conn;
    }

    public void runSearchQuery(ResultHandler handler, String query, String... params) {
        Connection connection = null;
        PreparedStatement prepared = null;
        List<String> queryParameters = Arrays.asList(params);
        final QueryKey queryKey = new QueryKey(query, queryParameters);

        try {
            connection = this.getDbConnection();
            prepared = connection.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                prepared.setString(i + 1, params[i]);
            }

            runQuery(connection, prepared, handler, queryKey);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void runReportQuery(ResultHandler handler, String queryText, String merchant, String startDate,
            String endDate) {
        Connection connection = null;
        PreparedStatement prepared = null;
        List<String> queryParameters = new ArrayList<>();
        queryParameters.add(merchant);
        queryParameters.add(startDate);
        queryParameters.add(endDate);
        final QueryKey queryKey = new QueryKey(queryText, queryParameters);

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date1 = LocalDate.parse(startDate, formatter);
            LocalDate date2 = LocalDate.parse(endDate, formatter);
            if (date1.equals(date2))
                date2 = date1.plusDays(1);
            Timestamp sDate = Timestamp.valueOf(date1.atStartOfDay());
            Timestamp eDate = Timestamp.valueOf(date2.atStartOfDay());

            connection = this.getDbConnection();
            prepared = connection.prepareStatement(queryText);

            // Set query parameters
            prepared.setString(1, merchant);
            prepared.setTimestamp(2, sDate);
            prepared.setTimestamp(3, eDate);

            runQuery(connection, prepared, handler, queryKey);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void runQuery(Connection connection, PreparedStatement prepared, ResultHandler handler, QueryKey queryKey) {
        // Get results from cache
        final Cache<QueryKey, QueryResult> queryCache = Cacheonix.getInstance().getCache("query.cache");
        QueryResult queryResult = (QueryResult) queryCache.get(queryKey);
        ResultSet result = null;

        if (queryResult == null) {
            // Not in cache, get the result from the database
            try {
                final List<Object[]> rows = new ArrayList<Object[]>();
                // Execute the statement and retrieve the result
                result = prepared.executeQuery();
                final int columnCount = result.getMetaData().getColumnCount();

                while (result.next()) {
                    final Object[] row = new Object[columnCount];

                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        row[columnIndex - 1] = result.getObject(columnIndex);
                    }

                    rows.add(row);
                }

                // Create query result
                queryResult = new QueryResult(columnCount, rows);

                // Put the result to cache
                queryCache.put(queryKey, queryResult);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    // Close result set
                    if (result != null) {
                        result.close();
                    }

                    // Close prepared statement
                    if (prepared != null) {
                        prepared.close();
                    }

                    // Close connection
                    if (connection != null) {
                        connection.close();
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        handler.handleResult(queryResult);

    }

}
