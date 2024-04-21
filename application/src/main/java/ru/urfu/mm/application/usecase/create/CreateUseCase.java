package ru.urfu.mm.application.usecase.create;

import ru.urfu.mm.application.usecase.create.user.CreateUserRequest;

/**
 * Интерфейс создания пользователя
 */
public interface CreateUseCase {
    void create(CreateUserRequest request);
}
