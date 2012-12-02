package com.ogs.datastore;

public class Query {
    private final String value;
    private final ChunkedString chunks;

    private Query() {}

    public Query(String value) {
        this.value = value;
        this.chunks = new ChunkedString(value);
    }

    public String getValue() {
        return this.value;
    }
}
