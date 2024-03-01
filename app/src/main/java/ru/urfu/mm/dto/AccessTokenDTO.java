package ru.urfu.mm.dto;

import ru.urfu.mm.entity.UserRole;

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
