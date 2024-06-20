package ru.urfu.mm.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Базовый учебный план на семестр
 */
public class BaseSemesterPlan {
    /**
     * Идентификатор базового учебного плана
     */
    private final UUID id;
    /**
     * Семестр, для которого составлен учебный план
     */
    private final Semester semester;
    /**
     * Список обяхательных курсов
     */
    private final List<Course> requiredCourses;
    /**
     * Список доступных курсов по выбору
     */
    private final List<Course> availableCourses;
    /**
     * Список НИР
     */
    private final List<Course> scienceWorks;

    public BaseSemesterPlan(UUID id, Semester semester) {
        this.id = id;
        this.semester = semester;
        this.requiredCourses = new ArrayList<>();
        this.availableCourses = new ArrayList<>();
        this.scienceWorks = new ArrayList<>();
    }

    public BaseSemesterPlan(
            UUID id,
            Semester semester,
            List<Course> requiredCourses,
            List<Course> availableCourses,
            List<Course> scienceWorks
    ) {
        this.id = id;
        this.semester = semester;
        this.requiredCourses = requiredCourses;
        this.availableCourses = availableCourses;
        this.scienceWorks = scienceWorks;
    }

    public UUID getId() {
        return id;
    }

    public Semester getSemester() {
        return semester;
    }
}
