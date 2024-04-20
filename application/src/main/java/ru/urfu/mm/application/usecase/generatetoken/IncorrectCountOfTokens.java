package ru.urfu.mm.application.usecase.generatetoken;

/**
 * Некорректное число токенов
 */
public class IncorrectCountOfTokens extends RuntimeException {
    public IncorrectCountOfTokens(int actualCount) {
        super("The number of tokens requested must be greater than zero. " + actualCount + " tokens were requested.");
    }
}
