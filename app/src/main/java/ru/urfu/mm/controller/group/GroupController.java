package ru.urfu.mm.controller.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.application.usecase.create_group.CreateGroup;
import ru.urfu.mm.application.usecase.download_tokens.DownloadTokens;
import ru.urfu.mm.application.usecase.download_tokens.DownloadTokensRequest;
import ru.urfu.mm.application.usecase.generate_student_registration_token.GenerateStudentRegistrationToken;
import ru.urfu.mm.application.usecase.get_group.GetAcademicGroup;
import ru.urfu.mm.application.usecase.get_groups.GetGroupsByEducationalProgram;
import ru.urfu.mm.application.usecase.get_token.GetTokensForGroup;
import ru.urfu.mm.application.usecase.get_token.GetTokensForGroupRequest;
import ru.urfu.mm.controller.AbstractAuthorizedController;
import ru.urfu.mm.controller.Endpoints;
import ru.urfu.mm.domain.AcademicGroup;

import java.io.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(Endpoints.Group.BASE)
public class GroupController extends AbstractAuthorizedController {
    @Autowired
    private GetGroupsByEducationalProgram getGroupsByEducationalProgram;
    @Autowired
    private CreateGroup createGroup;
    @Autowired
    private GetAcademicGroup getAcademicGroup;
    @Autowired
    private GenerateStudentRegistrationToken generateStudentRegistrationToken;
    @Autowired
    private GetTokensForGroup getTokensForGroup;
    @Autowired
    private DownloadTokens downloadTokens;

    @GetMapping(Endpoints.Group.GROUPS_BY_PROGRAM)
    public List<GroupDTO> getGroupsByEducationalProgram(@RequestParam("programId") UUID programId) {
        return getGroupsByEducationalProgram.getGroupsByEducationalProgram(programId)
                .stream()
                .map(x -> new GroupDTO(x.id(), x.number()))
                .toList();
    }

    @PostMapping(Endpoints.Group.GROUP)
    public void createGroup(@RequestBody CreateGroupDTO dto) {
        createGroup.createGroup(dto.toRequest());
    }

    @GetMapping(Endpoints.Group.GROUP_BY_ID)
    public GroupDTO getGroup(@RequestParam("groupId") UUID groupId) {
        AcademicGroup academicGroup = getAcademicGroup.getGroup(groupId);
        return new GroupDTO(academicGroup.getId(), academicGroup.getNumber());
    }

    @PostMapping(Endpoints.Group.TOKEN)
    public List<UUID> generateTokens(@RequestBody GenerateTokenDTO generateTokenDTO) {
        return generateStudentRegistrationToken.generateTokens(generateTokenDTO.groupId(), generateTokenDTO.count());
    }

    @GetMapping(Endpoints.Group.TOKEN)
    public List<TokenStatusDTO> getTokens(@RequestParam("groupId") UUID groupId) {
        GetTokensForGroupRequest request = new GetTokensForGroupRequest(groupId);
        return getTokensForGroup.getTokensForGroup(request)
                .stream()
                .map(x -> new TokenStatusDTO(x.token(), x.isActivated()))
                .toList();
    }

    @GetMapping(Endpoints.Group.TOKEN_FILE)
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
