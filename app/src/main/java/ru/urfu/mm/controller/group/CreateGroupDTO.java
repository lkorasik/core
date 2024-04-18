package ru.urfu.mm.controller.group;

import java.util.UUID;

public record CreateGroupDTO(
        String number,
        int startYear,
        UUID programId
) {
}
