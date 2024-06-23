package ru.urfu.mm.application.gateway;

import ru.urfu.mm.domain.AcademicGroup;

import java.util.List;
import java.util.UUID;

public interface TokenGateway {
    void deleteToken(UUID token);
    boolean isAdministratorToken(UUID token);
    boolean isStudentToken(UUID token);
}
