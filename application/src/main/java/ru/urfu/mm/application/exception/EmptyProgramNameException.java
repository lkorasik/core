package ru.urfu.mm.application.exception;

public class EmptyProgramNameException extends RuntimeException {
    public EmptyProgramNameException() {
        super("The program name cannot be empty.");
    }
}
