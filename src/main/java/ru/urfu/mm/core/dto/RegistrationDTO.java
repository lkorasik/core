package ru.urfu.mm.core.dto;

public class RegistrationDTO {
    private final String name;
    private final String password;

    public RegistrationDTO(String name, String password) {
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
