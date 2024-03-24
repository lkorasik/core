package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.urfu.mm.applicationlegacy.gateway.PasswordGateway;

@Component
public class PasswordGatewayImpl implements PasswordGateway {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordGatewayImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
