package ru.javawebinar.topjava.util.exception;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ErrorInfo {
    private final String url;
    private final ErrorType type;
    private final String code;
    private List<String> details;

    @ConstructorProperties({"url", "type", "details"})
    public ErrorInfo(CharSequence url, ErrorType type, String... details) {
        this.url = url.toString();
        this.type = type;
        this.code = type.getCode();
        this.details = new ArrayList<>(List.of(details));
    }

    public void addDetail(String detail) {
        details.add(detail);
    }

    public void setDetails(String... details) {
        this.details = new ArrayList<>(List.of(details));
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