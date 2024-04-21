package ru.urfu.mm.application.usecase.gettoken;

import ru.urfu.mm.application.gateway.StudentGateway;
import ru.urfu.mm.application.gateway.TokenGateway;
import ru.urfu.mm.application.usecase.getgroup.GetGroup;
import ru.urfu.mm.domain.Group;
import ru.urfu.mm.domain.Student;

import java.util.List;
import java.util.UUID;

/**
 * Получить список токенов для группы.
 * 1. Находим группу {@link GetGroup}
 * 2. Получаем все токены по группе.
 */
public class GetTokensForGroup {
    private final TokenGateway tokenGateway;
    private final GetGroup getGroup;
    private final StudentGateway studentGateway;

    public GetTokensForGroup(TokenGateway tokenGateway, GetGroup getGroup, StudentGateway studentGateway) {
        this.tokenGateway = tokenGateway;
        this.getGroup = getGroup;
        this.studentGateway = studentGateway;
    }

    public List<UUID> getTokensForGroup(GetTokensForGroupRequest request) {
        Group group = getGroup.getGroup(request.groupId());
        return studentGateway.findAllStudentsByGroup(group)
                .stream()
                .map(Student::getLogin)
                .toList();
    }
}
