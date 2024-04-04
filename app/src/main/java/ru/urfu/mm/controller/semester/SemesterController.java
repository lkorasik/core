package ru.urfu.mm.controller.semester;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.mm.application.usecase.GetActualSemesters;

import java.util.List;

@RestController
@RequestMapping("/api/semesters")
public class SemesterController {
    @Autowired
    private GetActualSemesters getActualSemesters;

    @GetMapping("/actual")
    public List<SemesterDTO> actual() {
        return getActualSemesters.getActualSemesters().stream()
                .map(semester -> new SemesterDTO(semester.getId(), semester.getYear(), semester.getSemesterNumber()))
                .toList();
    }
}
