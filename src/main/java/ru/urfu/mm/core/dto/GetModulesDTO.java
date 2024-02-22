package ru.urfu.mm.core.dto;

import java.util.List;
import java.util.UUID;

public class GetModulesDTO {
    private List<UUID> modulesIds;

    public GetModulesDTO(List<UUID> modulesIds) {
        this.modulesIds = modulesIds;
    }

    public GetModulesDTO() {
    }

    public List<UUID> getModulesIds() {
        return modulesIds;
    }
}
