package ru.urfu.mm.application.usecase.create;

import java.util.UUID;

public record CreateAccountRequest(UUID token, String password, String passwordAgain) {
}
