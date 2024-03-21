package ru.urfu.mm.domain;

/**
 * Академическая группа
 * Представляет собой академическую группу.
 */
public class Group {
    /**
     * Номер группы
     */
    private final String number;
    /**
     * Направление
     */
    private final Program program;

    public Group(String number, Program program) {
        this.number = number;
        this.program = program;
    }
}
