package ru.urfu.mm.controller.authentication;

public record LoginDTO(
        String token,
        String password
) { }
