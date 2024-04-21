package ru.urfu.mm.application.usecase.creategroup;

import java.util.UUID;

public record CreateGroupRequest(
        String number,
        int startYear,
        UUID programId
) {
}
