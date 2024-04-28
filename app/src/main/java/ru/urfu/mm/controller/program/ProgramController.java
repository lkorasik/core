package ru.urfu.mm.controller.program;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.application.usecase.createprogram.CreateProgram;
import ru.urfu.mm.application.usecase.createprogram.CreateProgramRequest;
import ru.urfu.mm.application.usecase.getallprograms.GetAllPrograms;
import ru.urfu.mm.application.usecase.GetEducationalProgram;
import ru.urfu.mm.application.usecase.get_program_for_student.GetProgramForStudent;
import ru.urfu.mm.application.usecase.get_program_for_student.ProgramForStudentResponse;
import ru.urfu.mm.application.usecase.getprogrambyid.GetProgramById;
import ru.urfu.mm.application.usecase.getavailableyears.GetAvailableYears;
import ru.urfu.mm.application.usecase.getavailableyears.GetStudyPlanResponse;
import ru.urfu.mm.application.usecase.getstudyplan.GetStudyPlan;
import ru.urfu.mm.application.usecase.updateprogram.UpdateProgram;
import ru.urfu.mm.application.usecase.updateprogram.UpdateProgramRequest;
import ru.urfu.mm.controller.AbstractAuthorizedController;
import ru.urfu.mm.domain.Program;
import ru.urfu.mm.domain.StudyPlan;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/programs")
public class ProgramController extends AbstractAuthorizedController {
    @Autowired
    private GetEducationalProgram getEducationalProgram;
    @Autowired
    private GetAllPrograms getAvailablePrograms;
    @Autowired
    private GetProgramForStudent getProgramForStudent;
    @Autowired
    private CreateProgram createProgram;
    @Autowired
    private GetProgramById getProgramById;
    @Autowired
    private UpdateProgram updateProgram;
    @Autowired
    private GetAvailableYears getAvailableYears;
    @Autowired
    private GetStudyPlan getStudyPlan;

    @GetMapping("/current")
    public ProgramInfoDTO current() {
        ProgramForStudentResponse response = getProgramForStudent.getProgramForStudent(UUID.fromString(getUserToken()));
        return ProgramInfoDTO.from(response);
    }

    @GetMapping("/program")
    public FullProgramDTO getEducationalProgram(@RequestParam("id") UUID programId) throws JsonProcessingException {
        Program program = getProgramById.getProgramById(programId);
        return new FullProgramDTO(program.getId(), program.getName(), program.getTrainingDirection());
    }

    @PutMapping("/program")
    public void updateEducationalProgram(@RequestBody UpdateProgramDTO dto) {
        UpdateProgramRequest request = new UpdateProgramRequest(dto.id(), dto.name(), dto.trainingDirection());
        updateProgram.updateProgram(request);
    }

    @PostMapping("/create")
    public void createEducationalProgram(@RequestBody CreateProgramDTO dto) {
        CreateProgramRequest request = new CreateProgramRequest(dto.name(), dto.trainingDirection());
        createProgram.createProgram(request);
    }

    @GetMapping("/all")
    public List<ShortProgramDTO> getAvailablePrograms() {
        return getAvailablePrograms.getAllPrograms()
                .stream()
                .map(x -> new ShortProgramDTO(x.id(), x.name()))
                .toList();
    }

    @GetMapping("/availableYears")
    public List<GetStudyPlanResponse> laod(@RequestParam("id") UUID id) {
        return getAvailableYears.getStudyPlan(id);
    }

    @PostMapping("/plan")
    public void saveStudyPlan(@RequestBody StudyPlanDTO dto) {
        System.out.println("Receive: " + dto);
    }

    @PostMapping("/getPlan")
    public StudyPlan getStudyPlan(@RequestBody GetStudyPlanDTO dto) {
        return getStudyPlan.getStudyPlan(dto.programId(), dto.startYear());
    }
}
