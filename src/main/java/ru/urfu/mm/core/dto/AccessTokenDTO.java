package ru.urfu.mm.core.dto;

import ru.urfu.mm.core.entity.UserRole;

public class AccessTokenDTO {
    private String accessToken;
    private String login;
    private UserRole userRole;

    public AccessTokenDTO(String accessToken, String login, UserRole userRole) {
        this.accessToken = accessToken;
        this.login = login;
        this.userRole = userRole;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getLogin() {
        return login;
    }

    public UserRole getUserRole() {
        return userRole;
    }
}
