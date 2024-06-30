package ru.urfu.mm.application.usecase.get_group;

import ru.urfu.mm.domain.exception.ApplicationException;

import java.util.UUID;

/**
 * Группа не найдена
 */
public class GroupNotFoundException extends ApplicationException {
    public GroupNotFoundException(UUID groupId) {
        super("Group ID " + groupId + " not found");
    }
}
