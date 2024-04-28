package ru.urfu.mm.application.usecase.get_group;

import java.util.UUID;

/**
 * Группа не найдена
 */
public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(UUID groupId) {
        super("Group ID " + groupId + " not found");
    }
}
