package ru.urfu.mm.domain;

import ru.urfu.mm.domain.enums.UserRole;

import java.util.UUID;

@Deprecated
public class RegistrationToken {
    public UUID registrationToken;
    public UserRole userRole;

    public RegistrationToken() {
    }

    public RegistrationToken(UUID registrationToken, UserRole userRole) {
        this.registrationToken = registrationToken;
        this.userRole = userRole;
    }
}
