package ru.urfu.mm.controller.authentication;

import ru.urfu.mm.entity.UserRole;

public record AccessTokenDTO(
        String accessToken,
        String userToken,
        UserRole userRole
) { }
