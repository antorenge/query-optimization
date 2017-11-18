package io.fortumo;

import java.sql.ResultSet;

@FunctionalInterface
public interface ResultHandler {
    void handleResult(ResultSet resultSet);
}
