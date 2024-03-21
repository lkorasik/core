package ru.urfu.mm.domain;

import java.util.List;

/**
 * Студент.
 * Представляет собой студента
 */
public class Student extends User {
    /**
     * Группа, в которой состоит студент
     */
    private final Group group;
    /**
     * Список навыков студента
     */
    private final List<Skill> skills;
    /**
     * Список выбранных модулей
     */
    private final List<Module> selectedModules;

    public Student(String token, String password, Group group, List<Skill> skills, List<Module> selectedModules) {
        super(token, password);
        this.group = group;
        this.skills = skills;
        this.selectedModules = selectedModules;
    }
}
