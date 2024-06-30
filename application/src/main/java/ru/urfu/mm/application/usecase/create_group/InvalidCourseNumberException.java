package ru.urfu.mm.application.usecase.create_group;

import ru.urfu.mm.domain.exception.ApplicationException;

/**
 * Неправльный номер курса.
 */
public class InvalidCourseNumberException extends ApplicationException {
    public InvalidCourseNumberException(int number) {
        super("The course number " + number + " is incorrect.");
    }
}
