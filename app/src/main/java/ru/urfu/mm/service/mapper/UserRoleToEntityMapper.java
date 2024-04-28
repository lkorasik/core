package ru.urfu.mm.service.mapper;

import org.springframework.stereotype.Component;
import ru.urfu.mm.domain.UserRole;
import ru.urfu.mm.persistance.entity.UserEntityRole;

@Component
public class UserRoleToEntityMapper implements Mapper<UserRole, UserEntityRole> {
    @Override
    public UserEntityRole map(UserRole object) {
        return UserEntityRole.values()[object.ordinal()];
    }
}
