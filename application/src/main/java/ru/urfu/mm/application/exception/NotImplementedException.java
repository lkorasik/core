package ru.urfu.mm.application.exception;

public class NotImplementedException extends RuntimeException {
    public NotImplementedException() {
        super("This method not implemented yet");
    }
}
