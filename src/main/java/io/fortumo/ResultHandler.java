package io.fortumo;

import java.sql.ResultSet;

@FunctionalInterface
public interface ResultHandler {
    public void handleResult(ResultSet resultSet);
}
