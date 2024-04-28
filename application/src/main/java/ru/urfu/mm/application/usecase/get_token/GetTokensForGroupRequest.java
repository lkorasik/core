package ru.urfu.mm.application.usecase.get_token;

import java.util.UUID;

public record GetTokensForGroupRequest(
        UUID groupId
) {
}
