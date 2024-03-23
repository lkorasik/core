package ru.urfu.mm.application.dsl;

import ru.urfu.mm.domain.AcademicYear;
import ru.urfu.mm.domain.Program;
import ru.urfu.mm.domain.Semester;
import ru.urfu.mm.domain.SemesterType;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class EntityDSL {
    public static String generateString() {
        return String.valueOf(UUID.randomUUID());
    }

    public static Program generateProgram() {
        return new Program(generateString());
    }

    public static Semester createFallSemester(int year) {
        return new Semester(new AcademicYear(year), SemesterType.FALL);
    }

    public static Semester createSpringSemester(int year) {
        return new Semester(new AcademicYear(year - 1), SemesterType.SPRING);
    }

    public static List<Semester> createYear(int year) {
        AcademicYear firstYear = new AcademicYear(year);
        AcademicYear secondYear = new AcademicYear(year + 1);
        return List.of(
                new Semester(firstYear, SemesterType.FALL),
                new Semester(firstYear, SemesterType.SPRING),
                new Semester(secondYear, SemesterType.FALL),
                new Semester(secondYear, SemesterType.SPRING)
        );
    }
}
