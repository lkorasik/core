package ru.urfu.mm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.core.dto.RecommendationResultDTO;
import ru.urfu.mm.core.dto.RecommendedCourseDTO;
import ru.urfu.mm.core.dto.SemesterDTO;
import ru.urfu.mm.core.dto.SkillDTO;
import ru.urfu.mm.core.entity.Student;
import ru.urfu.mm.core.entity.StudentDesiredSkills;
import ru.urfu.mm.core.entity.StudentSkills;

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
    private SpecialCourseService specialCourseService;
    @Autowired
    private CoursesSkillsService coursesSkillsService;

    public RecommendationResultDTO calculateRecommendations(Student student) {
        var studentSkills = skillsService.getSkillsForStudent(student.getLogin());
        var studentDesiredSkills = desiredSkillsService.getSkillsForStudent(student.getLogin());

        var actualSemestersIds = semesterService.getActualSemesters()
                .stream()
                .map(SemesterDTO::getId)
                .toList();
        var courses = specialCourseService.getCoursesByEducationalProgramAndSemesters(
                student.getEducationalProgram().getId(),
                actualSemestersIds
        );

        var optionalCourses = courses
                .stream()
                .filter(x -> x.getRequiredSemesterId() == null)
                .toList();
        var optionalCoursesIds = optionalCourses
                .stream()
                .map(CourseForEducationalProgram::getId)
                .toList();
        var courseIdToRequiredSkills = coursesSkillsService.getCoursesToRequiredSkills(student, optionalCoursesIds);
        var courseIdToResultSkills = coursesSkillsService.getCoursesToResultSkills(student, optionalCoursesIds);

        var perfectCoursesIds = getPerfectCoursesIds(
                courseIdToRequiredSkills,
                courseIdToResultSkills,
                studentSkills,
                studentDesiredSkills
        );
        var perfectCourses = buildCoursesWithSkills(
                perfectCoursesIds,
                courseIdToRequiredSkills,
                courseIdToResultSkills
        );
        perfectCourses.sort(Collections.reverseOrder());
        var perfectCourses2 = perfectCourses
                .stream()
                .map(x -> {
                    var desiredSkillsIds = studentDesiredSkills
                            .stream()
                            .map(y -> y.getSkill().getId())
                            .toList();
                    var courseResultSkillsIds = x.resultSkills
                            .stream()
                            .map(y -> y.getSkill().getId())
                            .toList();
                    return intersection(courseResultSkillsIds, desiredSkillsIds).size();
                })
                .toList();

        var coursesById = courses
                .stream()
                .collect(
                        Collectors
                                .toMap(CourseForEducationalProgram::getId, x -> x)
                );
        var coursesByModule = courses
                .stream()
                .filter(x -> x.getEducationalModuleId() != null)
                .collect(Collectors.groupingBy(CourseForEducationalProgram::getEducationalModuleId));

        return new RecommendationResultDTO(
                perfectCourses
                        .stream()
                        .map(x -> buildRecommendedCourse(coursesById, x))
                        .toList(),
                List.of(),
                List.of(),
                List.of()
        );
    }

    /*
    public async Task<RecommendationResultDto> CalculateRecommendations(string studentLogin)
{
    var partiallySuitableCoursesIds = GetPartiallySuitableCoursesIds(
        courseIdToRequiredSkills,
        courseIdToResultSkills,
        studentSkills,
        studentDesiredSkills
    );
    var complementaryCoursesIds = GetComplementaryCoursesIds(
        courseIdToRequiredSkills,
        courseIdToResultSkills,
        studentSkills,
        partiallySuitableCoursesIds
    );

    var partiallySuitableCourses =
        BuildCoursesWithSkills(partiallySuitableCoursesIds, courseIdToRequiredSkills, courseIdToResultSkills)
        .OrderByDescending(x =>
        {
            var studentActualSkillsIds = studentSkills.Select(x => x.Id);
            var desiredSkillsIds = studentDesiredSkills.Select(x => x.Id);
            var courseRequiredSkillsIds = x.RequiredSkills.Select(x => x.Id);
            var courseResultSkillsIds = x.ResultSkills.Select(y => y.Id);

            // Двухступенчатая сортировка. Сначала по требуемым курсам, потом по желаемым для изучения
            return courseRequiredSkillsIds.Intersect(studentActualSkillsIds).Count() * 1000 +
                   courseResultSkillsIds.Intersect(desiredSkillsIds).Count();
        });
    var complementaryCourses =
        BuildCoursesWithSkills(complementaryCoursesIds, courseIdToRequiredSkills, courseIdToResultSkills)
            .OrderByDescending(x =>
            {
                var skillsToLearn = GetSkillsToLearn(
                    partiallySuitableCourses.SelectMany(y => y.RequiredSkills).ToArray(),
                    studentSkills
                );
                var skillsToLearnIds = skillsToLearn.Select(y => y.Id);
                var courseResultSkillsIds = x.ResultSkills.Select(y => y.Id);
                return skillsToLearnIds.Intersect(courseResultSkillsIds).Count();
            });

    return new RecommendationResultDto
    {
        PerfectCourses = perfectCourses.Select(x => BuildRecommendedCourse(coursesById, x)).ToArray(),
        PartiallySuitableCourses = partiallySuitableCourses.Select(x => BuildRecommendedCourse(coursesById, x))
            .ToArray(),
        ComplementaryCourses =
            complementaryCourses.Select(x => BuildRecommendedCourse(coursesById, x)).ToArray(),
        ModuleCourses = coursesByModule.Select(x => new ModuleCoursesDto()
            {
                ModuleId = x.Key,
                Courses = x
                    .Select(y => new RecommendedCourse()
                    {
                        Id = y.Id,
                        Name = y.Name,
                        CreditsCount = y.CreditsCount,
                        Control = y.Control,
                        Description = y.Description,
                        Semesters = y.Semesters,
                        EducationalModuleId = y.EducationalModuleId,
                        RequiredSemesterId = y.RequiredSemesterId,
                        RequiredSkills = courseIdToRequiredSkills.TryGetValue(y.Id, out var requiredSkills)
                            ? requiredSkills
                            : Array.Empty<Skill>(),
                        ResultSkills = courseIdToResultSkills.TryGetValue(y.Id, out var resultSkills)
                            ? resultSkills
                            : Array.Empty<Skill>(),
                    })
                    .ToArray()
            })
            .ToArray()
    };
}
     */

    private <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if (list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
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
                        .anyMatch(studentSkill -> studentSkill.getId().equals(requiredSkill.getId())
                                                  && studentSkill.getLevel() == requiredSkill.getLevel()));
    }

    private boolean doesCourseImproveSkills(List<StudentDesiredSkills> wantedSkills, List<StudentSkills> courseResultSkills) {
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
                course.name,
                course.getCreditsCount(),
                course.getControl(),
                course.getDescription(),
                course.getSemesters().stream()
                        .map(x -> new SemesterDTO(x.getId(), x.getYear(), x.getSemesterNumber()))
                        .toList(),
                course.getEducationalModuleId(),
                Optional.of(course.requiredSemesterId),
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
    /*

        private static RecommendedCourse BuildRecommendedCourse(
            Dictionary<Guid, CourseForEducationalProgram> coursesById, CourseWithSkills courseWithSkills)
        {
            var course = coursesById[courseWithSkills.CourseId];
            return new RecommendedCourse
            {
                Id = courseWithSkills.CourseId,
                Name = course.Name,
                CreditsCount = course.CreditsCount,
                Control = course.Control,
                Description = course.Description,
                Semesters = course.Semesters,
                EducationalModuleId = course.EducationalModuleId,
                RequiredSemesterId = course.RequiredSemesterId,
                RequiredSkills = courseWithSkills.RequiredSkills,
                ResultSkills = courseWithSkills.ResultSkills,
            };
        }
     */

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
