package ru.urfu.mm.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Образовательное направление.
 * Представляет собой направление.
 */
public class Program {
    /**
     * Идентификатор программы
     */
    private final UUID id;
    /**
     * Название программы
     */
    private final String name;
    /**
     * Список групп
     */
    private final List<Group> groups;

    public Program(UUID id, String name, List<Group> groups) {
        this.id = id;
        this.name = name;
        this.groups = groups;
    }

    public Program(String name, List<Group> groups) {
        this(UUID.randomUUID(), name, groups);
    }

    public Program(UUID id, String name) {
        this(id, name, new ArrayList<>());
    }

    public Program(String name) {
        this(UUID.randomUUID(), name, new ArrayList<>());
    }

    public void addGroup(Group group) {
        groups.add(group);
    }
}
