package ru.urfu.mm.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.mm.core.entity.RegistrationToken;

import java.util.Optional;
import java.util.UUID;

public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, UUID> {
    Optional<RegistrationToken> findByRegistrationToken(UUID registrationToken);
}
