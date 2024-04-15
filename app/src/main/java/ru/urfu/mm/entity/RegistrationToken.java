package ru.urfu.mm.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "registration_tokens")
public class RegistrationToken {
    @Id
    @Column(name = "registration_token")
    public UUID registrationToken;
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    public UserEntityRole userEntityRole;

    public RegistrationToken() {
    }

    public RegistrationToken(UUID registrationToken, UserEntityRole userEntityRole) {
        this.registrationToken = registrationToken;
        this.userEntityRole = userEntityRole;
    }
}
