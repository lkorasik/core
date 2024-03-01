package ru.urfu.mm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.dto.CreateEducationalProgramDTO;
import ru.urfu.mm.dto.EducationalProgramIdDTO;
import ru.urfu.mm.dto.EducationalProgramInfoDTO;
import ru.urfu.mm.dto.FullEducationalProgramDTO;
import ru.urfu.mm.entity.EducationalProgram;
import ru.urfu.mm.entity.Student;
import ru.urfu.mm.service.EducationalProgramService;
import ru.urfu.mm.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @PostMapping("/create")
    public void createEducationalProgram(@RequestBody CreateEducationalProgramDTO educationalProgramDTO) throws JsonProcessingException {
        educationalProgramService.createEducationalProgram(educationalProgramDTO);
    }
}
