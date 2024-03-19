package ru.javawebinar.topjava.util.exception;

public enum ErrorType {
    APP_ERROR("errorInfo.app"),
    DATA_NOT_FOUND("errorInfo.dataNotFound"),
    DATA_ERROR("errorInfo.data"),
    VALIDATION_ERROR("errorInfo.validation");

    private final String code;
    ErrorType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
