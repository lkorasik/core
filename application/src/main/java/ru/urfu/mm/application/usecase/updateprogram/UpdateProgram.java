package ru.urfu.mm.application.usecase.updateprogram;

import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.Program;

/**
 * Обновить программу.
 * 1. Находим программу. Если не нашли, то кидаем ошибку.
 * 2. Обновляем поля в программе.
 * 3. Сохраняем программу.
 */
public class UpdateProgram {
    private final ProgramGateway programGateway;

    public UpdateProgram(ProgramGateway programGateway) {
        this.programGateway = programGateway;
    }

    public void updateProgram(UpdateProgramRequest request) {
        Program program = programGateway
                .findById(request.id())
                .orElseThrow(() -> new EducationalProgramNotFoundException(request.id()));

        Program newProgram = new Program(
                program.getId(),
                request.name(),
                request.trainingDirection(),
                program.getGroups(),
                program.getFirstRecommendedCredits(),
                program.getSecondRecommendedCredits(),
                program.getThirdRecommendedCredits(),
                program.getFourthRecommendedCredits()
        );

        programGateway.save(newProgram);
    }
}
