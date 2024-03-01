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
    public UserRole userRole;

    public RegistrationToken() {
    }

    public RegistrationToken(UUID registrationToken, UserRole userRole) {
        this.registrationToken = registrationToken;
        this.userRole = userRole;
    }
}
