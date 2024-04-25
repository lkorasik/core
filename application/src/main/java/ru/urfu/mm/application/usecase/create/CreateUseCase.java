package ru.urfu.mm.application.usecase.create;

import ru.urfu.mm.application.usecase.create.account.CreateUserRequest;

/**
 * Интерфейс создания пользователя
 */
public interface CreateUseCase {
    void create(CreateUserRequest request);
}
