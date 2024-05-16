package ru.urfu.mm.application.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.mm.application.dsl.ProgramDSL;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.application.usecase.get_all_programs.GetAllPrograms;
import ru.urfu.mm.application.usecase.get_all_programs.ProgramResponse;
import ru.urfu.mm.domain.EducationalProgram;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GetAllProgramsTest {
    @Mock
    private ProgramGateway programGateway;

    /**
     * Загрузка доступных образовательных программ.
     */
    @Test
    public void loadAvailablePrograms() {
        List<EducationalProgram> educationalPrograms = List.of(ProgramDSL.create(), ProgramDSL.create());

        Mockito.when(programGateway.getAll()).thenReturn(educationalPrograms);

        GetAllPrograms getAllPrograms = new GetAllPrograms(programGateway);

        List<ProgramResponse> availablePrograms = getAllPrograms.getAllPrograms();

        List<ProgramResponse> expectedProgram = educationalPrograms
                .stream()
                .map(x -> new ProgramResponse(x.getId(), x.getName()))
                .toList();

        Assertions.assertEquals(availablePrograms.size(), expectedProgram.size());
        availablePrograms.forEach(program -> Assertions.assertTrue(expectedProgram.contains(program)));
    }

    /**
     * Загрузка доступных образовательных программ. Программ нет.
     */
    @Test
    public void loadAvailablePrograms_empty() {
        List<EducationalProgram> educationalPrograms = List.of();

        Mockito.when(programGateway.getAll()).thenReturn(educationalPrograms);

        GetAllPrograms getAllPrograms = new GetAllPrograms(programGateway);

        List<ProgramResponse> availablePrograms = getAllPrograms.getAllPrograms();

        Assertions.assertTrue(availablePrograms.isEmpty());
    }
}
