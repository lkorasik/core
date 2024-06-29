package ru.urfu.mm.application.usecase.get_token;

import ru.urfu.mm.application.exception.NotImplementedException;
import ru.urfu.mm.application.gateway.StudentGateway;
import ru.urfu.mm.application.usecase.get_group.GetAcademicGroup;

import java.util.List;

/**
 * Получить список токенов для группы.
 * 1. Находим группу {@link GetAcademicGroup}
 * 2. Получаем все токены по группе.
 */
public class GetTokensForGroup {
    private final GetAcademicGroup getAcademicGroup;

    public GetTokensForGroup(GetAcademicGroup getAcademicGroup) {
        this.getAcademicGroup = getAcademicGroup;
    }

    public List<GetTokensForGroupResponse> getTokensForGroup(GetTokensForGroupRequest request) {
        return getAcademicGroup.getGroup(request.groupId())
                .getStudents()
                .stream()
                .map(x -> new GetTokensForGroupResponse(x.getId(), x.getAccount() != null))
                .toList();
    }
}
