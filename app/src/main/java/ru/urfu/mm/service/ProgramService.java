package ru.urfu.mm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.controller.course.CourseDTO;
import ru.urfu.mm.controller.program.*;
import ru.urfu.mm.entity.EducationalProgram;
import ru.urfu.mm.entity.EducationalProgramToCoursesWithSemesters;
import ru.urfu.mm.entity.Semester;
import ru.urfu.mm.entity.SemesterType;
import ru.urfu.mm.repository.EducationalProgramRepository;
import ru.urfu.mm.repository.EducationalProgramToCoursesWithSemestersRepository;
import ru.urfu.mm.repository.SemesterRepository;
import ru.urfu.mm.repository.SpecialCourseRepository;

import java.time.Year;
import java.util.*;

@Service
public class ProgramService {
    private final EducationalProgramRepository educationalProgramRepository;
    private final SemesterRepository semesterRepository;
    private final SpecialCourseRepository specialCourseRepository;
    private final EducationalProgramToCoursesWithSemestersRepository educationalProgramToCoursesWithSemestersRepository;
    private final ObjectMapper serializer;

    @Autowired
    public ProgramService(EducationalProgramRepository educationalProgramRepository, SemesterRepository semesterRepository, SpecialCourseRepository specialCourseRepository, EducationalProgramToCoursesWithSemestersRepository educationalProgramToCoursesWithSemestersRepository, ObjectMapper serializer) {
        this.educationalProgramRepository = educationalProgramRepository;
        this.semesterRepository = semesterRepository;
        this.specialCourseRepository = specialCourseRepository;
        this.educationalProgramToCoursesWithSemestersRepository = educationalProgramToCoursesWithSemestersRepository;
        this.serializer = serializer;
    }

    public FullProgramDTO getEducationalProgramById(UUID id) throws JsonProcessingException {
        var program2 = educationalProgramRepository
                .findById(id)
                .get();
        var program = serializer.readValue(program2.getSemesterIdToRequiredCreditsCount(), new TypeReference<HashMap<UUID, Integer>>() {});
        var semesters = new ArrayList<>(semesterRepository
                .findAll()
                .stream()
                .filter(x -> program.containsKey(x.getId()))
                .toList());
        semesters.sort(Comparator.comparing(Semester::getSemesterNumber));
        var credits = semesters
                .stream()
                .map(x -> program.get(x.getId()))
                .toList();
        var semesterIds = semesters
                .stream()
                .map(Semester::getId)
                .toList();

        var educationalProgramsWithSemestersAndCourses = educationalProgramToCoursesWithSemestersRepository
                .findAllByEducationalProgramId(program2.getId());

        var requiredCourses = educationalProgramsWithSemestersAndCourses
                .stream()
                .filter(EducationalProgramToCoursesWithSemesters::isRequiredCourse)
                .toList();
        var specialCourses = educationalProgramsWithSemestersAndCourses
                .stream()
                .filter(x -> !x.isRequiredCourse())
                .toList();
        var finalSemesters = semesterIds
                .stream()
                .map(x -> new FullSemesterDTO(
                        x,
                        requiredCourses
                                .stream()
                                .filter(y -> y.getSemester().getId().equals(x))
                                .map(y -> new ru.urfu.mm.controller.program.CourseDTO(
                                        y.getSemester().getId(),
                                        specialCourseRepository
                                                .findAllById(List.of(y.getSpecialCourse()
                                                        .getId()))
                                                .getFirst()
                                                .getName()
                                ))
                                .toList(),
                        specialCourses
                                .stream()
                                .filter(y -> y.getSemester().getId().equals(x))
                                .map(y -> new ru.urfu.mm.controller.program.CourseDTO(
                                        y.getSemester().getId(),
                                        specialCourseRepository
                                                .findAllById(List.of(y.getSpecialCourse().getId()))
                                                .getFirst()
                                                .getName()
                                ))
                                .toList(),
                        List.of()
                ))
                .toList();
        return new FullProgramDTO(
                program2.getId(),
                program2.getName(),
                credits,
                finalSemesters
        );
    }

    public void createEducationalProgram(CreateProgramDTO createProgramDTO) throws JsonProcessingException {
        createNecessarySemesters(createProgramDTO.semesters());
        var semestersIds = getSemesters();

        var pairs = new ArrayList<List<UUID>>();
        for(var semester : createProgramDTO.semesters()) {
            var requiredCoursesIds = semester.getRequiredCourses();

            var modulesIds = specialCourseRepository
                    .findAllById(requiredCoursesIds)
                    .stream()
                    .map(x -> x.getEducationalModule().getId())
                    .distinct()
                    .toList();
            var allCourses = modulesIds
                    .stream()
                    .map(this::getEducationalModuleCourses)
                    .flatMap(Collection::stream)
                    .toList();

            // Надо выяснить какие модули мы испольщзуем
            // Составить список всех курсов, которые есть в используемых модулях

            var semesterIndex = createProgramDTO
                    .semesters()
                    .indexOf(semester);
            requiredCoursesIds
                    .forEach(x -> pairs.add(List.of(semestersIds.get(semesterIndex), x)));
        }

        // Программа
        var programId = createEducationalProgram(semestersIds, createProgramDTO);
        var models = pairs
                .stream()
                .map(pair -> {
                    var semesterId = pair.get(0);
                    var courseId = pair.get(1);

                    var educationalProgram = educationalProgramRepository.findById(programId).get();
                    var semester = semesterRepository.findById(semesterId).get();
                    var course = specialCourseRepository.findById(courseId).get();

                    return new EducationalProgramToCoursesWithSemesters(
                            educationalProgram,
                            semester,
                            course,
                            true
                    );
                })
                .toList();
        models.forEach(x -> educationalProgramToCoursesWithSemestersRepository.save(x));

        // Добавить (программа, курс, семестр) в бд
    }

    public UUID createEducationalProgram(List<UUID> semestersIds, CreateProgramDTO educationalProgramRequest) throws JsonProcessingException {
        Map<UUID, Integer> credits = new HashMap<>();
        var length = Math.min(semestersIds.size(), educationalProgramRequest.recommendedCredits().size());
        for (int i = 0; i < length; i++) {
            credits.put(semestersIds.get(i), educationalProgramRequest.recommendedCredits().get(i));
        }

        var serializedCredits = serializer.writeValueAsString(credits);
        var program = new EducationalProgram(
                educationalProgramRequest.title(),
                educationalProgramRequest.title(),
                serializedCredits
        );

        educationalProgramRepository.save(program);

        return program.getId();
    }

    public List<CourseDTO> getEducationalModuleCourses(UUID educationalModuleId) {
        var courses = specialCourseRepository
                .findAll()
                .stream()
                .filter(x -> educationalModuleId.equals(x.getEducationalModule().getId()))
                .toList();

        return courses
                .stream()
                .map(ModelConverterHelper::toDomain)
                .toList();
    }

    /**
     * Получить список актуальных семестров
     */
    private List<UUID> getSemesters() {
        var years = List.of(
                Year.now().getValue(),
                Year.now().getValue() + 1,
                Year.now().getValue() + 1,
                Year.now().getValue() + 2
        );
        var semestersNumbers = List.of(1, 2, 3, 4);
        return semesterRepository
                .findAll()
                .stream()
                .filter(x -> years.contains(x.getYear()))
                .map(Semester::getId)
                .toList();
    }

    /**
     * Добавить недостающие семестры
     */
    private void createNecessarySemesters(List<CreateSemesterDTO> semesters) {
        var years = List.of(
                Year.now().getValue(),
                Year.now().getValue() + 1,
                Year.now().getValue() + 1,
                Year.now().getValue() + 2
        );
        var semestersNumbers = List.of(1, 2, 3, 4);

        // Получаем список семестров, которые уже занесены в систему
        var existingSemesters = semesterRepository
                .findAll()
                .stream()
                .filter(x -> years.contains(x.getYear()) && semestersNumbers.contains(x.getSemesterNumber()))
                .toList();

        // Выичсляем какие семестры необходимо добавить в систему
        var toAdd = new ArrayList<Semester>();
        for(int i = 0; i < semesters.size(); i++) {
            Semester semester;
            if (i % 2 == 0) {
                semester = new Semester(years.get(i), i + 1, SemesterType.FALL);
            } else {
                semester = new Semester(years.get(i), i + 1, SemesterType.SPRING);
            }
            toAdd.add(semester);
        }
        var toAdd2 = toAdd
                .stream()
                .filter(x -> !existingSemesters
                        .stream()
                        .map(y -> List.of(y.getYear(), y.getSemesterNumber()))
                        .toList()
                        .contains(List.of(x.getYear(), x.getSemesterNumber()))
                )
                .toList();

        // Добавляем недостающие семестры
        toAdd2.forEach(x -> semesterRepository.save(x));
    }
}
