package ru.urfu.mm.controller.program;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.application.usecase.create_educational_program.CreateEducationalProgram;
import ru.urfu.mm.application.usecase.create_educational_program.CreateProgramRequest;
import ru.urfu.mm.application.usecase.get_all_programs.GetAllPrograms;
import ru.urfu.mm.application.usecase.get_program_by_id.GetProgramById;
import ru.urfu.mm.controller.AbstractAuthorizedController;
import ru.urfu.mm.domain.EducationalProgram;

import java.util.List;
import java.util.UUID;

@RestController
public class ProgramController extends AbstractAuthorizedController implements ProgramControllerDescription {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private GetAllPrograms getAvailablePrograms;
    @Autowired
    private CreateEducationalProgram createEducationalProgram;
    @Autowired
    private GetProgramById getProgramById;

    @Override
    public FullProgramDTO getEducationalProgram(UUID programId) {
        EducationalProgram program = getProgramById.getProgramById(programId);
        List<GroupDTO> groups = program.getAcademicGroups()
                .stream()
                .map(x -> new GroupDTO(x.getId(), x.getNumber()))
                .toList();
        return new FullProgramDTO(program.getId(), program.getName(), program.getTrainingDirection(), groups);
    }

    @Override
    public void createEducationalProgram(CreateProgramDTO dto) {
        CreateProgramRequest request = new CreateProgramRequest(dto.name(), dto.trainingDirection());
        createEducationalProgram.createProgram(request);
    }

    @Override
    public List<ShortProgramDTO> getAll() {
        return getAvailablePrograms.getAllPrograms()
                .stream()
                .map(x -> new ShortProgramDTO(x.id(), x.name()))
                .toList();
    }
}
