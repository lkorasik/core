package ru.urfu.mm.controller.semester;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.mm.application.usecase.GetActualSemesters;
import ru.urfu.mm.controller.Endpoints;

import java.util.List;

@RestController
@RequestMapping(Endpoints.Semester.BASE)
public class SemesterController {
    @Autowired
    private GetActualSemesters getActualSemesters;

    @GetMapping(Endpoints.Semester.ACTUAL)
    public List<SemesterDTO> actual() {
        return getActualSemesters.getActualSemesters().stream()
                // todo: fix this
                .map(semester -> new SemesterDTO(semester.getId(), semester.getYear(), 0))
                .toList();
    }
}
