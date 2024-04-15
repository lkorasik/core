package ru.urfu.mm.controller.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.mm.application.usecase.getgroups.GetGroupForEducationalProgram;
import ru.urfu.mm.controller.AbstractAuthorizedController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/groups")
public class GroupController extends AbstractAuthorizedController {
    @Autowired
    private GetGroupForEducationalProgram getGroupForEducationalProgram;

    @GetMapping("/group")
        public List<GroupDTO> getGroupsByEducationalProgram(@RequestParam("programId") UUID programId) {
        return getGroupForEducationalProgram.getGroupForEducationalProgram(programId)
                .stream()
                .map(x -> new GroupDTO(x.id(), x.number()))
                .toList();
    }
}
