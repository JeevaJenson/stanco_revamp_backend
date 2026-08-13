package com.stanco.enums;

public enum Status {

    active("1"),
    inactive("0");

    private final String code;

    Status(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}