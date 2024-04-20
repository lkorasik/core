package ru.urfu.mm.application.usecase.generatetoken;

import java.util.UUID;

public record GenerateStudentRegistrationTokensRequest(
        int tokenCount,
        UUID groupId
) {
}
