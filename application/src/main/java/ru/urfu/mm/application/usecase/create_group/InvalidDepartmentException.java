package ru.urfu.mm.application.usecase.create_group;

import ru.urfu.mm.domain.exception.ApplicationException;

/**
 * Некорректный префикс группы.
 */
public class InvalidDepartmentException extends ApplicationException {
    public InvalidDepartmentException(String departmentCode) {
        super("The institute code is incorrect.");
    }
}
