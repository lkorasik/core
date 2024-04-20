package ru.urfu.mm.controller.group;

import java.util.UUID;

public record GenerateTokenDTO(
        int count,
        UUID groupId
) {
}
