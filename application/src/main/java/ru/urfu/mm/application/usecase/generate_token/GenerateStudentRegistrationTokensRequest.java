package ru.urfu.mm.application.usecase.generate_token;

import java.util.UUID;

public record GenerateStudentRegistrationTokensRequest(
        int tokenCount,
        UUID groupId
) {
}
