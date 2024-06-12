package ru.urfu.mm.application.usecase.create.account;

import java.util.UUID;

public interface CreateAccountRequest {
    UUID getToken();
    String getPassword();
    String getPasswordAgain();
}
