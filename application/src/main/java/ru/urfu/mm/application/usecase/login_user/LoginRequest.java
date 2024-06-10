package ru.urfu.mm.application.usecase.login_user;

import java.util.UUID;

public interface LoginRequest {
    UUID getToken();
    String getPassword();
}