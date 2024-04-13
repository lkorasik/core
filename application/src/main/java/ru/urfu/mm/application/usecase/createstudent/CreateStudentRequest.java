package ru.urfu.mm.application.usecase.createstudent;

import java.util.UUID;

public record CreateStudentRequest(
        UUID token,
        UUID programId,
        String group,
        String password,
        String passwordAgain
) {
}
