package ru.urfu.mm.application.usecase.creategroup;

/**
 * Некорректный префикс группы.
 */
public class InvalidDepartmentException extends RuntimeException {
    public InvalidDepartmentException(String departmentCode) {
        super("The institute code is incorrect.");
    }
}
