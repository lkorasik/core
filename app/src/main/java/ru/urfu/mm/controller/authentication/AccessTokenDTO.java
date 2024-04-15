package ru.urfu.mm.controller.authentication;

import ru.urfu.mm.entity.UserEntityRole;

public record AccessTokenDTO(
        String accessToken,
        String userToken,
        UserEntityRole userEntityRole
) { }
