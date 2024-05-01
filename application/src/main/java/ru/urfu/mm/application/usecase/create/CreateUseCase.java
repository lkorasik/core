package ru.urfu.mm.application.usecase.create;

import ru.urfu.mm.application.usecase.create.account.CreateAccountRequest;

/**
 * Интерфейс создания пользователя
 */
public interface CreateUseCase {
    void create(CreateAccountRequest request);
}
