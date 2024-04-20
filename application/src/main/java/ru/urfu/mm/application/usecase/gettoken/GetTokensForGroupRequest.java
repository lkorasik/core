package ru.urfu.mm.application.usecase.gettoken;

import java.util.UUID;

public record GetTokensForGroupRequest(
        UUID groupId
) {
}
