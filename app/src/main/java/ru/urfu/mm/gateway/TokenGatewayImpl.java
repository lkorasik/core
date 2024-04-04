package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.TokenGateway;
import ru.urfu.mm.domain.UserRole;
import ru.urfu.mm.repository.RegistrationTokenRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class TokenGatewayImpl implements TokenGateway {
    private final RegistrationTokenRepository registrationTokenRepository;

    @Autowired
    public TokenGatewayImpl(RegistrationTokenRepository registrationTokenRepository) {
        this.registrationTokenRepository = registrationTokenRepository;
    }

    @Override
    public Optional<UserRole> getRoleByToken(UUID token) {
        return registrationTokenRepository
                .findByRegistrationToken(token)
                .map(x -> x.userRole)
                .map(x -> UserRole.values()[x.ordinal()]);
    }

    @Override
    public void deleteToken(UUID token) {
        registrationTokenRepository.deleteById(token);
    }
}
