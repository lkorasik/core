package ru.urfu.mm.application.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.application.usecase.GetAllCourses;
import ru.urfu.mm.domain.Control;
import ru.urfu.mm.domain.Module;
import ru.urfu.mm.domain.SpecialCourse;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class GetAllCoursesTest {
    @Mock
    private CourseGateway courseGateway;

    @Test
    public void test_getAllCourses() {
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

        Mockito.when(courseGateway.getAllCourses()).thenReturn(specialCourses);

        List<SpecialCourse> expected = List.of(
                new SpecialCourse(
                        specialCourses.get(0).getId(),
                        specialCourses.get(0).getName(),
                        specialCourses.get(0).getCreditsCount(),
                        specialCourses.get(0).getControl(),
                        specialCourses.get(0).getDescription(),
                        specialCourses.get(0).getDepartment(),
                        specialCourses.get(0).getTeacherName(),
                        specialCourses.get(0).getEducationalModule()
                ),
                new SpecialCourse(
                        specialCourses.get(1).getId(),
                        specialCourses.get(1).getName(),
                        specialCourses.get(1).getCreditsCount(),
                        specialCourses.get(1).getControl(),
                        specialCourses.get(1).getDescription(),
                        specialCourses.get(1).getDepartment(),
                        specialCourses.get(1).getTeacherName(),
                        specialCourses.get(1).getEducationalModule()
                ),
                new SpecialCourse(
                        specialCourses.get(2).getId(),
                        specialCourses.get(2).getName(),
                        specialCourses.get(2).getCreditsCount(),
                        specialCourses.get(2).getControl(),
                        specialCourses.get(2).getDescription(),
                        specialCourses.get(2).getDepartment(),
                        specialCourses.get(2).getTeacherName(),
                        specialCourses.get(2).getEducationalModule()
                ),
                new SpecialCourse(
                        specialCourses.get(3).getId(),
                        specialCourses.get(3).getName(),
                        specialCourses.get(3).getCreditsCount(),
                        specialCourses.get(3).getControl(),
                        specialCourses.get(3).getDescription(),
                        specialCourses.get(3).getDepartment(),
                        specialCourses.get(3).getTeacherName(),
                        specialCourses.get(3).getEducationalModule()
                ),
                new SpecialCourse(
                        specialCourses.get(4).getId(),
                        specialCourses.get(4).getName(),
                        specialCourses.get(4).getCreditsCount(),
                        specialCourses.get(4).getControl(),
                        specialCourses.get(4).getDescription(),
                        specialCourses.get(4).getDepartment(),
                        specialCourses.get(4).getTeacherName(),
                        specialCourses.get(4).getEducationalModule()
                ),
                new SpecialCourse(
                        specialCourses.get(5).getId(),
                        specialCourses.get(5).getName(),
                        specialCourses.get(5).getCreditsCount(),
                        specialCourses.get(5).getControl(),
                        specialCourses.get(5).getDescription(),
                        specialCourses.get(5).getDepartment(),
                        specialCourses.get(5).getTeacherName(),
                        specialCourses.get(5).getEducationalModule()
                ),
                new SpecialCourse(
                        specialCourses.get(6).getId(),
                        specialCourses.get(6).getName(),
                        specialCourses.get(6).getCreditsCount(),
                        specialCourses.get(6).getControl(),
                        specialCourses.get(6).getDescription(),
                        specialCourses.get(6).getDepartment(),
                        specialCourses.get(6).getTeacherName(),
                        specialCourses.get(6).getEducationalModule()
                ),
                new SpecialCourse(
                        specialCourses.get(7).getId(),
                        specialCourses.get(7).getName(),
                        specialCourses.get(7).getCreditsCount(),
                        specialCourses.get(7).getControl(),
                        specialCourses.get(7).getDescription(),
                        specialCourses.get(7).getDepartment(),
                        specialCourses.get(7).getTeacherName(),
                        specialCourses.get(7).getEducationalModule()
                ),
                new SpecialCourse(
                        specialCourses.get(8).getId(),
                        specialCourses.get(8).getName(),
                        specialCourses.get(8).getCreditsCount(),
                        specialCourses.get(8).getControl(),
                        specialCourses.get(8).getDescription(),
                        specialCourses.get(8).getDepartment(),
                        specialCourses.get(8).getTeacherName(),
                        specialCourses.get(8).getEducationalModule()
                ),
                new SpecialCourse(
                        specialCourses.get(9).getId(),
                        specialCourses.get(9).getName(),
                        specialCourses.get(9).getCreditsCount(),
                        specialCourses.get(9).getControl(),
                        specialCourses.get(9).getDescription(),
                        specialCourses.get(9).getDepartment(),
                        specialCourses.get(9).getTeacherName(),
                        specialCourses.get(9).getEducationalModule()
                )
        );

        GetAllCourses getAllCourses = new GetAllCourses(courseGateway);

        List<SpecialCourse> allCourses = getAllCourses.getAllCourses();

        Assertions.assertEquals(expected.size(), allCourses.size());
        allCourses.forEach(x -> Assertions.assertTrue(expected.contains(x)));
    }

}
