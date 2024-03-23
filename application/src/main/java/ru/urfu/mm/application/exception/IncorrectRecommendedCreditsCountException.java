package ru.urfu.mm.application.exception;

public class IncorrectRecommendedCreditsCountException extends RuntimeException {
    public IncorrectRecommendedCreditsCountException(int credits) {
        super("The number of credits recommended must be greater than zero. The number was " + credits);
    }
}
