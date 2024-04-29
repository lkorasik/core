package ru.urfu.mm.application.usecase.get_token;

import ru.urfu.mm.application.gateway.StudentGateway;
import ru.urfu.mm.application.usecase.get_group.GetGroup;
import ru.urfu.mm.domain.AcademicGroup;

import java.util.List;

/**
 * Получить список токенов для группы.
 * 1. Находим группу {@link GetGroup}
 * 2. Получаем все токены по группе.
 */
public class GetTokensForGroup {
    private final GetGroup getGroup;
    private final StudentGateway studentGateway;

    public GetTokensForGroup(GetGroup getGroup, StudentGateway studentGateway) {
        this.getGroup = getGroup;
        this.studentGateway = studentGateway;
    }

    public List<GetTokensForGroupResponse> getTokensForGroup(GetTokensForGroupRequest request) {
        return getGroup.getGroup(request.groupId())
                .getStudents()
                .stream()
                .map(x -> new GetTokensForGroupResponse(x.getId(), x.getAccount() != null))
                .toList();
    }
}
