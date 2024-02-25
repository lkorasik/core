package ru.urfu.mm.core.dto;

import java.util.UUID;

public class RegistrationStudentDTO {
    private final String registrationToken;
    private final UUID educationalProgramId;
    private final String group;
    private final String password;
    private final String passwordAgain;

    public RegistrationStudentDTO(String registrationToken, UUID educationalProgramId, String group, String password, String passwordAgain) {
        this.registrationToken = registrationToken;
        this.educationalProgramId = educationalProgramId;
        this.group = group;
        this.password = password;
        this.passwordAgain = passwordAgain;
    }

    public String getRegistrationToken() {
        return registrationToken;
    }

    public UUID getEducationalProgramId() {
        return educationalProgramId;
    }

    public String getGroup() {
        return group;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }
}
