package ru.urfu.mm.controller.authentication;

import ru.urfu.mm.application.usecase.create.account.CreateAccountRequest;

import java.util.UUID;

public record RegistrationDTO(
        String token,
        String password,
        String passwordAgain
) {
    public CreateAccountRequest toRequest() {
        return new CreateAccountRequest(UUID.fromString(token), password, passwordAgain);
    }
}
