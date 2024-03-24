package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.applicationlegacy.gateway.UserGateway;
import ru.urfu.mm.domainlegacy.User;
import ru.urfu.mm.entity.UserRole;
import ru.urfu.mm.repository.UserRepository;

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
}
