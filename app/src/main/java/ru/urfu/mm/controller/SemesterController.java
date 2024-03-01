package ru.urfu.mm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.mm.dto.SemesterDTO;
import ru.urfu.mm.service.SemesterService;

import java.util.List;

@RestController
@RequestMapping("/api/semesters")
public class SemesterController {
    @Autowired
    private SemesterService semesterService;

    @GetMapping("/actual")
    public List<SemesterDTO> actual() {
        return semesterService.getActualSemesters();
    }
}
