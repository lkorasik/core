package ru.urfu.mm.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.core.dto.CourseDTO;
import ru.urfu.mm.core.dto.FullEducationalProgramDTO;
import ru.urfu.mm.core.dto.FullSemesterDTO;
import ru.urfu.mm.core.entity.EducationalProgram;
import ru.urfu.mm.core.entity.EducationalProgramToCoursesWithSemesters;
import ru.urfu.mm.core.entity.Semester;
import ru.urfu.mm.core.repository.EducationalProgramRepository;
import ru.urfu.mm.core.repository.EducationalProgramToCoursesWithSemestersRepository;
import ru.urfu.mm.core.repository.SemesterRepository;
import ru.urfu.mm.core.repository.SpecialCourseRepository;

import java.util.*;

@Service
public class EducationalProgramService {
    @Autowired
    private EducationalProgramRepository educationalProgramRepository;
    @Autowired
    private SemesterRepository semesterRepository;
    @Autowired
    private SpecialCourseRepository specialCourseRepository;
    @Autowired
    private EducationalProgramToCoursesWithSemestersRepository educationalProgramToCoursesWithSemestersRepository;
    @Autowired
    private ObjectMapper serializer;

    public EducationalProgram getEducationalProgram(UUID educationalProgramId) {
        return educationalProgramRepository
                .findById(educationalProgramId)
                .orElseThrow();
    }

    public Map<UUID, Integer> getSemesterIdToRequiredCreditsCount(EducationalProgram educationalProgram) throws JsonProcessingException {
        return serializer.readValue(educationalProgram.getSemesterIdToRequiredCreditsCount(), new TypeReference<HashMap<UUID, Integer>>() {});
    }

    public List<EducationalProgram> getEducationalPrograms() {
        return educationalProgramRepository.findAll();
    }

    public FullEducationalProgramDTO getEducationalProgramById(UUID id) throws JsonProcessingException {
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
                .map(x -> {
                    return new FullSemesterDTO(
                            x,
                            requiredCourses
                                    .stream()
                                    .filter(y -> y.getSpecialCourse().getId().equals(x))
                                    .map(y -> {
                                        return new CourseDTO(
                                                y.getSemester().getId(),
                                                specialCourseRepository
                                                        .findAllById(List.of(y.getSpecialCourse()
                                                                .getId()))
                                                        .getFirst()
                                                        .getName()
                                        );
                                    })
                                    .toList(),
                            specialCourses
                                    .stream()
                                    .filter(y -> y.getSemester().getId().equals(x))
                                    .map(y -> {
                                        return new CourseDTO(
                                                y.getSemester().getId(),
                                                specialCourseRepository
                                                        .findAllById(List.of(y.getSpecialCourse().getId()))
                                                        .getFirst()
                                                        .getName()
                                        );
                                    })
                                    .toList(),
                            List.of()
                    );
                })
                .toList();
        return new FullEducationalProgramDTO(
                program2.getId(),
                program2.getName(),
                credits,
                finalSemesters
        );
    }
}
