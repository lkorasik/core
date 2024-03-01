package ru.urfu.mm.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Control {
    TEST("Test", "Зачет"),
    EXAM("Exam", "Экзамен");

    private String value;
    private String documentaryValue;

    Control(String value, String documentaryValue) {
        this.value = value;
        this.documentaryValue = documentaryValue;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public String getDocumentaryValue() {
        return documentaryValue;
    }
}
