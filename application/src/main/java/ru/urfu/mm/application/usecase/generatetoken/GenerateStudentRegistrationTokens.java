package ru.urfu.mm.application.usecase.generatetoken;

import ru.urfu.mm.application.gateway.TokenGateway;
import ru.urfu.mm.application.usecase.getgroup.GetGroup;
import ru.urfu.mm.domain.Group;

import java.util.List;
import java.util.UUID;

/**
 * Сгенерировать токены для студентов.
 * 1. Находим группу {@link GetGroup}
 * 2. Если число запрошенных токенов равно нулю или меньше нуля, то кидаем ошибку.
 * 2. Генерируем нужное число токенов
 */
public class GenerateStudentRegistrationTokens {
    private final TokenGateway tokenGateway;
    private final GetGroup getGroup;

    public GenerateStudentRegistrationTokens(TokenGateway tokenGateway, GetGroup getGroup) {
        this.tokenGateway = tokenGateway;
        this.getGroup = getGroup;
    }

    public List<UUID> generateTokens(GenerateStudentRegistrationTokensRequest request) {
        Group group = getGroup.getGroup(request.groupId());

        if (request.tokenCount() <= 0) {
            throw new IncorrectCountOfTokens(request.tokenCount());
        }

        return tokenGateway.generateStudentRegistrationTokens(request.tokenCount(), group);
    }
}
