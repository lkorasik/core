package ru.urfu.mm.application.usecase.gettoken;

import ru.urfu.mm.application.gateway.TokenGateway;
import ru.urfu.mm.application.usecase.getgroup.GetGroup;
import ru.urfu.mm.domain.Group;

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

    public GetTokensForGroup(TokenGateway tokenGateway, GetGroup getGroup) {
        this.tokenGateway = tokenGateway;
        this.getGroup = getGroup;
    }

    public List<UUID> getTokensForGroup(GetTokensForGroupRequest request) {
        Group group = getGroup.getGroup(request.groupId());
        return tokenGateway.getTokensByGroup(group);
    }
}
