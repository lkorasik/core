package ru.urfu.mm.persistance.entity.enums;

public enum Years {
    FIRST,
    SECOND;

    public static Years fromDomain(ru.urfu.mm.domain.enums.Years years) {
        return switch (years) {
            case FIRST -> FIRST;
            case SECOND -> SECOND;
        };
    }

    public static ru.urfu.mm.domain.enums.Years toDomain(Years years) {
        return switch (years) {
            case FIRST -> ru.urfu.mm.domain.enums.Years.FIRST;
            case SECOND -> ru.urfu.mm.domain.enums.Years.SECOND;
        };
    }
}
