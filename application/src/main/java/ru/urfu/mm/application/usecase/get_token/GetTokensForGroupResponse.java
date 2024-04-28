package ru.urfu.mm.application.usecase.get_token;

import java.util.UUID;

public record GetTokensForGroupResponse(
        UUID token,
        boolean isActivated
) {
}
