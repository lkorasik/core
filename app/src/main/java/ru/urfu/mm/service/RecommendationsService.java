package ru.urfu.mm.service;

import org.springframework.stereotype.Service;
import ru.urfu.mm.application.exception.NotImplementedException;
import ru.urfu.mm.application.usecase.CourseForEducationalProgram;
import ru.urfu.mm.controller.recommendation.*;
import ru.urfu.mm.persistance.entity.StudentDesiredSkills;
import ru.urfu.mm.persistance.entity.StudentSkills;

import java.util.*;

@Service
public class RecommendationsService {
    private List<UUID> getComplementaryCoursesIds(
            Map<UUID, List<StudentSkills>> courseIdToRequiredSkills,
            Map<UUID, List<StudentSkills>> courseIdToResultSkills,
            List<StudentSkills> studentSkills,
            List<UUID> partiallySuitableCoursesIds
    ) {
        var resultCoursesIds = new ArrayList<UUID>();
        var requiredSkillsForPartiallySuitableCourses = partiallySuitableCoursesIds
                .stream()
                .map(courseIdToRequiredSkills::get)
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
        throw new NotImplementedException();
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
