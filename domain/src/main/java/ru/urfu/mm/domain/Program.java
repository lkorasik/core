package ru.urfu.mm.domain;

import java.util.List;

/**
 * Образовательное направление.
 * Представляет собой направление.
 */
public class Program {
    /**
     * Название программы
     */
    private final String name;
    /**
     * Список групп
     */
    private final List<Group> groups;

    public Program(String name, List<Group> groups) {
        this.name = name;
        this.groups = groups;
    }
}
