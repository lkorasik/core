package ru.urfu.mm.controller.program;

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
import ru.urfu.mm.application.usecase.get_base_syllabus.GetAllSyllabi;
import ru.urfu.mm.application.usecase.update_program.UpdateProgram;
import ru.urfu.mm.application.usecase.update_program.UpdateProgramRequest;
import ru.urfu.mm.controller.AbstractAuthorizedController;
import ru.urfu.mm.domain.BaseSyllabus;
import ru.urfu.mm.domain.EducationalProgram;
import ru.urfu.mm.domain.StudentSyllabus;

import java.util.List;
import java.util.UUID;

@RestController
public class ProgramController extends AbstractAuthorizedController implements ProgramControllerDescription {
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
    private GetAllSyllabi getAllSyllabi;

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
    public void saveStudyPlan(StudyPlanDTO dto) {
        // todo: реализуй сохранение учебного плана
        System.out.println("Receive: " + dto);
    }

    @Override
    public List<BaseSyllabus> getAllSyllabi(UUID programId) {
        return getAllSyllabi.getStudyPlan(programId);
    }
}
