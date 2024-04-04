package ru.urfu.mm.domain;

public enum Control {
    TEST("Test", "Зачет"),
    EXAM("Exam", "Экзамен");

    private String value;
    private String documentaryValue;

    Control(String value, String documentaryValue) {
        this.value = value;
        this.documentaryValue = documentaryValue;
    }

    public String getValue() {
        return value;
    }

    public String getDocumentaryValue() {
        return documentaryValue;
    }
}
