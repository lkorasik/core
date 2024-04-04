package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.UserGateway;
import ru.urfu.mm.domain.User;
import ru.urfu.mm.entity.UserRole;
import ru.urfu.mm.repository.UserRepository;

import java.util.UUID;

@Component
public class UserGatewayImpl implements UserGateway {
    private final UserRepository userRepository;

    @Autowired
    public UserGatewayImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        ru.urfu.mm.entity.User entity = new ru.urfu.mm.entity.User(
                user.getLogin(),
                user.getPassword(),
                UserRole.values()[user.getRole().ordinal()]
        );
        userRepository.save(entity);
    }

    @Override
    public User getByToken(UUID token) {
        ru.urfu.mm.entity.User entity = userRepository.getReferenceById(token);
        return new User(
                entity.getLogin(),
                entity.getPassword(),
                ru.urfu.mm.domain.UserRole.values()[entity.getRole().ordinal()]
        );
    }
}
