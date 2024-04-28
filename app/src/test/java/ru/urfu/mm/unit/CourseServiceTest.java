package ru.urfu.mm.unit;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.mm.entity.*;
import ru.urfu.mm.persistance.repository.EducationalProgramToCoursesWithSemestersRepository;
import ru.urfu.mm.persistance.repository.SelectedCoursesRepository;
import ru.urfu.mm.service.CourseForEducationalProgram;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
    @Mock
    private EducationalProgramToCoursesWithSemestersRepository educationalProgramToCoursesWithSemestersRepository;
    @Mock
    private SelectedCoursesRepository selectedCoursesRepository;

//    @Test
//    public void test_getRequiredCoursesForEducationalProgram() {
//        List<Semester> semesters = List.of(
//                new Semester(UUID.randomUUID(), 2023, 1),
//                new Semester(UUID.randomUUID(), 2024, 2),
//                new Semester(UUID.randomUUID(), 2024, 3),
//                new Semester(UUID.randomUUID(), 2025, 4)
//        );
//
//        CourseService courseService = new CourseService(
//                educationalProgramToCoursesWithSemestersRepository,
//                selectedCoursesRepository
//        );
//
//        EducationalProgram educationalProgram = EntityDSL.createEducationalProgram();
//
//        List<Module> modules = List.of(
//                new Module(UUID.randomUUID(), "M1"),
//                new Module(UUID.randomUUID(), "M2"),
//                new Module(UUID.randomUUID(), "M3"),
//                new Module(UUID.randomUUID(), "M4")
//        );
//
//        List<SpecialCourse> specialCourses = List.of(
//                new SpecialCourse(UUID.randomUUID(), "C1", 3, Control.TEST, "C1", "D1", "T1", modules.get(0)),
//                new SpecialCourse(UUID.randomUUID(), "C2", 3, Control.TEST, "C2", "D2", "T2", modules.get(0)),
//                new SpecialCourse(UUID.randomUUID(), "C3", 3, Control.TEST, "C3", "D3", "T3", modules.get(1)),
//                new SpecialCourse(UUID.randomUUID(), "C4", 3, Control.TEST, "C4", "D4", "T4", modules.get(1)),
//                new SpecialCourse(UUID.randomUUID(), "C5", 3, Control.TEST, "C5", "D5", "T5", modules.get(2)),
//                new SpecialCourse(UUID.randomUUID(), "C6", 3, Control.TEST, "C6", "D6", "T6", modules.get(2)),
//                new SpecialCourse(UUID.randomUUID(), "C7", 3, Control.TEST, "C7", "D7", "T7", modules.get(3)),
//                new SpecialCourse(UUID.randomUUID(), "C8", 3, Control.TEST, "C8", "D8", "T8", modules.get(3)),
//                new SpecialCourse(UUID.randomUUID(), "C9", 3, Control.TEST, "C9", "D9", "T9", modules.get(3)),
//                new SpecialCourse(UUID.randomUUID(), "C10", 3, Control.TEST, "C10", "D10", "T10", modules.get(3))
//        );
//
//        List<EducationalProgramToCoursesWithSemesters> selectedCourses = List.of(
//                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(0), specialCourses.get(0), true),
//                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(1), specialCourses.get(1), true),
//                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(1), specialCourses.get(2), true),
//                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(2), specialCourses.get(3), true)
//        );
//
//        when(educationalProgramToCoursesWithSemestersRepository.findAll()).thenReturn(selectedCourses);
//
//        List<Map.Entry<UUID, UUID>> expected = List.of(
//                Map.entry(specialCourses.get(0).getId(), semesters.get(0).getId()),
//                Map.entry(specialCourses.get(1).getId(), semesters.get(1).getId()),
//                Map.entry(specialCourses.get(2).getId(), semesters.get(1).getId()),
//                Map.entry(specialCourses.get(3).getId(), semesters.get(2).getId())
//        );
//
//        List<Map.Entry<UUID, UUID>> list = courseService.getRequiredCoursesForEducationalProgram(educationalProgram.getId());
//
//        Assertions.assertEquals(expected.size(), list.size());
//        list.forEach(x -> Assertions.assertTrue(expected.contains(x)));
//    }

//    @Test
//    public void test_getRequiredCoursesForEducationalProgram_incorrectRequirements() {
//        List<Semester> semesters = List.of(
//                new Semester(UUID.randomUUID(), 2023, 1),
//                new Semester(UUID.randomUUID(), 2024, 2),
//                new Semester(UUID.randomUUID(), 2024, 3),
//                new Semester(UUID.randomUUID(), 2025, 4)
//        );
//
//        CourseService courseService = new CourseService(
//                educationalProgramToCoursesWithSemestersRepository,
//                selectedCoursesRepository
//        );
//
//        EducationalProgram educationalProgram = EntityDSL.createEducationalProgram();
//
//        List<Module> modules = List.of(
//                new Module(UUID.randomUUID(), "M1"),
//                new Module(UUID.randomUUID(), "M2"),
//                new Module(UUID.randomUUID(), "M3"),
//                new Module(UUID.randomUUID(), "M4")
//        );
//
//        List<SpecialCourse> specialCourses = List.of(
//                new SpecialCourse(UUID.randomUUID(), "C1", 3, Control.TEST, "C1", "D1", "T1", modules.get(0)),
//                new SpecialCourse(UUID.randomUUID(), "C2", 3, Control.TEST, "C2", "D2", "T2", modules.get(0)),
//                new SpecialCourse(UUID.randomUUID(), "C3", 3, Control.TEST, "C3", "D3", "T3", modules.get(1)),
//                new SpecialCourse(UUID.randomUUID(), "C4", 3, Control.TEST, "C4", "D4", "T4", modules.get(1)),
//                new SpecialCourse(UUID.randomUUID(), "C5", 3, Control.TEST, "C5", "D5", "T5", modules.get(2)),
//                new SpecialCourse(UUID.randomUUID(), "C6", 3, Control.TEST, "C6", "D6", "T6", modules.get(2)),
//                new SpecialCourse(UUID.randomUUID(), "C7", 3, Control.TEST, "C7", "D7", "T7", modules.get(3)),
//                new SpecialCourse(UUID.randomUUID(), "C8", 3, Control.TEST, "C8", "D8", "T8", modules.get(3)),
//                new SpecialCourse(UUID.randomUUID(), "C9", 3, Control.TEST, "C9", "D9", "T9", modules.get(3)),
//                new SpecialCourse(UUID.randomUUID(), "C10", 3, Control.TEST, "C10", "D10", "T10", modules.get(3))
//        );
//
//        List<EducationalProgramToCoursesWithSemesters> selectedCourses = List.of(
//                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(0), specialCourses.get(0), true),
//                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(1), specialCourses.get(1), true),
//                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(1), specialCourses.get(2), true),
//                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(2), specialCourses.get(3), true),
//                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(3), specialCourses.get(3), true)
//        );
//
//        when(educationalProgramToCoursesWithSemestersRepository.findAll()).thenReturn(selectedCourses);
//
//        Assertions.assertThrows(CourseRequiredCriteriaException.class, () -> courseService.getRequiredCoursesForEducationalProgram(educationalProgram.getId()));
//    }

//    @Test
//    public void test_getActualSpecialCoursesStatistics() {
//        List<Semester> semesters = List.of(
//                new Semester(UUID.randomUUID(), 2023, 1),
//                new Semester(UUID.randomUUID(), 2024, 2),
//                new Semester(UUID.randomUUID(), 2024, 3),
//                new Semester(UUID.randomUUID(), 2025, 4)
//        );
//
//        CourseService courseService = new CourseService(
//                educationalProgramToCoursesWithSemestersRepository,
//                selectedCoursesRepository
//        );
//
//        EducationalProgram educationalProgram = EntityDSL.createEducationalProgram();
//        User user = new User(UUID.randomUUID(), "P", UserRole.STUDENT);
//        Student student = new Student(UUID.randomUUID(), educationalProgram, "G1", user);
//
//        List<Module> modules = List.of(
//                new Module(UUID.randomUUID(), "M1"),
//                new Module(UUID.randomUUID(), "M2"),
//                new Module(UUID.randomUUID(), "M3"),
//                new Module(UUID.randomUUID(), "M4")
//        );
//
//        List<SpecialCourse> specialCourses = List.of(
//                new SpecialCourse(UUID.randomUUID(), "C1", 3, Control.TEST, "C1", "D1", "T1", modules.get(0)),
//                new SpecialCourse(UUID.randomUUID(), "C2", 3, Control.TEST, "C2", "D2", "T2", modules.get(0)),
//                new SpecialCourse(UUID.randomUUID(), "C3", 3, Control.TEST, "C3", "D3", "T3", modules.get(1)),
//                new SpecialCourse(UUID.randomUUID(), "C4", 3, Control.TEST, "C4", "D4", "T4", modules.get(1)),
//                new SpecialCourse(UUID.randomUUID(), "C5", 3, Control.TEST, "C5", "D5", "T5", modules.get(2)),
//                new SpecialCourse(UUID.randomUUID(), "C6", 3, Control.TEST, "C6", "D6", "T6", modules.get(2)),
//                new SpecialCourse(UUID.randomUUID(), "C7", 3, Control.TEST, "C7", "D7", "T7", modules.get(3)),
//                new SpecialCourse(UUID.randomUUID(), "C8", 3, Control.TEST, "C8", "D8", "T8", modules.get(3)),
//                new SpecialCourse(UUID.randomUUID(), "C9", 3, Control.TEST, "C9", "D9", "T9", modules.get(3)),
//                new SpecialCourse(UUID.randomUUID(), "C10", 3, Control.TEST, "C10", "D10", "T10", modules.get(3))
//        );
//
//        List<SelectedCourses> selectedCourses = List.of(
//                new SelectedCourses(UUID.randomUUID(), student, semesters.get(0), specialCourses.get(0)),
//                new SelectedCourses(UUID.randomUUID(), student, semesters.get(1), specialCourses.get(1)),
//                new SelectedCourses(UUID.randomUUID(), student, semesters.get(1), specialCourses.get(2)),
//                new SelectedCourses(UUID.randomUUID(), student, semesters.get(2), specialCourses.get(3))
//        );
//
//        List<EducationalProgramToCoursesWithSemesters> educationalProgramToCoursesWithSemesters = List.of(
//                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(0), specialCourses.get(0), true),
//                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(1), specialCourses.get(1), true),
//                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(1), specialCourses.get(2), true),
//                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(2), specialCourses.get(3), true)
//        );
//
//        when(educationalProgramToCoursesWithSemestersRepository.findAll()).thenReturn(educationalProgramToCoursesWithSemesters);
//        when(selectedCoursesRepository.findAll()).thenReturn(selectedCourses);
//
//        List<CourseStatisticsDTO> expected = List.of(
//                new CourseStatisticsDTO(specialCourses.get(0).getId(), specialCourses.get(0).getName(), 1),
//                new CourseStatisticsDTO(specialCourses.get(1).getId(), specialCourses.get(1).getName(), 1),
//                new CourseStatisticsDTO(specialCourses.get(2).getId(), specialCourses.get(2).getName(), 1),
//                new CourseStatisticsDTO(specialCourses.get(3).getId(), specialCourses.get(3).getName(), 1)
//        );
//
//        List<UUID> semestersIds = semesters
//                .stream()
//                .map(Semester::getId)
//                .toList();
//
//        List<CourseStatisticsDTO> list = courseService.getActualSpecialCoursesStatistics(semestersIds);
//
//        Assertions.assertEquals(expected.size(), list.size());
//        list.forEach(x -> Assertions.assertTrue(expected.contains(x)));
//    }

    private boolean isEquals(CourseForEducationalProgram first, CourseForEducationalProgram second) {
        return first.getName().equals(second.getName())
               && first.getCreditsCount() == second.getCreditsCount()
               && first.getControl().equals(second.getControl())
               && first.getDescription().equals(second.getDescription())
               && first.getEducationalModuleId().equals(second.getEducationalModuleId());
    }
}
