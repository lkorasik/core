package ru.urfu.mm.controller.group;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.controller.Endpoints;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

@Tag(name = "Group", description = "Управление академическими группами")
@RequestMapping(Endpoints.Group.BASE)
public interface GroupControllerDescription {
    @PostMapping(Endpoints.Group.GROUP)
    void createGroup(@RequestBody CreateGroupDTO dto);

    @Operation(summary = "Получить группу по идентификатору")
    @GetMapping(Endpoints.Group.GROUP_BY_ID)
    GroupDTO getGroup(@RequestParam("groupId") UUID groupId);

    @PostMapping(Endpoints.Group.TOKEN)
    List<UUID> generateTokens(@RequestBody GenerateTokenDTO generateTokenDTO);

    @Operation(summary = "Получить токены")
    @GetMapping(Endpoints.Group.TOKEN)
    List<TokenStatusDTO> getTokens(@RequestParam("groupId") UUID groupId);

    @GetMapping(Endpoints.Group.TOKEN_FILE)
    ResponseEntity<InputStreamResource> getFile(@RequestParam("groupId") UUID groupId) throws FileNotFoundException;
}
