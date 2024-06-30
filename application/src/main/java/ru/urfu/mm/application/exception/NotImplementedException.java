package ru.urfu.mm.application.exception;

import ru.urfu.mm.domain.exception.ApplicationException;

public class NotImplementedException extends ApplicationException {
    public NotImplementedException() {
        super("This method not implemented yet");
    }
}
