package com.springbalaji.odd.dto;

public class FinalQueryRequest {
    private String finalQuery;

    public FinalQueryRequest(String finalQuery) {
        this.finalQuery = finalQuery;
    }

    public String getFinalQuery() {
        return finalQuery;
    }
}
