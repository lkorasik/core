package ru.urfu.mm.application.usecase.generate_student_registration_token;

/**
 * Некоректное число токенов
 */
public class IncorrectNumberOfTokensException extends RuntimeException {
    public IncorrectNumberOfTokensException(int tokensCount) {
        super("An incorrect number of tokens was requested. Requested: " + tokensCount + ". More than zero tokens " +
                "must be requested.");
    }
}
