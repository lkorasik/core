package ru.urfu.mm.application.usecase.creategroup;

/**
 * Неправльный номер курса.
 */
public class InvalidCourseNumberException extends RuntimeException {
    public InvalidCourseNumberException(int number) {
        super("The course number " + number + " is incorrect.");
    }
}
