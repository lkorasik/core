package ru.urfu.mm.application.usecase.generate_student_registration_token;

import ru.urfu.mm.domain.exception.ApplicationException;

/**
 * Некоректное число токенов
 */
public class IncorrectNumberOfTokensException extends ApplicationException {
    public IncorrectNumberOfTokensException(int tokensCount) {
        super("An incorrect number of tokens was requested. Requested: " + tokensCount + ". More than zero tokens " +
                "must be requested.");
    }
}
