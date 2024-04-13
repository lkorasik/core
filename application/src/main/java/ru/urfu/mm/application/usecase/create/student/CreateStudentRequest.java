package ru.urfu.mm.application.usecase.create.student;

import java.util.UUID;

public record CreateStudentRequest(
        UUID token,
        UUID programId,
        String group,
        String password,
        String passwordAgain
) {
}
