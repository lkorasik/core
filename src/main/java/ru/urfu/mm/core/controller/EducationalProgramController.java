package ru.urfu.mm.core.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.core.dto.EducationalProgramIdDTO;
import ru.urfu.mm.core.dto.EducationalProgramInfoDTO;
import ru.urfu.mm.core.dto.FullEducationalProgramDTO;
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
    public EducationalProgramInfoDTO current() throws JsonProcessingException {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        Student student = studentService.getStudent(authentication.getName());
        EducationalProgram educationalProgram = educationalProgramService.getEducationalProgram(student.getEducationalProgram().getId());
        Map<UUID, Integer> semesterIdToRequiredCreditsCount = educationalProgramService.getSemesterIdToRequiredCreditsCount(educationalProgram);

        return new EducationalProgramInfoDTO(educationalProgram.getId(), educationalProgram.getName(), semesterIdToRequiredCreditsCount);
    }

    @GetMapping
    public List<EducationalProgramInfoDTO> getEducationalProgram() throws JsonProcessingException {
        var programs = educationalProgramService.getEducationalPrograms();

        var result = new ArrayList<EducationalProgramInfoDTO>();
        for(var program: programs) {
            EducationalProgram educationalProgram = educationalProgramService.getEducationalProgram(program.getId());
            Map<UUID, Integer> semesterIdToRequiredCreditsCount = educationalProgramService.getSemesterIdToRequiredCreditsCount(educationalProgram);

            var dto = new EducationalProgramInfoDTO(
                    program.getId(),
                    program.getName(),
                    semesterIdToRequiredCreditsCount
            );

            result.add(dto);
        }

        return result;
    }

    @PostMapping("/program")
    public FullEducationalProgramDTO getEducationalProgram(@RequestBody EducationalProgramIdDTO educationalProgramIdDto) throws JsonProcessingException {
        return educationalProgramService.getEducationalProgramById(educationalProgramIdDto.getId());
    }
}
