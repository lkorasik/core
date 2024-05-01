package ru.urfu.mm.domain.enums;

/**
 * Тип итоговой проверки знаний.
 */
public enum ControlTypes {
    TEST("Test", "Зачет"),
    EXAM("Exam", "Экзамен");

    private final String value;
    private final String documentaryValue;

    ControlTypes(String value, String documentaryValue) {
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
