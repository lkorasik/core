package ru.urfu.mm.controller.syllabus;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.mm.application.usecase.create_syylabus.CourseSelectionDTO;
import ru.urfu.mm.application.usecase.create_syylabus.CreateBaseSyllabus;
import ru.urfu.mm.application.usecase.create_syylabus.CreateSyllabusRequest;
import ru.urfu.mm.application.usecase.create_syylabus.ModuleSelectionDTO;
import ru.urfu.mm.application.usecase.get_base_syllabus.GetAllSyllabi;
import ru.urfu.mm.controller.program.SyllabusDTO;
import ru.urfu.mm.domain.BaseSyllabus;

import java.util.List;
import java.util.UUID;


@RestController
public class SyllabusController implements SyllabusControllerDescription {
    @Autowired
    private CreateBaseSyllabus createBaseSyllabus;
    @Autowired
    private GetAllSyllabi getAllSyllabi;

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
}
