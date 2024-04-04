package ru.urfu.mm.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Академическая группа.
 * Представляет собой академическую группу.
 */
public class Group {
    /**
     * Номер группы
     */
    private final String number;
    /**
     * Список студентов группы
     */
    private final List<Student> students;
    /**
     * Список семестров
     */
    private final List<Semester> semesters;
    /**
     * Список модулей, которые доступны студентам для выбора
     */
    private final List<Module> availableModules;
    /**
     * Соответствие семестра и рекомендуемое число зачетных единиц
     */
    private final Map<Semester, Integer> recommendedCredits;
    /**
     * Список обязательных модулей
     */
    private final List<Module> requiredModules;

    public Group(
            String number,
            List<Student> students,
            List<Semester> semesters,
            List<Module> availableModules,
            Map<Semester, Integer> recommendedCredits,
            List<Module> requiredModules) {
        this.number = number;
        this.students = students;
        this.semesters = semesters;
        this.availableModules = availableModules;
        this.recommendedCredits = recommendedCredits;
        this.requiredModules = requiredModules;
    }

    public Group(
            String number,
            List<Semester> semesters,
            Map<Semester, Integer> recommendedCredits) {
        this.number = number;
        this.students = new ArrayList<>();
        this.semesters = semesters;
        this.availableModules = new ArrayList<>();
        this.recommendedCredits = recommendedCredits;
        this.requiredModules = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addAvailableModule(Module module) {
        availableModules.add(module);
    }

    public void addRequiredModules(Module module) {
        requiredModules.add(module);
    }
}
