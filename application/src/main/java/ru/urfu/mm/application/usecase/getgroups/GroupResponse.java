package ru.urfu.mm.application.usecase.getgroups;

import java.util.UUID;

public record GroupResponse(
        UUID id,
        String number
) {
}
