package ru.urfu.mm.application.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.mm.application.gateway.TokenGateway;
import ru.urfu.mm.application.usecase.generatetoken.GenerateStudentRegistrationTokens;
import ru.urfu.mm.application.usecase.generatetoken.GenerateStudentRegistrationTokensRequest;
import ru.urfu.mm.application.usecase.generatetoken.IncorrectCountOfTokens;
import ru.urfu.mm.application.usecase.getgroup.GetGroup;
import ru.urfu.mm.application.usecase.getgroup.GroupNotFoundException;
import ru.urfu.mm.domain.Group;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class GenerateStudentRegistrationTokensTest {
    @Mock
    private TokenGateway tokenGateway;
    @Mock
    private GetGroup getGroup;

    /**
     * Генерируем 10 токенов.
     */
    @Test
    public void generateTokens() {
        UUID groupId = UUID.randomUUID();
        Group group = new Group(groupId, "МЕНМ-123123");
        int count = 10;

        List<UUID> tokens = Stream.generate(UUID::randomUUID).limit(count).toList();

        Mockito.when(getGroup.getGroup(groupId)).thenReturn(group);
        Mockito.when(tokenGateway.generateStudentRegistrationTokens(count, group)).thenReturn(tokens);

        GenerateStudentRegistrationTokens generateStudentRegistrationTokens =
                new GenerateStudentRegistrationTokens(tokenGateway, getGroup);

        GenerateStudentRegistrationTokensRequest request = new GenerateStudentRegistrationTokensRequest(count, groupId);
        List<UUID> uuids = generateStudentRegistrationTokens.generateTokens(request);

        Assertions.assertEquals(tokens, uuids);
    }


    /**
     * Генерируем 0 токенов. Ожидаем ошибку.
     */
    @Test
    public void generate0Tokens() {
        UUID groupId = UUID.randomUUID();
        Group group = new Group(groupId, "МЕНМ-123123");
        int count = 0;

        Mockito.when(getGroup.getGroup(groupId)).thenReturn(group);

        GenerateStudentRegistrationTokens generateStudentRegistrationTokens =
                new GenerateStudentRegistrationTokens(tokenGateway, getGroup);

        GenerateStudentRegistrationTokensRequest request = new GenerateStudentRegistrationTokensRequest(count, groupId);

        Assertions.assertThrows(
                IncorrectCountOfTokens.class,
                () -> generateStudentRegistrationTokens.generateTokens(request)
        );
    }


    /**
     * Генерируем -3 токена. Ожидаем ошибку.
     */
    @Test
    public void generateNegativeCountOfTokens() {
        UUID groupId = UUID.randomUUID();
        Group group = new Group(groupId, "МЕНМ-123123");
        int count = -3;

        Mockito.when(getGroup.getGroup(groupId)).thenReturn(group);

        GenerateStudentRegistrationTokens generateStudentRegistrationTokens =
                new GenerateStudentRegistrationTokens(tokenGateway, getGroup);

        GenerateStudentRegistrationTokensRequest request = new GenerateStudentRegistrationTokensRequest(count, groupId);

        Assertions.assertThrows(
                IncorrectCountOfTokens.class,
                () -> generateStudentRegistrationTokens.generateTokens(request)
        );
    }


    /**
     * Генерируем 3 токена. Группа не найдена. Ожидаем ошибку.
     */
    @Test
    public void generateTokensWithoutGroup() {
        UUID groupId = UUID.randomUUID();
        int count = -3;

        Mockito.when(getGroup.getGroup(groupId)).thenThrow(GroupNotFoundException.class);

        GenerateStudentRegistrationTokens generateStudentRegistrationTokens =
                new GenerateStudentRegistrationTokens(tokenGateway, getGroup);

        GenerateStudentRegistrationTokensRequest request = new GenerateStudentRegistrationTokensRequest(count, groupId);

        Assertions.assertThrows(
                GroupNotFoundException.class,
                () -> generateStudentRegistrationTokens.generateTokens(request)
        );
    }
}
