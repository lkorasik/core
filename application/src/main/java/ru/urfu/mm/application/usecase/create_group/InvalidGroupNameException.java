package ru.urfu.mm.application.usecase.create_group;

import ru.urfu.mm.domain.exception.ApplicationException;

/**
 * Номер группы предоставлен в некорректном формате
 */
public class InvalidGroupNameException extends ApplicationException {
    public InvalidGroupNameException(String number) {
        super("The group number " + number + " is not in the correct format.");
    }
}
