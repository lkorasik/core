package ru.urfu.mm.core.dto;

public class RegistrationAdministratorDTO {
    private final String registrationToken;
    private final String password;
    private final String passwordAgain;

    public RegistrationAdministratorDTO(String registrationToken, String password, String passwordAgain) {
        this.registrationToken = registrationToken;
        this.password = password;
        this.passwordAgain = passwordAgain;
    }

    public String getRegistrationToken() {
        return registrationToken;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }
}
