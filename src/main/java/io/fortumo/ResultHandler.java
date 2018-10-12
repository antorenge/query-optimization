package io.fortumo;

@FunctionalInterface
public interface ResultHandler {
    void handleResult(QueryResult queryResult);
}
