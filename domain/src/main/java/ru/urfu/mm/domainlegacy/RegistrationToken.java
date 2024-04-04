package ru.urfu.mm.domainlegacy;

import java.util.UUID;

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
