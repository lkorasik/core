package ru.urfu.mm.application.usecase.getgroup;

import java.util.UUID;

/**
 * Группа не найдена
 */
public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(UUID groupId) {
        super("Group ID " + groupId + " not found");
    }
}
