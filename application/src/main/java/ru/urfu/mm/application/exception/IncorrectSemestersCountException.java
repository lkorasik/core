package ru.urfu.mm.application.exception;

public class IncorrectSemestersCountException extends RuntimeException {
    public IncorrectSemestersCountException(int currentSemestersCount) {
        super("Received " + currentSemestersCount + " semesters, but should have been 4.");
    }
}
