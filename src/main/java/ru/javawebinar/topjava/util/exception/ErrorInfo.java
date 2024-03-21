package ru.javawebinar.topjava.util.exception;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Objects;

public class ErrorInfo {
    private final String url;
    private final ErrorType type;
    private final String code;
    private final List<String> details;

    @ConstructorProperties({"url", "type", "details"})
    public ErrorInfo(CharSequence url, ErrorType type, String... details) {
        this.url = url.toString();
        this.type = type;
        this.code = type.getCode();
        this.details = List.of(details);
    }

    public List<String> getDetails() {
        return details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorInfo errorInfo = (ErrorInfo) o;
        return Objects.equals(url, errorInfo.url) &&
               type == errorInfo.type &&
               Objects.equals(code, errorInfo.code) &&
               Objects.equals(details, errorInfo.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, type, code, details);
    }
}