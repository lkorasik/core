package ru.urfu.mm.application.usecase.get_all_programs;

import ru.urfu.mm.application.gateway.ProgramGateway;

import java.util.List;

/**
 * Получить все программы
 * 1. Получаем список всех программ
 */
public class GetAllPrograms {
    private final ProgramGateway programGateway;

    public GetAllPrograms(ProgramGateway programGateway) {
        this.programGateway = programGateway;
    }

    public List<ProgramResponse> getAllPrograms() {
        return programGateway.getAll()
                .stream()
                .map(x -> new ProgramResponse(x.getId(), x.getName()))
                .toList();
    }
}
