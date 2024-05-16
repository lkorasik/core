package ru.urfu.mm.application.usecase.get_groups;

import java.util.UUID;

public record GroupResponse(
        UUID id,
        String number
) {
}
