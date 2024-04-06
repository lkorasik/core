package ru.urfu.mm.application.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.mm.application.dsl.DSL;
import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.application.usecase.GetSelectedCoursesIds;
import ru.urfu.mm.domain.Module;
import ru.urfu.mm.domain.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class GetSelectedCoursesByStudentAndSemesterIdsTest {
    @Mock
    private CourseGateway courseGateway;

    @Test
    public void test_getSelectedCoursesIds() {
        List<Semester> semesters = List.of(
                new Semester(UUID.randomUUID(), 2023, 1),
                new Semester(UUID.randomUUID(), 2024, 2),
                new Semester(UUID.randomUUID(), 2024, 3),
                new Semester(UUID.randomUUID(), 2025, 4)
        );

        EducationalProgram educationalProgram = new EducationalProgram(UUID.randomUUID(), DSL.generateString(), DSL.generateString(), DSL.generateString());
        User user = new User(UUID.randomUUID(), "password", UserRole.STUDENT);
        Student student = new Student(UUID.randomUUID(), educationalProgram, "G1", user);

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

        List<SelectedCourses> selectedCourses = List.of(
                new SelectedCourses(UUID.randomUUID(), student, semesters.get(0), specialCourses.get(0)),
                new SelectedCourses(UUID.randomUUID(), student, semesters.get(1), specialCourses.get(1)),
                new SelectedCourses(UUID.randomUUID(), student, semesters.get(2), specialCourses.get(4)),
                new SelectedCourses(UUID.randomUUID(), student, semesters.get(3), specialCourses.get(5))
        );

        Mockito.when(courseGateway.getSelectedCourses(student.getLogin())).thenReturn(selectedCourses);

        GetSelectedCoursesIds getSelectedCoursesIds = new GetSelectedCoursesIds(courseGateway);
        Map<UUID, List<UUID>> map = getSelectedCoursesIds.getSelectedCoursesIds(student.getLogin(), semesters.stream().map(Semester::getId).toList());

        Assertions.assertEquals(map.size(), selectedCourses.size());
        Assertions.assertTrue(selectedCourses
                .stream()
                .map(SelectedCourses::getId)
                .map(map::containsKey)
                .reduce((x, y) -> x && y)
                .get());
    }
}
