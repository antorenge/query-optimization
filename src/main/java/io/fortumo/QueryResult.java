package io.fortumo;

import java.io.Serializable;
import java.util.List;

public final class QueryResult implements Serializable {

    private final int columnCount;
    private final List<Object[]> rows;

    public QueryResult(final int columnCount, final List<Object[]> rows) {
        this.columnCount = columnCount;
        this.rows = rows;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public List<Object[]> getRows() {
        return rows;
    }

}