package ru.urfu.mm.application.usecase.create_group;

import java.util.UUID;

public record CreateGroupRequest(
        String number,
        int startYear,
        UUID programId
) {
}
