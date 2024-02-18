package ru.urfu.mm.core.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.mm.core.dto.EducationalProgramInfoDto;
import ru.urfu.mm.core.entity.EducationalProgram;
import ru.urfu.mm.core.entity.Student;
import ru.urfu.mm.core.service.EducationalProgramService;
import ru.urfu.mm.core.service.StudentService;

import java.util.*;

@RestController
@RequestMapping("/api/educationalPrograms")
public class EducationalProgramController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private EducationalProgramService educationalProgramService;

    @GetMapping("/current")
    public EducationalProgramInfoDto current() throws JsonProcessingException {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        Student student = studentService.getStudent(authentication.getName());
        EducationalProgram educationalProgram = educationalProgramService.getEducationalProgram(student.getEducationalProgramId());
        HashMap semesterIdToRequiredCreditsCount = educationalProgramService.getSemesterIdToRequiredCreditsCount(educationalProgram);

        return new EducationalProgramInfoDto(educationalProgram.getId(), educationalProgram.getName(), semesterIdToRequiredCreditsCount);
    }

    @GetMapping
    public List<EducationalProgramInfoDto> getEducationalProgram() throws JsonProcessingException {
        var programs = educationalProgramService.getEducationalPrograms();

        var result = new ArrayList<EducationalProgramInfoDto>();
        for(var program: programs) {
            EducationalProgram educationalProgram = educationalProgramService.getEducationalProgram(program.getId());
            HashMap semesterIdToRequiredCreditsCount = educationalProgramService.getSemesterIdToRequiredCreditsCount(educationalProgram);

            var dto = new EducationalProgramInfoDto(
                    program.getId(),
                    program.getName(),
                    semesterIdToRequiredCreditsCount
            );

            result.add(dto);
        }

        return result;
    }
}
