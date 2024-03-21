package ru.urfu.mm.domain;

import java.util.List;
import java.util.Map;

/**
 * Образовательное направление.
 * Представляет собой направление.
 */
public class Program {
    /**
     * Список доступных модулей
     */
    private final List<Module> availableModules;
    /**
     * Семестру соответствуют курсы, которые проводятся в нем
     */
    private final Map<Semester, List<Course>> coursesBySemester;

    public Program(List<Module> availableModules, Map<Semester, List<Course>> coursesBySemester) {
        this.availableModules = availableModules;
        this.coursesBySemester = coursesBySemester;
    }
}
