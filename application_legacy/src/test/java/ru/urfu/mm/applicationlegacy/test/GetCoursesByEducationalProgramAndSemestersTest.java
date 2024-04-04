package ru.urfu.mm.applicationlegacy.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.mm.applicationlegacy.dsl.DSL;
import ru.urfu.mm.applicationlegacy.gateway.CourseGateway;
import ru.urfu.mm.applicationlegacy.gateway.StudentGateway;
import ru.urfu.mm.applicationlegacy.usecase.CourseForEducationalProgram;
import ru.urfu.mm.applicationlegacy.usecase.GetCoursesByEducationalProgramAndSemesters;
import ru.urfu.mm.domainlegacy.Module;
import ru.urfu.mm.domainlegacy.*;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class GetCoursesByEducationalProgramAndSemestersTest {
    @Mock
    private CourseGateway courseGateway;
    @Mock
    private StudentGateway studentGateway;

    /**
     * Сломал тест во время переноса
     *
     * Добавляем в систему курсы. Первые шесть курсов идут для первого направления. Остальные четыре курса идут для
     * второго направления.
     */
    @Disabled
    @Test
    public void test_getCoursesByEducationalProgramAndSemesters() {
        List<Semester> semesters = List.of(
                new Semester(UUID.randomUUID(), 2023, 1),
                new Semester(UUID.randomUUID(), 2024, 2),
                new Semester(UUID.randomUUID(), 2024, 3),
                new Semester(UUID.randomUUID(), 2025, 4)
        );
        EducationalProgram educationalProgram = new EducationalProgram(
                UUID.randomUUID(),
                DSL.generateString(),
                DSL.generateString(),
                DSL.generateString()
        );
        EducationalProgram fakeEducationalProgram = new EducationalProgram(
                UUID.randomUUID(),
                DSL.generateString(),
                DSL.generateString(),
                DSL.generateString()
        );

        List<Module> modules = List.of(
                new Module(UUID.randomUUID(), "M1"),
                new Module(UUID.randomUUID(), "M2"),
                new Module(UUID.randomUUID(), "M3"),
                new Module(UUID.randomUUID(), "M4")
        );

        List<SpecialCourse> specialCourses = List.of(
                new SpecialCourse(UUID.randomUUID(), "C1", 3, Control.TEST, "C1", "D1", "T1", modules.get(0)),
                new SpecialCourse(UUID.randomUUID(), "C2", 3, Control.TEST, "C2", "D2", "T2", modules.get(0)),
                new SpecialCourse(UUID.randomUUID(), "C3", 3, Control.TEST, "C3", "D3", "T3", modules.get(1)),
                new SpecialCourse(UUID.randomUUID(), "C4", 3, Control.TEST, "C4", "D4", "T4", modules.get(1)),
                new SpecialCourse(UUID.randomUUID(), "C5", 3, Control.TEST, "C5", "D5", "T5", modules.get(2)),
                new SpecialCourse(UUID.randomUUID(), "C6", 3, Control.TEST, "C6", "D6", "T6", modules.get(2)),
                new SpecialCourse(UUID.randomUUID(), "C7", 3, Control.TEST, "C7", "D7", "T7", modules.get(3)),
                new SpecialCourse(UUID.randomUUID(), "C8", 3, Control.TEST, "C8", "D8", "T8", modules.get(3)),
                new SpecialCourse(UUID.randomUUID(), "C9", 3, Control.TEST, "C9", "D9", "T9", modules.get(3)),
                new SpecialCourse(UUID.randomUUID(), "C10", 3, Control.TEST, "C10", "D10", "T10", modules.get(3))
        );

        List<EducationalProgramToCoursesWithSemesters> educationalProgramToCoursesWithSemesters = List.of(
                new EducationalProgramToCoursesWithSemesters(educationalProgram, semesters.get(0), specialCourses.get(0), true),
                new EducationalProgramToCoursesWithSemesters(educationalProgram, semesters.get(1), specialCourses.get(1), true),
                new EducationalProgramToCoursesWithSemesters(educationalProgram, semesters.get(1), specialCourses.get(2), true),
                new EducationalProgramToCoursesWithSemesters(educationalProgram, semesters.get(2), specialCourses.get(3), true),
                new EducationalProgramToCoursesWithSemesters(educationalProgram, semesters.get(2), specialCourses.get(4), true),
                new EducationalProgramToCoursesWithSemesters(educationalProgram, semesters.get(3), specialCourses.get(5), true),
                new EducationalProgramToCoursesWithSemesters(fakeEducationalProgram, semesters.get(0), specialCourses.get(6), true),
                new EducationalProgramToCoursesWithSemesters(fakeEducationalProgram, semesters.get(1), specialCourses.get(7), true),
                new EducationalProgramToCoursesWithSemesters(fakeEducationalProgram, semesters.get(2), specialCourses.get(8), true),
                new EducationalProgramToCoursesWithSemesters(fakeEducationalProgram, semesters.get(3), specialCourses.get(9), true)
        );

        Mockito.when(courseGateway.getEducationalProgramToCoursesWithSemestersByEducationalProgram(educationalProgram.getId())).thenReturn(educationalProgramToCoursesWithSemesters);

        List<UUID> semestersId = semesters
                .stream()
                .map(Semester::getId)
                .toList();

        GetCoursesByEducationalProgramAndSemesters getCoursesByEducationalProgramAndSemesters =
                new GetCoursesByEducationalProgramAndSemesters(courseGateway, studentGateway);
        List<CourseForEducationalProgram> result =
                getCoursesByEducationalProgramAndSemesters
                        .getCoursesByEducationalProgramAndSemesters(educationalProgram.getId(), semestersId);

        List<CourseForEducationalProgram> expected = List.of(
                new CourseForEducationalProgram(
                        UUID.randomUUID(),
                        specialCourses.get(0).getName(),
                        specialCourses.get(0).getCreditsCount(),
                        specialCourses.get(0).getControl(),
                        specialCourses.get(0).getDescription(),
                        List.of(semesters.get(0)),
                        modules.get(0).getId(),
                        semesters.get(0).getId()
                ),
                new CourseForEducationalProgram(
                        UUID.randomUUID(),
                        specialCourses.get(1).getName(),
                        specialCourses.get(1).getCreditsCount(),
                        specialCourses.get(1).getControl(),
                        specialCourses.get(1).getDescription(),
                        List.of(semesters.get(1)),
                        modules.get(0).getId(),
                        semesters.get(1).getId()
                ),
                new CourseForEducationalProgram(
                        UUID.randomUUID(),
                        specialCourses.get(2).getName(),
                        specialCourses.get(2).getCreditsCount(),
                        specialCourses.get(2).getControl(),
                        specialCourses.get(2).getDescription(),
                        List.of(semesters.get(1)),
                        modules.get(1).getId(),
                        semesters.get(1).getId()
                ),
                new CourseForEducationalProgram(
                        UUID.randomUUID(),
                        specialCourses.get(3).getName(),
                        specialCourses.get(3).getCreditsCount(),
                        specialCourses.get(3).getControl(),
                        specialCourses.get(3).getDescription(),
                        List.of(semesters.get(2)),
                        modules.get(1).getId(),
                        semesters.get(2).getId()
                ),
                new CourseForEducationalProgram(
                        UUID.randomUUID(),
                        specialCourses.get(4).getName(),
                        specialCourses.get(4).getCreditsCount(),
                        specialCourses.get(4).getControl(),
                        specialCourses.get(4).getDescription(),
                        List.of(semesters.get(2)),
                        modules.get(2).getId(),
                        semesters.get(2).getId()
                ),
                new CourseForEducationalProgram(
                        UUID.randomUUID(),
                        specialCourses.get(5).getName(),
                        specialCourses.get(5).getCreditsCount(),
                        specialCourses.get(5).getControl(),
                        specialCourses.get(5).getDescription(),
                        List.of(semesters.get(3)),
                        modules.get(2).getId(),
                        semesters.get(3).getId()
                )
        );

        Assertions.assertTrue(
                result
                        .stream()
                        .map(x -> expected.stream().map(y -> isEquals(x, y)))
                        .map(x -> x.reduce((y, z) -> y || z).get())
                        .reduce((x, y) -> java.util.Optional.of(x && y).get())
                        .get()
        );
    }

    private boolean isEquals(CourseForEducationalProgram first, CourseForEducationalProgram second) {
        return first.getName().equals(second.getName())
               && first.getCreditsCount() == second.getCreditsCount()
               && first.getControl().equals(second.getControl())
               && first.getDescription().equals(second.getDescription())
               && first.getEducationalModuleId().equals(second.getEducationalModuleId());
    }
}
