package ru.urfu.mm.controller.program;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.application.usecase.get_available_years.GetStudyPlanResponse;
import ru.urfu.mm.controller.Endpoints;
import ru.urfu.mm.domain.BaseSyllabus;
import ru.urfu.mm.domain.StudentSyllabus;

import java.util.List;
import java.util.UUID;

@Tag(name = "Program", description = "Управление программами")
@RequestMapping(Endpoints.Program.BASE)
public interface ProgramControllerDescription {
    @GetMapping(Endpoints.Program.CURRENT)
    ProgramInfoDTO current();

    @GetMapping(Endpoints.Program.PROGRAM)
    FullProgramDTO getEducationalProgram(@RequestParam("id") UUID programId);

    @PutMapping(Endpoints.Program.PROGRAM)
    void updateEducationalProgram(@RequestBody UpdateProgramDTO dto);

    @PostMapping(Endpoints.Program.CREATE)
    void createEducationalProgram(@RequestBody CreateProgramDTO dto);

    @GetMapping(Endpoints.Program.ALL)
    List<ShortProgramDTO> getAll();

    @GetMapping(Endpoints.Program.AVAILABLE_YEARS)
    List<GetStudyPlanResponse> laod(@RequestParam("id") UUID id);

    @PostMapping(Endpoints.Program.PLAN)
    void saveStudyPlan(@RequestBody StudyPlanDTO dto);

    @PostMapping(Endpoints.Program.GET_PLAN)
    List<BaseSyllabus> getStudyPlan(@RequestBody GetStudyPlanDTO dto);

    @GetMapping(Endpoints.Program.GET_ACTUAL_YEARS)
    List<Integer> getActualYears(@RequestParam("programId") UUID programId);
}
