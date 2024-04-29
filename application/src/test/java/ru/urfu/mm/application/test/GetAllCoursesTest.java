package ru.urfu.mm.application.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.mm.application.gateway.CourseGateway;
import ru.urfu.mm.application.usecase.GetAllCourses;
import ru.urfu.mm.domain.EducationalModule;
import ru.urfu.mm.domain.enums.ControlTypes;
import ru.urfu.mm.domain.Course;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class GetAllCoursesTest {
    @Mock
    private CourseGateway courseGateway;

    @Test
    public void test_getAllCourses() {
        List<EducationalModule> educationalModules = List.of(
                new EducationalModule(UUID.randomUUID(), "M1"),
                new EducationalModule(UUID.randomUUID(), "M2"),
                new EducationalModule(UUID.randomUUID(), "M3"),
                new EducationalModule(UUID.randomUUID(), "M4")
        );

        List<Course> specialCourses = List.of(
                new Course(UUID.randomUUID(), "C1", 3, ControlTypes.TEST, "C1", "D1", "T1", educationalModules.get(0)),
                new Course(UUID.randomUUID(), "C2", 3, ControlTypes.TEST, "C2", "D2", "T2", educationalModules.get(0)),
                new Course(UUID.randomUUID(), "C3", 3, ControlTypes.TEST, "C3", "D3", "T3", educationalModules.get(1)),
                new Course(UUID.randomUUID(), "C4", 3, ControlTypes.TEST, "C4", "D4", "T4", educationalModules.get(1)),
                new Course(UUID.randomUUID(), "C5", 3, ControlTypes.TEST, "C5", "D5", "T5", educationalModules.get(2)),
                new Course(UUID.randomUUID(), "C6", 3, ControlTypes.TEST, "C6", "D6", "T6", educationalModules.get(2)),
                new Course(UUID.randomUUID(), "C7", 3, ControlTypes.TEST, "C7", "D7", "T7", educationalModules.get(3)),
                new Course(UUID.randomUUID(), "C8", 3, ControlTypes.TEST, "C8", "D8", "T8", educationalModules.get(3)),
                new Course(UUID.randomUUID(), "C9", 3, ControlTypes.TEST, "C9", "D9", "T9", educationalModules.get(3)),
                new Course(UUID.randomUUID(), "C10", 3, ControlTypes.TEST, "C10", "D10", "T10", educationalModules.get(3))
        );

        Mockito.when(courseGateway.getAllCourses()).thenReturn(specialCourses);

        List<Course> expected = List.of(
                new Course(
                        specialCourses.get(0).getId(),
                        specialCourses.get(0).getName(),
                        specialCourses.get(0).getCredits(),
                        specialCourses.get(0).getControl(),
                        specialCourses.get(0).getDescription(),
                        specialCourses.get(0).getDepartment(),
                        specialCourses.get(0).getTeacher(),
                        specialCourses.get(0).getEducationalModule()
                ),
                new Course(
                        specialCourses.get(1).getId(),
                        specialCourses.get(1).getName(),
                        specialCourses.get(1).getCredits(),
                        specialCourses.get(1).getControl(),
                        specialCourses.get(1).getDescription(),
                        specialCourses.get(1).getDepartment(),
                        specialCourses.get(1).getTeacher(),
                        specialCourses.get(1).getEducationalModule()
                ),
                new Course(
                        specialCourses.get(2).getId(),
                        specialCourses.get(2).getName(),
                        specialCourses.get(2).getCredits(),
                        specialCourses.get(2).getControl(),
                        specialCourses.get(2).getDescription(),
                        specialCourses.get(2).getDepartment(),
                        specialCourses.get(2).getTeacher(),
                        specialCourses.get(2).getEducationalModule()
                ),
                new Course(
                        specialCourses.get(3).getId(),
                        specialCourses.get(3).getName(),
                        specialCourses.get(3).getCredits(),
                        specialCourses.get(3).getControl(),
                        specialCourses.get(3).getDescription(),
                        specialCourses.get(3).getDepartment(),
                        specialCourses.get(3).getTeacher(),
                        specialCourses.get(3).getEducationalModule()
                ),
                new Course(
                        specialCourses.get(4).getId(),
                        specialCourses.get(4).getName(),
                        specialCourses.get(4).getCredits(),
                        specialCourses.get(4).getControl(),
                        specialCourses.get(4).getDescription(),
                        specialCourses.get(4).getDepartment(),
                        specialCourses.get(4).getTeacher(),
                        specialCourses.get(4).getEducationalModule()
                ),
                new Course(
                        specialCourses.get(5).getId(),
                        specialCourses.get(5).getName(),
                        specialCourses.get(5).getCredits(),
                        specialCourses.get(5).getControl(),
                        specialCourses.get(5).getDescription(),
                        specialCourses.get(5).getDepartment(),
                        specialCourses.get(5).getTeacher(),
                        specialCourses.get(5).getEducationalModule()
                ),
                new Course(
                        specialCourses.get(6).getId(),
                        specialCourses.get(6).getName(),
                        specialCourses.get(6).getCredits(),
                        specialCourses.get(6).getControl(),
                        specialCourses.get(6).getDescription(),
                        specialCourses.get(6).getDepartment(),
                        specialCourses.get(6).getTeacher(),
                        specialCourses.get(6).getEducationalModule()
                ),
                new Course(
                        specialCourses.get(7).getId(),
                        specialCourses.get(7).getName(),
                        specialCourses.get(7).getCredits(),
                        specialCourses.get(7).getControl(),
                        specialCourses.get(7).getDescription(),
                        specialCourses.get(7).getDepartment(),
                        specialCourses.get(7).getTeacher(),
                        specialCourses.get(7).getEducationalModule()
                ),
                new Course(
                        specialCourses.get(8).getId(),
                        specialCourses.get(8).getName(),
                        specialCourses.get(8).getCredits(),
                        specialCourses.get(8).getControl(),
                        specialCourses.get(8).getDescription(),
                        specialCourses.get(8).getDepartment(),
                        specialCourses.get(8).getTeacher(),
                        specialCourses.get(8).getEducationalModule()
                ),
                new Course(
                        specialCourses.get(9).getId(),
                        specialCourses.get(9).getName(),
                        specialCourses.get(9).getCredits(),
                        specialCourses.get(9).getControl(),
                        specialCourses.get(9).getDescription(),
                        specialCourses.get(9).getDepartment(),
                        specialCourses.get(9).getTeacher(),
                        specialCourses.get(9).getEducationalModule()
                )
        );

        GetAllCourses getAllCourses = new GetAllCourses(courseGateway);

        List<Course> allCourses = getAllCourses.getAllCourses();

        Assertions.assertEquals(expected.size(), allCourses.size());
        allCourses.forEach(x -> Assertions.assertTrue(expected.contains(x)));
    }

}
