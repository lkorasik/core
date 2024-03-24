package ru.urfu.mm.application.usecase;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.mm.application.dsl.EntityDSL;
import ru.urfu.mm.application.exception.EmptyProgramNameException;
import ru.urfu.mm.application.gateway.ProgramGateway;

@ExtendWith(MockitoExtension.class)
public class CreateProgramTest {
    @Mock
    private ProgramGateway programGateway;

    /**
     * Создание программы
     */
    @Test
    public void createProgram() {
        String programName = EntityDSL.generateString();

        programGateway = Mockito.mock(ProgramGateway.class);

        CreateProgram createProgram = new CreateProgram(programGateway);

        createProgram.createProgram(programName);
    }

    /**
     * Попытка создать программу с некорректным именем
     */
    @Test
    public void createProgram_withEmptyName() {
        String programName = "    ";

        CreateProgram createProgram = new CreateProgram(programGateway);

        Assertions.assertThrows(EmptyProgramNameException.class, () -> createProgram.createProgram(programName));
    }
}
