package ru.urfu.mm.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.mm.dsl.EntityDSL;
import ru.urfu.mm.dto.SpecialCourseDTO;
import ru.urfu.mm.dto.SpecialCourseStatisticsDTO;
import ru.urfu.mm.entity.*;
import ru.urfu.mm.entity.Module;
import ru.urfu.mm.exceptions.CourseRequiredCriteriaException;
import ru.urfu.mm.repository.EducationalModuleRepository;
import ru.urfu.mm.repository.EducationalProgramToCoursesWithSemestersRepository;
import ru.urfu.mm.repository.SelectedCoursesRepository;
import ru.urfu.mm.repository.SpecialCourseRepository;
import ru.urfu.mm.service.CourseForEducationalProgram;
import ru.urfu.mm.service.SpecialCourseService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SpecialCourseServiceTest {
    @Mock
    private EducationalProgramToCoursesWithSemestersRepository educationalProgramToCoursesWithSemestersRepository;
    @Mock
    private SelectedCoursesRepository selectedCoursesRepository;
    @Mock
    private SpecialCourseRepository specialCourseRepository;
    @Mock
    private EducationalModuleRepository educationalModuleRepository;

    /**
     * Добавляем в систему курсы. Первые шесть курсов идут для первого направления. Остальные четыре курса идут для
     * второго направления.
     */
    @Test
    public void test_getCoursesByEducationalProgramAndSemesters() {
        List<Semester> semesters = List.of(
                new Semester(UUID.randomUUID(), 2023, 1),
                new Semester(UUID.randomUUID(), 2024, 2),
                new Semester(UUID.randomUUID(), 2024, 3),
                new Semester(UUID.randomUUID(), 2025, 4)
        );
        EducationalProgram educationalProgram = EntityDSL.createEducationalProgram();
        EducationalProgram fakeEducationalProgram = EntityDSL.createEducationalProgram();

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

        when(educationalProgramToCoursesWithSemestersRepository.findAll()).thenReturn(educationalProgramToCoursesWithSemesters);

        SpecialCourseService specialCourseService = new SpecialCourseService(
                educationalProgramToCoursesWithSemestersRepository,
                selectedCoursesRepository,
                specialCourseRepository,
                educationalModuleRepository
        );

        List<UUID> semestersId = semesters
                .stream()
                .map(Semester::getId)
                .toList();

        List<CourseForEducationalProgram> result = specialCourseService.getCoursesByEducationalProgramAndSemesters(educationalProgram.getId(), semestersId);

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

    @Test
    public void test_getSelectedCoursesIds() {
        List<Semester> semesters = List.of(
                new Semester(UUID.randomUUID(), 2023, 1),
                new Semester(UUID.randomUUID(), 2024, 2),
                new Semester(UUID.randomUUID(), 2024, 3),
                new Semester(UUID.randomUUID(), 2025, 4)
        );

        SpecialCourseService specialCourseService = new SpecialCourseService(
                educationalProgramToCoursesWithSemestersRepository,
                selectedCoursesRepository,
                specialCourseRepository,
                educationalModuleRepository
        );

        EducationalProgram educationalProgram = EntityDSL.createEducationalProgram();
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

        when(selectedCoursesRepository.findAll()).thenReturn(selectedCourses);

        Map<UUID, List<UUID>> map = specialCourseService.getSelectedCoursesIds(student.getLogin(), semesters.stream().map(Semester::getId).toList());

        Assertions.assertEquals(map.size(), selectedCourses.size());
        Assertions.assertTrue(selectedCourses
                .stream()
                .map(SelectedCourses::getId)
                .map(map::containsKey)
                .reduce((x, y) -> x && y)
                .get());
    }

    @Test
    public void test_getRequiredCoursesForEducationalProgram() {
        List<Semester> semesters = List.of(
                new Semester(UUID.randomUUID(), 2023, 1),
                new Semester(UUID.randomUUID(), 2024, 2),
                new Semester(UUID.randomUUID(), 2024, 3),
                new Semester(UUID.randomUUID(), 2025, 4)
        );

        SpecialCourseService specialCourseService = new SpecialCourseService(
                educationalProgramToCoursesWithSemestersRepository,
                selectedCoursesRepository,
                specialCourseRepository,
                educationalModuleRepository
        );

        EducationalProgram educationalProgram = EntityDSL.createEducationalProgram();

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

        List<EducationalProgramToCoursesWithSemesters> selectedCourses = List.of(
                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(0), specialCourses.get(0), true),
                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(1), specialCourses.get(1), true),
                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(1), specialCourses.get(2), true),
                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(2), specialCourses.get(3), true)
        );

        when(educationalProgramToCoursesWithSemestersRepository.findAll()).thenReturn(selectedCourses);

        List<Map.Entry<UUID, UUID>> expected = List.of(
                Map.entry(specialCourses.get(0).getId(), semesters.get(0).getId()),
                Map.entry(specialCourses.get(1).getId(), semesters.get(1).getId()),
                Map.entry(specialCourses.get(2).getId(), semesters.get(1).getId()),
                Map.entry(specialCourses.get(3).getId(), semesters.get(2).getId())
        );

        List<Map.Entry<UUID, UUID>> list = specialCourseService.getRequiredCoursesForEducationalProgram(educationalProgram.getId());

        Assertions.assertEquals(expected.size(), list.size());
        list.forEach(x -> Assertions.assertTrue(expected.contains(x)));
    }

    @Test
    public void test_getRequiredCoursesForEducationalProgram_incorrectRequirements() {
        List<Semester> semesters = List.of(
                new Semester(UUID.randomUUID(), 2023, 1),
                new Semester(UUID.randomUUID(), 2024, 2),
                new Semester(UUID.randomUUID(), 2024, 3),
                new Semester(UUID.randomUUID(), 2025, 4)
        );

        SpecialCourseService specialCourseService = new SpecialCourseService(
                educationalProgramToCoursesWithSemestersRepository,
                selectedCoursesRepository,
                specialCourseRepository,
                educationalModuleRepository
        );

        EducationalProgram educationalProgram = EntityDSL.createEducationalProgram();

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

        List<EducationalProgramToCoursesWithSemesters> selectedCourses = List.of(
                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(0), specialCourses.get(0), true),
                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(1), specialCourses.get(1), true),
                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(1), specialCourses.get(2), true),
                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(2), specialCourses.get(3), true),
                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(3), specialCourses.get(3), true)
        );

        when(educationalProgramToCoursesWithSemestersRepository.findAll()).thenReturn(selectedCourses);

        Assertions.assertThrows(CourseRequiredCriteriaException.class, () -> specialCourseService.getRequiredCoursesForEducationalProgram(educationalProgram.getId()));
    }

    @Test
    public void test_getActualSpecialCoursesStatistics() {
        List<Semester> semesters = List.of(
                new Semester(UUID.randomUUID(), 2023, 1),
                new Semester(UUID.randomUUID(), 2024, 2),
                new Semester(UUID.randomUUID(), 2024, 3),
                new Semester(UUID.randomUUID(), 2025, 4)
        );

        SpecialCourseService specialCourseService = new SpecialCourseService(
                educationalProgramToCoursesWithSemestersRepository,
                selectedCoursesRepository,
                specialCourseRepository,
                educationalModuleRepository
        );

        EducationalProgram educationalProgram = EntityDSL.createEducationalProgram();
        User user = new User(UUID.randomUUID(), "P", UserRole.STUDENT);
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
                new SelectedCourses(UUID.randomUUID(), student, semesters.get(1), specialCourses.get(2)),
                new SelectedCourses(UUID.randomUUID(), student, semesters.get(2), specialCourses.get(3))
        );

        List<EducationalProgramToCoursesWithSemesters> educationalProgramToCoursesWithSemesters = List.of(
                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(0), specialCourses.get(0), true),
                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(1), specialCourses.get(1), true),
                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(1), specialCourses.get(2), true),
                new EducationalProgramToCoursesWithSemesters(UUID.randomUUID(), educationalProgram, semesters.get(2), specialCourses.get(3), true)
        );

        when(educationalProgramToCoursesWithSemestersRepository.findAll()).thenReturn(educationalProgramToCoursesWithSemesters);
        when(selectedCoursesRepository.findAll()).thenReturn(selectedCourses);

        List<SpecialCourseStatisticsDTO> expected = List.of(
                new SpecialCourseStatisticsDTO(specialCourses.get(0).getId(), specialCourses.get(0).getName(), 1),
                new SpecialCourseStatisticsDTO(specialCourses.get(1).getId(), specialCourses.get(1).getName(), 1),
                new SpecialCourseStatisticsDTO(specialCourses.get(2).getId(), specialCourses.get(2).getName(), 1),
                new SpecialCourseStatisticsDTO(specialCourses.get(3).getId(), specialCourses.get(3).getName(), 1)
        );

        List<UUID> semestersIds = semesters
                .stream()
                .map(Semester::getId)
                .toList();

        List<SpecialCourseStatisticsDTO> list = specialCourseService.getActualSpecialCoursesStatistics(semestersIds);

        Assertions.assertEquals(expected.size(), list.size());
        list.forEach(x -> Assertions.assertTrue(expected.contains(x)));
    }

    @Test
    public void test_getAllCourses() {
        List<Semester> semesters = List.of(
                new Semester(UUID.randomUUID(), 2023, 1),
                new Semester(UUID.randomUUID(), 2024, 2),
                new Semester(UUID.randomUUID(), 2024, 3),
                new Semester(UUID.randomUUID(), 2025, 4)
        );

        SpecialCourseService specialCourseService = new SpecialCourseService(
                educationalProgramToCoursesWithSemestersRepository,
                selectedCoursesRepository,
                specialCourseRepository,
                educationalModuleRepository
        );

        EducationalProgram educationalProgram = EntityDSL.createEducationalProgram();
        User user = new User(UUID.randomUUID(), "P", UserRole.STUDENT);
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

        when(specialCourseRepository.findAll()).thenReturn(specialCourses);

        List<SpecialCourseDTO> expected = List.of(
                new SpecialCourseDTO(
                        specialCourses.get(0).getId(),
                        specialCourses.get(0).getName(),
                        specialCourses.get(0).getCreditsCount(),
                        specialCourses.get(0).getControl(),
                        specialCourses.get(0).getDescription(),
                        specialCourses.get(0).getEducationalModule().getId(),
                        specialCourses.get(0).getTeacherName(),
                        specialCourses.get(0).getDepartment()
                ),
                new SpecialCourseDTO(
                        specialCourses.get(1).getId(),
                        specialCourses.get(1).getName(),
                        specialCourses.get(1).getCreditsCount(),
                        specialCourses.get(1).getControl(),
                        specialCourses.get(1).getDescription(),
                        specialCourses.get(1).getEducationalModule().getId(),
                        specialCourses.get(1).getTeacherName(),
                        specialCourses.get(1).getDepartment()
                ),
                new SpecialCourseDTO(
                        specialCourses.get(2).getId(),
                        specialCourses.get(2).getName(),
                        specialCourses.get(2).getCreditsCount(),
                        specialCourses.get(2).getControl(),
                        specialCourses.get(2).getDescription(),
                        specialCourses.get(2).getEducationalModule().getId(),
                        specialCourses.get(2).getTeacherName(),
                        specialCourses.get(2).getDepartment()
                ),
                new SpecialCourseDTO(
                        specialCourses.get(3).getId(),
                        specialCourses.get(3).getName(),
                        specialCourses.get(3).getCreditsCount(),
                        specialCourses.get(3).getControl(),
                        specialCourses.get(3).getDescription(),
                        specialCourses.get(3).getEducationalModule().getId(),
                        specialCourses.get(3).getTeacherName(),
                        specialCourses.get(3).getDepartment()
                ),
                new SpecialCourseDTO(
                        specialCourses.get(4).getId(),
                        specialCourses.get(4).getName(),
                        specialCourses.get(4).getCreditsCount(),
                        specialCourses.get(4).getControl(),
                        specialCourses.get(4).getDescription(),
                        specialCourses.get(4).getEducationalModule().getId(),
                        specialCourses.get(4).getTeacherName(),
                        specialCourses.get(4).getDepartment()
                ),
                new SpecialCourseDTO(
                        specialCourses.get(5).getId(),
                        specialCourses.get(5).getName(),
                        specialCourses.get(5).getCreditsCount(),
                        specialCourses.get(5).getControl(),
                        specialCourses.get(5).getDescription(),
                        specialCourses.get(5).getEducationalModule().getId(),
                        specialCourses.get(5).getTeacherName(),
                        specialCourses.get(5).getDepartment()
                ),
                new SpecialCourseDTO(
                        specialCourses.get(6).getId(),
                        specialCourses.get(6).getName(),
                        specialCourses.get(6).getCreditsCount(),
                        specialCourses.get(6).getControl(),
                        specialCourses.get(6).getDescription(),
                        specialCourses.get(6).getEducationalModule().getId(),
                        specialCourses.get(6).getTeacherName(),
                        specialCourses.get(6).getDepartment()
                ),
                new SpecialCourseDTO(
                        specialCourses.get(7).getId(),
                        specialCourses.get(7).getName(),
                        specialCourses.get(7).getCreditsCount(),
                        specialCourses.get(7).getControl(),
                        specialCourses.get(7).getDescription(),
                        specialCourses.get(7).getEducationalModule().getId(),
                        specialCourses.get(7).getTeacherName(),
                        specialCourses.get(7).getDepartment()
                ),
                new SpecialCourseDTO(
                        specialCourses.get(8).getId(),
                        specialCourses.get(8).getName(),
                        specialCourses.get(8).getCreditsCount(),
                        specialCourses.get(8).getControl(),
                        specialCourses.get(8).getDescription(),
                        specialCourses.get(8).getEducationalModule().getId(),
                        specialCourses.get(8).getTeacherName(),
                        specialCourses.get(8).getDepartment()
                ),
                new SpecialCourseDTO(
                        specialCourses.get(9).getId(),
                        specialCourses.get(9).getName(),
                        specialCourses.get(9).getCreditsCount(),
                        specialCourses.get(9).getControl(),
                        specialCourses.get(9).getDescription(),
                        specialCourses.get(9).getEducationalModule().getId(),
                        specialCourses.get(9).getTeacherName(),
                        specialCourses.get(9).getDepartment()
                )
        );

        List<SpecialCourseDTO> list = specialCourseService.getAllCourses();

        Assertions.assertEquals(expected.size(), list.size());
        list.forEach(x -> Assertions.assertTrue(expected.contains(x)));
    }

    private boolean isEquals(CourseForEducationalProgram first, CourseForEducationalProgram second) {
        return first.getName().equals(second.getName())
               && first.getCreditsCount() == second.getCreditsCount()
               && first.getControl().equals(second.getControl())
               && first.getDescription().equals(second.getDescription())
               && first.getEducationalModuleId().equals(second.getEducationalModuleId());
    }
}
