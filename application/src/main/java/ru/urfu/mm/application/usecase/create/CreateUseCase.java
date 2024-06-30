package ru.urfu.mm.application.usecase.create;

/**
 * Интерфейс создания пользователя
 */
public interface CreateUseCase {
    void create(CreateAccountRequest request);
}
