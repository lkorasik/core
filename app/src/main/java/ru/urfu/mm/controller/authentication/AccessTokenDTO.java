package ru.urfu.mm.controller.authentication;

public record AccessTokenDTO(
        String accessToken,
        String userToken,
        String userEntityRole
) { }
