package ru.urfu.mm.controller.program;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.application.usecase.create_educational_program.CreateEducationalProgram;
import ru.urfu.mm.application.usecase.create_educational_program.CreateProgramRequest;
import ru.urfu.mm.application.usecase.create_syylabus.CourseSelectionDTO;
import ru.urfu.mm.application.usecase.create_syylabus.CreateBaseSyllabus;
import ru.urfu.mm.application.usecase.create_syylabus.CreateSyllabusRequest;
import ru.urfu.mm.application.usecase.create_syylabus.ModuleSelectionDTO;
import ru.urfu.mm.application.usecase.get_all_programs.GetAllPrograms;
import ru.urfu.mm.application.usecase.get_modules_by_syllabus.GetModulesBySyllabus;
import ru.urfu.mm.application.usecase.get_new_syllabus.GetSyllabus;
import ru.urfu.mm.application.usecase.get_new_syllabus.GlobalResponse;
import ru.urfu.mm.application.usecase.get_program_for_student.GetProgramForStudent;
import ru.urfu.mm.application.usecase.get_program_for_student.ProgramForStudentResponse;
import ru.urfu.mm.application.usecase.get_program_by_id.GetProgramById;
import ru.urfu.mm.application.usecase.get_available_years.GetAvailableYears;
import ru.urfu.mm.application.usecase.get_available_years.GetStudyPlanResponse;
import ru.urfu.mm.application.usecase.get_base_syllabus.GetAllSyllabi;
import ru.urfu.mm.application.usecase.update_program.UpdateProgram;
import ru.urfu.mm.application.usecase.update_program.UpdateProgramRequest;
import ru.urfu.mm.controller.AbstractAuthorizedController;
import ru.urfu.mm.domain.BaseSyllabus;
import ru.urfu.mm.domain.EducationalModule;
import ru.urfu.mm.domain.EducationalProgram;

import java.util.List;
import java.util.UUID;

@RestController
public class ProgramController extends AbstractAuthorizedController implements ProgramControllerDescription {
    private final Logger logger = LoggerFactory.getLogger(getClass());
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
    private GetAllSyllabi getAllSyllabi;
    @Autowired
    private CreateBaseSyllabus createBaseSyllabus;
    @Autowired
    private GetSyllabus getSyllabus;
    @Autowired
    private GetModulesBySyllabus getModulesBySyllabus;

    @Override
    public ProgramInfoDTO current() {
        ProgramForStudentResponse response = getProgramForStudent.getProgramForStudent(UUID.fromString(getUserToken()));
        return ProgramInfoDTO.from(response);
    }

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
    public void updateEducationalProgram(UpdateProgramDTO dto) {
        UpdateProgramRequest request = new UpdateProgramRequest(dto.id(), dto.name(), dto.trainingDirection());
        updateProgram.updateProgram(request);
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

    @Override
    public List<GetStudyPlanResponse> laod(UUID id) {
        return getAvailableYears.getStudyPlan(id);
    }

    @Override
    public void saveSyllabus(SyllabusDTO dto) {
        List<ModuleSelectionDTO> modules = dto.modules()
                .stream()
                .map(x -> {
                    List<CourseSelectionDTO> courses = x.courses()
                            .stream()
                            .map(y -> new CourseSelectionDTO(y.courseId(), y.semesterId()))
                            .toList();
                    return new ModuleSelectionDTO(x.moduleId(), courses);
                })
                .toList();
        CreateSyllabusRequest request = new CreateSyllabusRequest(dto.programId(), dto.firstSemesterId(), modules);
        createBaseSyllabus.createStudyPlan(request);
    }

    @Override
    public List<BaseSyllabus> getAllSyllabi(UUID programId) {
        return getAllSyllabi.getStudyPlan(programId);
    }

    @GetMapping("/getNewPlan")
    public GlobalResponse getSyllabus(@RequestParam("programId") UUID programId, @RequestParam("startYear") int startYear) {
        return getSyllabus.getSyllabus(programId, startYear);
    }

    @GetMapping("/fff")
    public EducationalModule getSyllabus2(@RequestParam("programId") UUID programId, @RequestParam("startYear") int startYear) {
        return getModulesBySyllabus.getModulesBySyllabus(programId, startYear);
    }
}
