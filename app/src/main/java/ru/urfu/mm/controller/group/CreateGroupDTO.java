package ru.urfu.mm.controller.group;

import ru.urfu.mm.application.usecase.create_group.CreateGroupRequest;

import java.util.UUID;

public record CreateGroupDTO(
        String number,
        int year,
        UUID programId
) {
    public CreateGroupRequest toRequest() {
        return new CreateGroupRequest(number, year, programId);
    }
}
