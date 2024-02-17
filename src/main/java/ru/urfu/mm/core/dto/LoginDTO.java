package ru.urfu.mm.core.dto;

public class LoginDTO {
    private final String name;
    private final String password;

    public LoginDTO(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
