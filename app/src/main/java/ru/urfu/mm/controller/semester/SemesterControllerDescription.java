package ru.urfu.mm.controller.semester;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.urfu.mm.application.exception.NotImplementedException;
import ru.urfu.mm.controller.Endpoints;

import java.util.List;

@RequestMapping(Endpoints.Semester.BASE)
public interface SemesterControllerDescription {
    @GetMapping(Endpoints.Semester.ACTUAL)
    List<SemesterDTO> actual();
}
