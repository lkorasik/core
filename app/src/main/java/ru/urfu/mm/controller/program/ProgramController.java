package ru.urfu.mm.controller.program;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.application.usecase.GetAllEducationalPrograms;
import ru.urfu.mm.application.usecase.GetEducationalProgram;
import ru.urfu.mm.controller.AbstractAuthorizedController;
import ru.urfu.mm.domain.EducationalProgram;
import ru.urfu.mm.service.ProgramService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/programs")
public class ProgramController extends AbstractAuthorizedController {
    @Autowired
    private ProgramService programService;
    @Autowired
    private GetEducationalProgram getEducationalProgram;
    @Autowired
    private GetAllEducationalPrograms getAllEducationalPrograms;

    @GetMapping("/current")
    public ProgramInfoDTO current() {
        EducationalProgram program = getEducationalProgram.getEducationalProgram(UUID.fromString(getUserToken()));
        return new ProgramInfoDTO(program.getId(), program.getName());
    }

    @GetMapping
    public List<ProgramInfoDTO> getEducationalProgram() {
        var programs = getAllEducationalPrograms.getAllPrograms();

        var result = new ArrayList<ProgramInfoDTO>();
        for(var program: programs) {
            var dto = new ProgramInfoDTO(
                    program.getId(),
                    program.getName()
            );

            result.add(dto);
        }

        return result;
    }

    @PostMapping("/program")
    public FullProgramDTO getEducationalProgram(@RequestBody ProgramIdDTO programIdDto) throws JsonProcessingException {
        return programService.getEducationalProgramById(programIdDto.id());
    }

    @PostMapping("/create")
    public void createEducationalProgram(@RequestBody CreateProgramDTO educationalProgramDTO) throws JsonProcessingException {
        programService.createEducationalProgram(educationalProgramDTO);
    }
}
