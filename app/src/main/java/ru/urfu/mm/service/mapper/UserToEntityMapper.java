package ru.urfu.mm.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.domain.User;
import ru.urfu.mm.entity.UserEntity;
import ru.urfu.mm.entity.UserEntityRole;

@Component
public class UserToEntityMapper implements Mapper<User, UserEntity> {
    private final Mapper<ru.urfu.mm.domain.UserRole, UserEntityRole> roleMapper;

    @Autowired
    public UserToEntityMapper(Mapper<ru.urfu.mm.domain.UserRole, UserEntityRole> roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public UserEntity map(ru.urfu.mm.domain.User user) {
        return new UserEntity(
                user.getLogin(),
                user.getPassword(),
                roleMapper.map(user.getRole())
        );
    }
}
