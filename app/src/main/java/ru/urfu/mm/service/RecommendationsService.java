package ru.urfu.mm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.applicationlegacy.usecase.CourseForEducationalProgram;
import ru.urfu.mm.applicationlegacy.usecase.GetCoursesByEducationalProgramAndSemesters;
import ru.urfu.mm.controller.recommendation.*;
import ru.urfu.mm.entity.Student;
import ru.urfu.mm.entity.StudentDesiredSkills;
import ru.urfu.mm.entity.StudentSkills;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationsService {
    @Autowired
    private SkillsService skillsService;
    @Autowired
    private DesiredSkillsService desiredSkillsService;
    @Autowired
    private SemesterService semesterService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CoursesSkillsService coursesSkillsService;
    @Autowired
    private GetCoursesByEducationalProgramAndSemesters getCoursesByEducationalProgramAndSemesters;

    public RecommendationResultDTO calculateRecommendations(Student student) {
        var studentSkills = skillsService.getSkillsForStudent(student.getLogin());
        var studentDesiredSkills = desiredSkillsService.getSkillsForStudent(student.getLogin());

        var actualSemestersIds = semesterService.getActualSemesters()
                .stream()
                .map(ru.urfu.mm.controller.semester.SemesterDTO::id)
                .toList();
        var courses = getCoursesByEducationalProgramAndSemesters
                .getCoursesByEducationalProgramAndSemesters(student.getEducationalProgram().getId(), actualSemestersIds);

        var optionalCourses = courses
                .stream()
                .filter(x -> x.getRequiredSemesterId() == null)
                .toList();
        var optionalCoursesIds = optionalCourses
                .stream()
                .map(ru.urfu.mm.applicationlegacy.usecase.CourseForEducationalProgram::getId)
                .toList();
        var courseIdToRequiredSkills = coursesSkillsService.getCoursesToRequiredSkills(student, optionalCoursesIds);
        var courseIdToResultSkills = coursesSkillsService.getCoursesToResultSkills(student, optionalCoursesIds);

        var perfectCoursesIds = getPerfectCoursesIds(
                courseIdToRequiredSkills,
                courseIdToResultSkills,
                studentSkills,
                studentDesiredSkills
        );
        var partiallySuitableCoursesIds = getPartiallySuitableCoursesIds(
                courseIdToRequiredSkills,
                courseIdToResultSkills,
                studentSkills,
                studentDesiredSkills
        );
        var complementaryCoursesIds = getComplementaryCoursesIds(
                courseIdToRequiredSkills,
                courseIdToResultSkills,
                studentSkills,
                partiallySuitableCoursesIds
        );

        var perfectCourses = buildCoursesWithSkills(
                perfectCoursesIds,
                courseIdToRequiredSkills,
                courseIdToResultSkills
        );
        perfectCourses.sort(Comparator.comparing(x -> {
                    var desiredSkillsIds = studentDesiredSkills
                            .stream()
                            .map(y -> y.getSkill().getId())
                            .toList();
                    var courseResultSkillsIds = x.resultSkills
                            .stream()
                            .map(y -> y.getSkill().getId())
                            .toList();
                    return intersection(courseResultSkillsIds, desiredSkillsIds).size();
                }));

        var partiallySuitableCourses = buildCoursesWithSkills(
                partiallySuitableCoursesIds,
                courseIdToRequiredSkills,
                courseIdToResultSkills
        );
        partiallySuitableCourses.sort(Comparator.comparing(x -> {
                    var studentActualSkillsIds = studentSkills.stream().map(y -> y.getSkill().getId()).toList();
                    var desiredSkillsIds = studentDesiredSkills.stream().map(y -> y.getSkill().getId()).toList();
                    var courseRequiredSkillsIds = x.requiredSkills.stream().map(y -> y.getSkill().getId()).toList();
                    var courseResultSkillsIds = x.requiredSkills.stream().map(y -> y.getSkill().getId()).toList();

                    return intersection(courseRequiredSkillsIds, studentActualSkillsIds).size() * 1000 + intersection(courseResultSkillsIds, desiredSkillsIds).size();
                }));

        var complementaryCourses = buildCoursesWithSkills(
                complementaryCoursesIds,
                courseIdToRequiredSkills,
                courseIdToResultSkills
        );
        complementaryCourses.sort(Comparator.comparing(x -> {
                    var skillsToLearn = getSkillsToLearn(
                            partiallySuitableCourses
                                    .stream()
                                    .flatMap(w -> w.getRequiredSkills().stream())
                                    .toList(),
                            studentSkills
                    );

                    var skillsToLearnIds = skillsToLearn
                            .stream()
                            .map(y -> y.getSkill().getId())
                            .toList();
                    var courseResultSkillsIds = x
                            .resultSkills
                            .stream()
                            .map(y -> y.getSkill().getId())
                            .toList();
                    return intersection(skillsToLearnIds, courseResultSkillsIds).size();
                }));

        var coursesById = courses
                .stream()
                .collect(
                        Collectors
                                .toMap(ru.urfu.mm.applicationlegacy.usecase.CourseForEducationalProgram::getId, x -> x)
                );
        var coursesByModule = courses
                .stream()
                .filter(x -> x.getEducationalModuleId() != null)
                .collect(Collectors.groupingBy(ru.urfu.mm.applicationlegacy.usecase.CourseForEducationalProgram::getEducationalModuleId));

        return new RecommendationResultDTO(
                perfectCourses
                        .stream()
                        .map(x -> buildRecommendedCourse(coursesById, x))
                        .toList(),
                partiallySuitableCourses
                        .stream()
                        .map(x -> buildRecommendedCourse(coursesById, x))
                        .toList(),
                complementaryCourses
                        .stream()
                        .map(x -> buildRecommendedCourse(coursesById, x))
                        .toList(),
                coursesByModule
                        .entrySet()
                        .stream()
                        .map(x -> new ModuleCoursesDTO(
                                x.getKey(),
                                x.getValue().stream().map(y -> new RecommendedCourseDTO(
                                        y.getId(),
                                        y.getName(),
                                        y.getCreditsCount(),
                                        y.getSemesters().stream().map(w -> new SemesterDTO(w.getId(), w.getYear(), w.getSemesterNumber())).toList(),
                                        y.getEducationalModuleId(),
                                        y.requiredSemesterId,
                                        courseIdToRequiredSkills.containsKey(y.getId())
                                                ? courseIdToRequiredSkills.get(y.getId()).stream().map(w -> new SkillDTO(w.getId(), w.getSkill().getName(), w.getLevel())).toList()
                                                : Collections.emptyList(),
                                        courseIdToResultSkills.containsKey(y.getId())
                                                ? courseIdToResultSkills.get(y.getId()).stream().map(w -> new SkillDTO(w.getId(), w.getSkill().getName(), w.getLevel())).toList()
                                                : Collections.emptyList()
                                ))
                                        .toList()
                        ))
                        .toList()
        );
    }

    private List<UUID> getComplementaryCoursesIds(
            Map<UUID, List<StudentSkills>> courseIdToRequiredSkills,
            Map<UUID, List<StudentSkills>> courseIdToResultSkills,
            List<StudentSkills> studentSkills,
            List<UUID> partiallySuitableCoursesIds
    ) {
        var resultCoursesIds = new ArrayList<UUID>();
        var requiredSkillsForPartiallySuitableCourses = partiallySuitableCoursesIds
                .stream()
                .map(x -> courseIdToRequiredSkills.get(x))
                .toList()
                .stream()
                .flatMap(Collection::stream)
                .toList();

        var skillsToLearn = getSkillsToLearn(requiredSkillsForPartiallySuitableCourses, studentSkills);

        for (var entry : courseIdToRequiredSkills.entrySet()) {
            var courseId = entry.getKey();
            List<StudentSkills> skills = entry.getValue();

            if (doHaveAllRequiredSkillsForCourse(skills, studentSkills)) {
                var resultSkillsForCourse = courseIdToResultSkills.get(courseId);
                if (doesCourseImproveSkills2(skillsToLearn, resultSkillsForCourse)) {
                    resultCoursesIds.add(courseId);
                }
            }
        }

        return resultCoursesIds;
    }

    /**
     * Вычисляет скиллы для изучения. Смотрим на требующиеся скиллы для курсов из 2 группы и вычитаем то, что студент
     * уже умеет.
     */
    public List<StudentSkills> getSkillsToLearn(List<StudentSkills> requiredSkillsForPartiallySuitableCourses, List<StudentSkills> studentSkills) {
        return requiredSkillsForPartiallySuitableCourses
                .stream()
                .filter(x -> studentSkills.stream().noneMatch(y -> y.getId() == x.getId())
                             || studentSkills.stream().anyMatch(y -> y.getId() == x.getId() && y.getLevel().ordinal() < x.getLevel().ordinal()))
                .toList();
    }

    public static List<CourseWithSkills> buildCoursesWithSkills(
            UUID[] coursesIds,
            Map<UUID, List<StudentSkills>> courseIdToRequiredSkills,
            Map<UUID, List<StudentSkills>> courseIdToResultSkills
    ) {
        List<CourseWithSkills> coursesWithSkillsList = new ArrayList<>();

        for (UUID courseId : coursesIds) {
            CourseWithSkills courseWithSkills = new CourseWithSkills(
                    courseId,
                    courseIdToRequiredSkills.get(courseId),
                    courseIdToResultSkills.get(courseId));
            coursesWithSkillsList.add(courseWithSkills);
        }

        return coursesWithSkillsList;
    }


    private <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if (list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }

    public List<UUID> getPartiallySuitableCoursesIds(
            Map<UUID, List<StudentSkills>> courseIdToRequiredSkills,
            Map<UUID, List<StudentSkills>> courseIdToResultSkills,
            List<StudentSkills> studentSkills,
            List<StudentDesiredSkills> studentDesiredSkills
    ) {
        var resultCoursesIds = new ArrayList<UUID>();
        for (var entry : courseIdToRequiredSkills.entrySet()) {
            var courseId = entry.getKey();
            var skills = entry.getValue();
            if (doHaveSomeRequiredSkillsForCourse(skills, studentSkills) && !doHaveAllRequiredSkillsForCourse(skills, studentSkills)) {
                var resultSkillsForCourse = courseIdToResultSkills.get(courseId);
                if (doesCourseImproveSkills(studentDesiredSkills, resultSkillsForCourse)) {
                    resultCoursesIds.add(courseId);
                }
            }
        }
        return resultCoursesIds;
    }

    private List<UUID> getPerfectCoursesIds(
            Map<UUID, List<StudentSkills>> courseIdToRequiredSkills,
            Map<UUID, List<StudentSkills>> courseIdToResultSkills,
            List<StudentSkills> studentSkills,
            List<StudentDesiredSkills> studentDesiredSkills
    ) {
        var resultCoursesIds = new ArrayList<UUID>();
        for (var entry : courseIdToRequiredSkills.entrySet()) {
            var courseId = entry.getKey();
            var skills = entry.getValue();
            if (doHaveAllRequiredSkillsForCourse(skills, studentSkills)) {
                var resultSkillsForCourse = courseIdToResultSkills.get(courseId);
                if (doesCourseImproveSkills(studentDesiredSkills, resultSkillsForCourse)) {
                    resultCoursesIds.add(courseId);
                }
            }
        }
        return resultCoursesIds;
    }

    private boolean doHaveAllRequiredSkillsForCourse(List<StudentSkills> courseRequiredSkills, List<StudentSkills> studentSkills) {
        return courseRequiredSkills
                .stream()
                .allMatch(requiredSkill -> studentSkills
                        .stream()
                        .anyMatch(studentSkill -> studentSkill.getSkill().getId().equals(requiredSkill.getSkill().getId())
                                                  && studentSkill.getLevel() == requiredSkill.getLevel()));
    }

    private static boolean doHaveSomeRequiredSkillsForCourse(List<StudentSkills> courseRequiredSkills, List<StudentSkills> studentSkills) {
        return courseRequiredSkills
                .stream()
                .anyMatch(x -> studentSkills
                        .stream()
                        .anyMatch(y -> y.getSkill().getId().equals(x.getSkill().getId()) && y.getLevel() == x.getLevel()));
    }

    private boolean doesCourseImproveSkills(List<StudentDesiredSkills> wantedSkills, List<StudentSkills> courseResultSkills) {
        return courseResultSkills
                .stream()
                .anyMatch(x -> wantedSkills
                        .stream()
                        .anyMatch(y -> y.getSkill().getId().equals(x.getSkill().getId()) && y.getLevel().ordinal() <= x.getLevel().ordinal()));
    }

    private boolean doesCourseImproveSkills2(List<StudentSkills> wantedSkills, List<StudentSkills> courseResultSkills) {
        return courseResultSkills
                .stream()
                .anyMatch(x -> wantedSkills
                        .stream()
                        .anyMatch(y -> y.getSkill().getId() == x.getSkill().getId() && y.getLevel().ordinal() <= x.getLevel().ordinal()));
    }

    private List<CourseWithSkills> buildCoursesWithSkills(
            List<UUID> coursesIds,
            Map<UUID, List<StudentSkills>> courseIdToRequiredSkills,
            Map<UUID, List<StudentSkills>> courseIdToResultSkills
    ) {
        var coursesWithSkills = new ArrayList<CourseWithSkills>();
        for (UUID courseId : coursesIds) {
            var courseWithSkills = new CourseWithSkills(
                    courseId,
                    courseIdToRequiredSkills.get(courseId),
                    courseIdToResultSkills.get(courseId)
            );
            coursesWithSkills.add(courseWithSkills);
        }
        return coursesWithSkills;
    }

    private RecommendedCourseDTO buildRecommendedCourse(
            Map<UUID, CourseForEducationalProgram> coursesById,
            CourseWithSkills courseWithSkills) {
        var course = coursesById.get(courseWithSkills.courseId);
        return new RecommendedCourseDTO(
                courseWithSkills.courseId,
                course.getName(),
                course.getCreditsCount(),
                course.getSemesters().stream()
                        .map(x -> new SemesterDTO(x.getId(), x.getYear(), x.getSemesterNumber()))
                        .toList(),
                course.getEducationalModuleId(),
                course.requiredSemesterId,
                courseWithSkills.requiredSkills
                        .stream()
                        .map(x -> new SkillDTO(x.getId(), x.getSkill().getName(), x.getLevel()))
                        .toList(),
                courseWithSkills.requiredSkills
                        .stream()
                        .map(x -> new SkillDTO(x.getId(), x.getSkill().getName(), x.getLevel()))
                        .toList()
        );
    }

    private static class CourseWithSkills {
        private final UUID courseId;
        private final List<StudentSkills> requiredSkills;
        private final List<StudentSkills> resultSkills;

        public CourseWithSkills(UUID courseId, List<StudentSkills> requiredSkills, List<StudentSkills> resultSkills) {
            this.courseId = courseId;
            this.requiredSkills = requiredSkills;
            this.resultSkills = resultSkills;
        }

        public UUID getCourseId() {
            return courseId;
        }

        public List<StudentSkills> getRequiredSkills() {
            return requiredSkills;
        }

        public List<StudentSkills> getResultSkills() {
            return resultSkills;
        }
    }
}
