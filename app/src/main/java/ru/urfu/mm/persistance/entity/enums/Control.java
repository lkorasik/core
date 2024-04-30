package ru.urfu.mm.persistance.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import ru.urfu.mm.domain.enums.ControlTypes;

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

    public static Control fromDomain(ControlTypes controlTypes) {
        return switch (controlTypes) {
            case TEST -> TEST;
            case EXAM -> EXAM;
        };
    }
}
