package ru.urfu.mm.exceptions;

public class CourseRequiredCriteriaException extends RuntimeException {
    public CourseRequiredCriteriaException() {
        super("There is more than one semester, in which course is required");
    }
}
