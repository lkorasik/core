package ru.urfu.mm.controller.program;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.application.usecase.GetAllEducationalPrograms;
import ru.urfu.mm.application.usecase.GetEducationalProgram;
import ru.urfu.mm.application.usecase.get_program_for_student.GetProgramForStudent;
import ru.urfu.mm.application.usecase.get_program_for_student.ProgramForStudentResponse;
import ru.urfu.mm.controller.AbstractAuthorizedController;
import ru.urfu.mm.service.ProgramService;

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
    @Autowired
    private GetProgramForStudent getProgramForStudent;

    @GetMapping("/current")
    public ProgramInfoDTO current() {
        ProgramForStudentResponse response = getProgramForStudent.getProgramForStudent(UUID.fromString(getUserToken()));
        return ProgramInfoDTO.from(response);
    }

    @GetMapping
    public List<ProgramInfoDTO> getEducationalProgram() {
        return getAllEducationalPrograms.getAllPrograms()
                .stream()
                .map(x -> new ProgramInfoDTO(
                        x.getId(),
                        x.getName(),
                        List.of(),
                        List.of()
                ))
                .toList();
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
