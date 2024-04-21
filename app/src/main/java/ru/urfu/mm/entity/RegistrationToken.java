package ru.urfu.mm.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "registration_tokens")
public class RegistrationToken {
    @Id
    @Column(name = "registration_token")
    public UUID registrationToken;
    public RegistrationToken() {
    }

    public RegistrationToken(UUID registrationToken) {
        this.registrationToken = registrationToken;
    }
}
