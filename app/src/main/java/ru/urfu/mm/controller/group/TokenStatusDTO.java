package ru.urfu.mm.controller.group;

import java.util.UUID;

public record TokenStatusDTO(
        UUID token,
        boolean isActivated
) {
}
