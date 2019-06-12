package com.intuit.account.response;

public enum ErrorStatus {
    SUCCESS,
    PARTIAL,
    FAILED,
    UNPROCESSED,
    CONFLICT;

    private ErrorStatus() {
    }

    public String value() {
        return this.name();
    }

    public static ErrorStatus fromValue(String v) {
        return valueOf(v);
    }
}