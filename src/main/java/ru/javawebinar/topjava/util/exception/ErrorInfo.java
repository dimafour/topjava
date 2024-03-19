package ru.javawebinar.topjava.util.exception;

import java.util.ArrayList;
import java.util.List;

public class ErrorInfo {
    private final String url;
    private final ErrorType type;
    private final String code;
    private List<String> details;

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
}