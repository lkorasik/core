package ru.urfu.mm.application.usecase.getallprograms;

import java.util.UUID;

public record ProgramResponse(
        UUID id,
        String name
) {
}
