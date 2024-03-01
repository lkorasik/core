package ru.urfu.mm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.urfu.mm.entity.EducationalProgram;
import ru.urfu.mm.entity.RegistrationToken;
import ru.urfu.mm.entity.Semester;
import ru.urfu.mm.entity.UserRole;
import ru.urfu.mm.repository.*;
import ru.urfu.mm.entity.*;
import ru.urfu.mm.repository.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
class CoreApplicationTests {
    @Autowired
    public CourseToRequiredSkillsRepository courseToRequiredSkillsRepository;
    @Autowired
    public CourseToResultSkillsRepository courseToResultSkillsRepository;
    @Autowired
    private DesiredSkillsRepository desiredSkillsRepository;
    @Autowired
    public EducationalModuleRepository educationalModuleRepository;
    @Autowired
    public EducationalProgramRepository educationalProgramRepository;
    @Autowired
    public EducationalProgramToCoursesWithSemestersRepository educationalProgramToCoursesWithSemestersRepository;
    @Autowired
    public RegistrationTokenRepository registrationTokenRepository;
    @Autowired
    public SelectedCoursesRepository selectedCoursesRepository;
    @Autowired
    public SemesterRepository semesterRepository;
    @Autowired
    public SkillRepository skillRepository;
    @Autowired
    public SpecialCourseRepository specialCourseRepository;
    @Autowired
    public StudentRepository studentRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    private StudentSkillRepository studentSkillRepository;
    @Autowired
    public ObjectMapper serializer;

    @Test
    public void init() throws JsonProcessingException {
        // Инициализация

        var semesters = List.of(
                new Semester(2023, 1),
                new Semester(2024, 2),
                new Semester(2024, 3),
                new Semester(2025, 4)
        );
        semesterRepository.saveAll(semesters);

        var modules = List.of(
                new EducationalModule("Введение в Kaggle"),
                new EducationalModule("Обучение с подкреплением и нейронные сети"),
                new EducationalModule("Введение в Python"),
                new EducationalModule("Конфигурирование и программирование в системе 1С")
        );
        educationalModuleRepository.saveAll(modules);

        var specialCourses = List.of(
                new SpecialCourse(
                        "Введение в Kaggle",
                        6,
                        Control.EXAM,
                        "-",
                        "-",
                        "-",
                        modules.get(0)
                ),
                new SpecialCourse(
                        "Обучение с подкреплением и нейронные сети",
                        6,
                        Control.TEST,
                        "https://docs.google.com/document/d/1J6XAVcghwQ3_yZXizJF9Cg9sRZLPyXY6YpmrUTtrjF4/edit",
                        "-",
                        "Плаксин Антон",
                        modules.get(1)
                ),
                new SpecialCourse(
                        "Введение в Python",
                        3,
                        Control.EXAM,
                        "-",
                        "-",
                        "Зверев Владимир Сергеевич",
                        modules.get(2)
                ),
                new SpecialCourse(
                        "Конфигурирование и программирование в системе 1С",
                        3,
                        Control.TEST,
                        "-",
                        "Кафедра вычислительной математики и компьютерных наук",
                        "Хачай, Михаил Юрьевич",
                        modules.get(3)
                ),
                new SpecialCourse(
                        "Конфигурирование и программирование в системе 1С",
                        3,
                        Control.TEST,
                        "-",
                        "Кафедра вычислительной математики и компьютерных наук",
                        "Хачай, Михаил Юрьевич",
                        modules.get(3)
                ),
                new SpecialCourse(
                        "Конфигурирование и программирование в системе 1С",
                        3,
                        Control.EXAM,
                        "-",
                        "Кафедра вычислительной математики и компьютерных наук",
                        "Хачай, Михаил Юрьевич",
                        modules.get(3)
                )
        );
        specialCourseRepository.saveAll(specialCourses);

        var semestersIdsToCreditsCount = Map.of(
                semesters.get(0).getId(), 6,
                semesters.get(1).getId(), 12,
                semesters.get(2).getId(), 6,
                semesters.get(3).getId(), 6
        );
        var educationalPrograms = List.of(
                new EducationalProgram(
                        "Математика и Компьютерные Науки",
                        "02.04.01 Математика и компьютерные науки",
                        serializer.writeValueAsString(semestersIdsToCreditsCount)
                ),
                new EducationalProgram(
                        "Математическое обеспечение и администрирование информационных систем",
                        "02.04.03 Математическое обеспечение и администрирование информационных систем",
                        serializer.writeValueAsString(semestersIdsToCreditsCount)
                )
        );
        educationalProgramRepository.saveAll(educationalPrograms);

        var coursesBySemesters = List.of(
                new EducationalProgramToCoursesWithSemesters(
                        educationalPrograms.get(0),
                        semesters.get(0),
                        specialCourses.get(0),
                        false
                ),
                new EducationalProgramToCoursesWithSemesters(
                        educationalPrograms.get(0),
                        semesters.get(0),
                        specialCourses.get(1),
                        false
                ),
                new EducationalProgramToCoursesWithSemesters(
                        educationalPrograms.get(1),
                        semesters.get(3),
                        specialCourses.get(1),
                        false
                ),
                new EducationalProgramToCoursesWithSemesters(
                        educationalPrograms.get(0),
                        semesters.get(0),
                        specialCourses.get(2),
                        false
                ),
                new EducationalProgramToCoursesWithSemesters(
                        educationalPrograms.get(0),
                        semesters.get(0),
                        specialCourses.get(3),
                        false
                ),
                new EducationalProgramToCoursesWithSemesters(
                        educationalPrograms.get(0),
                        semesters.get(1),
                        specialCourses.get(4),
                        false
                ),
                new EducationalProgramToCoursesWithSemesters(
                        educationalPrograms.get(0),
                        semesters.get(2),
                        specialCourses.get(5),
                        false
                ),
                new EducationalProgramToCoursesWithSemesters(
                        educationalPrograms.get(1),
                        semesters.get(0),
                        specialCourses.get(3),
                        false
                ),
                new EducationalProgramToCoursesWithSemesters(
                        educationalPrograms.get(1),
                        semesters.get(1),
                        specialCourses.get(4),
                        false
                ),
                new EducationalProgramToCoursesWithSemesters(
                        educationalPrograms.get(1),
                        semesters.get(2),
                        specialCourses.get(5),
                        false
                )
        );
        educationalProgramToCoursesWithSemestersRepository.saveAll(coursesBySemesters);

        Iterable<RegistrationToken> tokens = List.of(
                new RegistrationToken(UUID.randomUUID(), UserRole.STUDENT),
                new RegistrationToken(UUID.randomUUID(), UserRole.UNIVERSITY_EMPLOYEE),
                new RegistrationToken(UUID.randomUUID(), UserRole.ADMIN)
        );
        registrationTokenRepository.saveAll(tokens);

        System.out.println("Tokens: ");
        tokens.forEach(item -> System.out.println(item.registrationToken + " " + item.userRole.getValue()));

        // Навыки для сервиса рекомендаций
        var skills = List.of(
                new Skill(UUID.randomUUID(), "Машинное обучение"),
                new Skill(UUID.randomUUID(), "Мат. статистика"),
                new Skill(UUID.randomUUID(), "1С")
        );
        var courseToRequiredSkills = List.of(
                new CourseToRequiredSkills(
                        specialCourses.get(0),
                        skills.get(0),
                        SkillLevel.BEGINNER
                ),
                new CourseToRequiredSkills(
                        specialCourses.get(1),
                        skills.get(1),
                        SkillLevel.BEGINNER
                )
        );
        var courseToResultSkills = List.of(
                new CourseToResultSkills(
                        specialCourses.get(0),
                        skills.get(0),
                        SkillLevel.INTERMEDIATE
                ),
                new CourseToResultSkills(
                        specialCourses.get(1),
                        skills.get(1),
                        SkillLevel.INTERMEDIATE
                )
        );
        skillRepository.saveAll(skills);
        courseToRequiredSkillsRepository.saveAll(courseToRequiredSkills);
        courseToResultSkillsRepository.saveAll(courseToResultSkills);
    }

    @Test
    public void drop() {
        studentSkillRepository.deleteAll();
        desiredSkillsRepository.deleteAll();
        selectedCoursesRepository.deleteAll();
        courseToResultSkillsRepository.deleteAll();
        courseToRequiredSkillsRepository.deleteAll();
        skillRepository.deleteAll();
        studentRepository.deleteAll();
        educationalProgramToCoursesWithSemestersRepository.deleteAll();
        semesterRepository.deleteAll();
        specialCourseRepository.deleteAll();
        educationalModuleRepository.deleteAll();
        educationalProgramRepository.deleteAll();
        registrationTokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void recreate() throws JsonProcessingException {
        drop();
        init();
    }
}
