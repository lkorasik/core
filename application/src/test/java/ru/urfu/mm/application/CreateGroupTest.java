package ru.urfu.mm.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.mm.application.dsl.EntityDSL;
import ru.urfu.mm.application.exception.IncorrectRecommendedCreditsCountException;
import ru.urfu.mm.application.exception.IncorrectSemestersCountException;
import ru.urfu.mm.application.gateway.GroupGateway;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.application.usecase.CreateGroup;
import ru.urfu.mm.domain.Program;
import ru.urfu.mm.domain.Semester;

import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class CreateGroupTest {
    @Mock
    private GroupGateway groupGateway;
    @Mock
    private ProgramGateway programGateway;

    /**
     * Создание группы
     */
    @Test
    public void createGroup() {
        Program program = EntityDSL.generateProgram();
        String groupName = EntityDSL.generateString();
        List<Semester> semesters = EntityDSL.createYear(2023);
        Map<Semester, Integer> recommendedCredits = Map.of(
                semesters.get(0), 3,
                semesters.get(1), 3,
                semesters.get(2), 3,
                semesters.get(3), 3
        );

        CreateGroup createGroup = new CreateGroup(groupGateway, programGateway);

        createGroup.createGroup(program, groupName, semesters, recommendedCredits);
    }

    /**
     * Создание группы. Семестров меньше, чем должно быть.
     */
    @Test
    public void createGroup_semestersWithLessThan4() {
        Program program = EntityDSL.generateProgram();
        String groupName = EntityDSL.generateString();
        List<Semester> semesters = List.of(
                EntityDSL.createFallSemester(2023),
                EntityDSL.createSpringSemester(2023),
                EntityDSL.createFallSemester(2024)
        );
        Map<Semester, Integer> recommendedCredits = Map.of();

        CreateGroup createGroup = new CreateGroup(groupGateway, programGateway);

        Assertions.assertThrows(IncorrectSemestersCountException.class, () -> createGroup.createGroup(program, groupName, semesters, recommendedCredits));
    }

    /**
     * Создание группы. Семестров больше, чем нужно.
     */
    @Test
    public void createGroup_semestersWithMoreThan4() {
        Program program = EntityDSL.generateProgram();
        String groupName = EntityDSL.generateString();
        List<Semester> semesters = List.of(
                EntityDSL.createFallSemester(2023),
                EntityDSL.createSpringSemester(2024),
                EntityDSL.createFallSemester(2024),
                EntityDSL.createSpringSemester(2025),
                EntityDSL.createFallSemester(2025)
        );
        Map<Semester, Integer> recommendedCredits = Map.of();

        CreateGroup createGroup = new CreateGroup(groupGateway, programGateway);

        Assertions.assertThrows(IncorrectSemestersCountException.class, () -> createGroup.createGroup(program, groupName, semesters, recommendedCredits));
    }

    /**
     * Создание группы. Семестров меньше, чем должно быть.
     */
    @Test
    public void createGroup_recommendedCreditsWithLessThan4() {
        Program program = EntityDSL.generateProgram();
        String groupName = EntityDSL.generateString();
        List<Semester> semesters = EntityDSL.createYear(2023);
        Map<Semester, Integer> recommendedCredits = Map.of(
                EntityDSL.createFallSemester(2023), 3,
                EntityDSL.createSpringSemester(2024), 3,
                EntityDSL.createFallSemester(2024), 3
        );

        CreateGroup createGroup = new CreateGroup(groupGateway, programGateway);

        Assertions.assertThrows(IncorrectSemestersCountException.class, () -> createGroup.createGroup(program, groupName, semesters, recommendedCredits));
    }

    /**
     * Создание группы. Семестров меньше, чем должно быть.
     */
    @Test
    public void createGroup_recommendedCreditsWithMoreThan4() {
        Program program = EntityDSL.generateProgram();
        String groupName = EntityDSL.generateString();
        List<Semester> semesters = EntityDSL.createYear(2023);
        Map<Semester, Integer> recommendedCredits = Map.of(
                EntityDSL.createFallSemester(2023), 3,
                EntityDSL.createSpringSemester(2024), 3,
                EntityDSL.createFallSemester(2024), 3,
                EntityDSL.createSpringSemester(2025), 3,
                EntityDSL.createFallSemester(2025), 3
        );

        CreateGroup createGroup = new CreateGroup(groupGateway, programGateway);

        Assertions.assertThrows(IncorrectSemestersCountException.class, () -> createGroup.createGroup(program, groupName, semesters, recommendedCredits));
    }

    /**
     * Создание группы. Семестров меньше, чем должно быть.
     */
    @Test
    public void createGroup_recommendedCreditsNegative() {
        Program program = EntityDSL.generateProgram();
        String groupName = EntityDSL.generateString();
        List<Semester> semesters = EntityDSL.createYear(2023);
        Map<Semester, Integer> recommendedCredits = Map.of(
                EntityDSL.createFallSemester(2023), 3,
                EntityDSL.createSpringSemester(2024), 0,
                EntityDSL.createFallSemester(2024), 3,
                EntityDSL.createSpringSemester(2025), 3
        );

        CreateGroup createGroup = new CreateGroup(groupGateway, programGateway);

        Assertions.assertThrows(IncorrectRecommendedCreditsCountException.class, () -> createGroup.createGroup(program, groupName, semesters, recommendedCredits));
    }
}
