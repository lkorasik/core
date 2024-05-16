package ru.urfu.mm.controller.program;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.application.usecase.create_educational_program.CreateEducationalProgram;
import ru.urfu.mm.application.usecase.create_educational_program.CreateProgramRequest;
import ru.urfu.mm.application.usecase.get_all_programs.GetAllPrograms;
import ru.urfu.mm.application.usecase.get_program_for_student.GetProgramForStudent;
import ru.urfu.mm.application.usecase.get_program_for_student.ProgramForStudentResponse;
import ru.urfu.mm.application.usecase.get_program_by_id.GetProgramById;
import ru.urfu.mm.application.usecase.get_available_years.GetAvailableYears;
import ru.urfu.mm.application.usecase.get_available_years.GetStudyPlanResponse;
import ru.urfu.mm.application.usecase.get_study_plan.GetStudyPlan;
import ru.urfu.mm.application.usecase.update_program.UpdateProgram;
import ru.urfu.mm.application.usecase.update_program.UpdateProgramRequest;
import ru.urfu.mm.controller.AbstractAuthorizedController;
import ru.urfu.mm.controller.Endpoints;
import ru.urfu.mm.domain.EducationalProgram;
import ru.urfu.mm.domain.Syllabus;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(Endpoints.Program.BASE)
public class ProgramController extends AbstractAuthorizedController {
//    @Autowired
//    private GetEducationalProgram getEducationalProgram;
    @Autowired
    private GetAllPrograms getAvailablePrograms;
    @Autowired
    private GetProgramForStudent getProgramForStudent;
    @Autowired
    private CreateEducationalProgram createEducationalProgram;
    @Autowired
    private GetProgramById getProgramById;
    @Autowired
    private UpdateProgram updateProgram;
    @Autowired
    private GetAvailableYears getAvailableYears;
    @Autowired
    private GetStudyPlan getStudyPlan;

    @GetMapping(Endpoints.Program.CURRENT)
    public ProgramInfoDTO current() {
        ProgramForStudentResponse response = getProgramForStudent.getProgramForStudent(UUID.fromString(getUserToken()));
        return ProgramInfoDTO.from(response);
    }

    @GetMapping(Endpoints.Program.PROGRAM)
    public FullProgramDTO getEducationalProgram(@RequestParam("id") UUID programId) throws JsonProcessingException {
        EducationalProgram educationalProgram = getProgramById.getProgramById(programId);
        return new FullProgramDTO(educationalProgram.getId(), educationalProgram.getName(), educationalProgram.getTrainingDirection());
    }

    @PutMapping(Endpoints.Program.PROGRAM)
    public void updateEducationalProgram(@RequestBody UpdateProgramDTO dto) {
        UpdateProgramRequest request = new UpdateProgramRequest(dto.id(), dto.name(), dto.trainingDirection());
        updateProgram.updateProgram(request);
    }

    @PostMapping(Endpoints.Program.CREATE)
    public void createEducationalProgram(@RequestBody CreateProgramDTO dto) {
        CreateProgramRequest request = new CreateProgramRequest(dto.name(), dto.trainingDirection());
        createEducationalProgram.createProgram(request);
    }

    @GetMapping(Endpoints.Program.ALL)
    public List<ShortProgramDTO> getAll() {
        return getAvailablePrograms.getAllPrograms()
                .stream()
                .map(x -> new ShortProgramDTO(x.id(), x.name()))
                .toList();
    }

    @GetMapping(Endpoints.Program.AVAILABLE_YEARS)
    public List<GetStudyPlanResponse> laod(@RequestParam("id") UUID id) {
        return getAvailableYears.getStudyPlan(id);
    }

    @PostMapping(Endpoints.Program.PLAN)
    public void saveStudyPlan(@RequestBody StudyPlanDTO dto) {
        // todo: реализуй сохранение учебного плана
        System.out.println("Receive: " + dto);
    }

    @PostMapping(Endpoints.Program.GET_PLAN)
    public Syllabus getStudyPlan(@RequestBody GetStudyPlanDTO dto) {
        return getStudyPlan.getStudyPlan(dto.programId(), dto.startYear());
    }
}
