package io.fortumo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class DbHelper {


    public Connection getDbConnection() throws Exception {
        final String url = "jdbc:postgresql://127.0.0.1/fortumo_test_task";
        final Properties props = new Properties();
        props.setProperty("user","fortumo_test_user");
        props.setProperty("password","fortumo_test_password");
        props.setProperty("ssl","false");
        final Connection conn = DriverManager.getConnection(url, props);

        return conn;
    }

    public void runQuery(ResultHandler handler,String query, String... params) {
        try (final Connection connection = this.getDbConnection()) {
            final PreparedStatement prepared = connection.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                prepared.setString(i + 1, params[i]);
            }
            final ResultSet result = prepared.executeQuery();
            handler.handleResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
