package ru.urfu.mm.dsl;

import ru.urfu.mm.dto.LoginDTO;
import ru.urfu.mm.dto.RegistrationAdministratorDTO;
import ru.urfu.mm.dto.RegistrationStudentDTO;

import java.util.UUID;

/**
 * Описание DSL для манипуляций с DTO
 */
public class DTO_DSL {
    public static RegistrationAdministratorDTO createRegistrationAdministratorDTO() {
        UUID id = UUID.randomUUID();
        UUID password = UUID.randomUUID();
        return new RegistrationAdministratorDTO(
                String.valueOf(id),
                String.valueOf(password),
                String.valueOf(password)
        );
    }

    public static RegistrationStudentDTO createRegistrationStudentDTO() {
        UUID id = UUID.randomUUID();
        UUID password = UUID.randomUUID();
        UUID educationalProgram = UUID.randomUUID();
        return new RegistrationStudentDTO(
                String.valueOf(id),
                educationalProgram,
                "GROUP-1",
                String.valueOf(password),
                String.valueOf(password)
        );
    }

    public static LoginDTO createLoginDTO() {
        UUID id = UUID.randomUUID();
        UUID password = UUID.randomUUID();
        return new LoginDTO(
                String.valueOf(id),
                String.valueOf(password)
        );
    }
}
