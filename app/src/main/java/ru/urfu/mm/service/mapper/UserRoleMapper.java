package ru.urfu.mm.service.mapper;

import org.springframework.stereotype.Component;
import ru.urfu.mm.entity.SemesterType;
import ru.urfu.mm.entity.UserRole;

@Component
public class UserRoleMapper implements Mapper<ru.urfu.mm.domain.UserRole, ru.urfu.mm.entity.UserRole>{
    @Override
    public UserRole map(ru.urfu.mm.domain.UserRole object) {
        return ru.urfu.mm.entity.UserRole.values()[object.ordinal()];
    }
}
