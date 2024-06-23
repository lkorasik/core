package ru.urfu.mm.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Семстровый учебный план студента
 */
public class StudentSemesterPlan {
    /**
     * Идентификатор учебного плана
     */
    private final UUID id;
    /**
     * Семестр
     */
    private final Semester semester;
    /**
     * Список курсов по выбору
     */
    private final List<Course> specialCourses;
    /**
     * Базовый учебный план
     */
    private final BaseSemesterPlan baseSemesterPlan;

    public StudentSemesterPlan(UUID id, Semester semester, BaseSemesterPlan baseSemesterPlan) {
        this.id = id;
        this.semester = semester;
        this.specialCourses = new ArrayList<>();
        this.baseSemesterPlan = baseSemesterPlan;
    }
}
