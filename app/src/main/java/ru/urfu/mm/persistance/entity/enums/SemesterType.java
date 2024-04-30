package ru.urfu.mm.persistance.entity.enums;

/**
 * Тип семестра
 */
public enum SemesterType {
    /**
     * Осенний
     */
    FALL,
    /**
     * Весенний
     */
    SPRING;

    public static SemesterType fromDomain(ru.urfu.mm.domain.enums.SemesterType semesterType) {
        return switch (semesterType) {
            case FALL -> FALL;
            case SPRING -> SPRING;
        };
    }

    public static ru.urfu.mm.domain.enums.SemesterType toDomain(SemesterType semesterType) {
        return switch (semesterType) {
            case FALL -> ru.urfu.mm.domain.enums.SemesterType.FALL;
            case SPRING -> ru.urfu.mm.domain.enums.SemesterType.SPRING;
        };
    }
}
