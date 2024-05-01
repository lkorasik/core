package ru.urfu.mm.controller.authentication;

import ru.urfu.mm.application.usecase.login_user.LoginRequest;

import java.util.UUID;

public record LoginDTO(
        String token,
        String password
) {
    public LoginRequest toRequest() {
        return new LoginRequest(UUID.fromString(token), password);
    }
}
