package ru.urfu.mm.application.exception;

public class CourseRequiredCriteriaException extends RuntimeException {
    public CourseRequiredCriteriaException() {
        super("There is more than one semester, in which course is required");
    }
}