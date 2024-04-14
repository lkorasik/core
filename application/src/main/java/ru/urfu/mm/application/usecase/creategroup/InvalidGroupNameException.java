package ru.urfu.mm.application.usecase.creategroup;

/**
 * Номер группы предоставлен в некорректном формате
 */
public class InvalidGroupNameException extends RuntimeException {
    public InvalidGroupNameException(String number) {
        super("The group number " + number + " is not in the correct format.");
    }
}
