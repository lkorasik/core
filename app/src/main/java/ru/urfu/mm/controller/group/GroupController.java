package ru.urfu.mm.controller.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.application.usecase.create_group.CreateGroup;
import ru.urfu.mm.application.usecase.create_group.CreateGroupRequest;
import ru.urfu.mm.application.usecase.download_tokens.DownloadTokens;
import ru.urfu.mm.application.usecase.download_tokens.DownloadTokensRequest;
import ru.urfu.mm.application.usecase.generate_token.GenerateStudentRegistrationTokens;
import ru.urfu.mm.application.usecase.generate_token.GenerateStudentRegistrationTokensRequest;
import ru.urfu.mm.application.usecase.get_group.GetGroup;
import ru.urfu.mm.application.usecase.get_groups.GetGroupForEducationalProgram;
import ru.urfu.mm.application.usecase.get_token.GetTokensForGroup;
import ru.urfu.mm.application.usecase.get_token.GetTokensForGroupRequest;
import ru.urfu.mm.controller.AbstractAuthorizedController;
import ru.urfu.mm.domain.Group;

import java.io.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/groups")
public class GroupController extends AbstractAuthorizedController {
    @Autowired
    private GetGroupForEducationalProgram getGroupForEducationalProgram;
    @Autowired
    private CreateGroup createGroup;
    @Autowired
    private GetGroup getGroup;
    @Autowired
    private GenerateStudentRegistrationTokens generateStudentRegistrationTokens;
    @Autowired
    private GetTokensForGroup getTokensForGroup;
    @Autowired
    private DownloadTokens downloadTokens;

    @GetMapping("/group")
    public List<GroupDTO> getGroupsByEducationalProgram(@RequestParam("programId") UUID programId) {
        return getGroupForEducationalProgram.getGroupForEducationalProgram(programId)
                .stream()
                .map(x -> new GroupDTO(x.id(), x.number()))
                .toList();
    }

    @PostMapping("/group")
    public void createGroup(@RequestBody CreateGroupDTO dto) {
        CreateGroupRequest request = new CreateGroupRequest(dto.number(), dto.year(), dto.programId());
        createGroup.createGroup(request);
    }

    @GetMapping("/groupById")
    public GroupDTO getGroup(@RequestParam("groupId") UUID groupId) {
        Group group = getGroup.getGroup(groupId);
        return new GroupDTO(group.getId(), group.getNumber());
    }

    @PostMapping("/token")
    public List<UUID> generateTokens(@RequestBody GenerateTokenDTO generateTokenDTO) {
        GenerateStudentRegistrationTokensRequest request =
                new GenerateStudentRegistrationTokensRequest(generateTokenDTO.count(), generateTokenDTO.groupId());
        return generateStudentRegistrationTokens.generateTokens(request);
    }

    @GetMapping("/token")
    public List<TokenStatusDTO> getTokens(@RequestParam("groupId") UUID groupId) {
        GetTokensForGroupRequest request = new GetTokensForGroupRequest(groupId);
        return getTokensForGroup.getTokensForGroup(request)
                .stream()
                .map(x -> new TokenStatusDTO(x.token(), x.isActivated()))
                .toList();
    }

    @GetMapping("/token_file")
    public ResponseEntity<InputStreamResource> getFile(@RequestParam("groupId") UUID groupId) throws FileNotFoundException {
        DownloadTokensRequest request = new DownloadTokensRequest(groupId);

        File file = downloadTokens.downloadTokens(request);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
