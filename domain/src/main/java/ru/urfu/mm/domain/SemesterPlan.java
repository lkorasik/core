package ru.urfu.mm.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Учебный план на конкретный семестр
 */
public class SemesterPlan {
    /**
     * Идентификатор семестрового учебного плана
     */
    private final UUID id;
    /**
     * Семестр
     */
    private final Semester semester;
    /**
     * Рекомендуемое число зачисляемых единиц
     */
    private final int recommendedCredits;
    /**
     * Обязательные курсы
     */
    private final List<Course> requiredCourses;
    /**
     * Спец курсы
     */
    private final List<Course> specialCourses;
    /**
     * Научно-исследовательская работа
     */
    private final List<Course> scienceWork;

    public SemesterPlan(UUID id, Semester semester, int recommendedCredits) {
        this.id = id;
        this.semester = semester;
        this.recommendedCredits = recommendedCredits;
        requiredCourses = new ArrayList<>();
        specialCourses = new ArrayList<>();
        scienceWork = new ArrayList<>();
    }

    public SemesterPlan(
            UUID id,
            Semester semester,
            int recommendedCredits,
            List<Course> requiredCourses,
            List<Course> specialCourses,
            List<Course> scienceWork) {
        this.id = id;
        this.semester = semester;
        this.recommendedCredits = recommendedCredits;
        this.requiredCourses = requiredCourses;
        this.specialCourses = specialCourses;
        this.scienceWork = scienceWork;
    }

    public UUID getId() {
        return id;
    }

    public Semester getSemester() {
        return semester;
    }

    public int getRecommendedCredits() {
        return recommendedCredits;
    }

    public List<Course> getRequiredCourses() {
        return requiredCourses;
    }

    public List<Course> getSpecialCourses() {
        return specialCourses;
    }

    public List<Course> getScienceWork() {
        return scienceWork;
    }
}
