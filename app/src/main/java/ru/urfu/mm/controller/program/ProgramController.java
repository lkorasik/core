package ru.urfu.mm.controller.program;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.urfu.mm.controller.AbstractAuthorizedController;
import ru.urfu.mm.entity.EducationalProgram;
import ru.urfu.mm.entity.Student;
import ru.urfu.mm.service.ProgramService;
import ru.urfu.mm.service.StudentService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/programs")
public class ProgramController extends AbstractAuthorizedController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private ProgramService programService;

    @GetMapping("/current")
    public ProgramInfoDTO current() {
        Student student = studentService.getStudent(getUserToken());
        EducationalProgram educationalProgram = programService.getEducationalProgram(student.getEducationalProgram().getId());

        return new ProgramInfoDTO(educationalProgram.getId(), educationalProgram.getName());
    }

    @GetMapping
    public List<ProgramInfoDTO> getEducationalProgram() throws JsonProcessingException {
        var programs = programService.getEducationalPrograms();

        var result = new ArrayList<ProgramInfoDTO>();
        for(var program: programs) {
            var dto = new ProgramInfoDTO(
                    program.getId(),
                    program.getName()
            );

            result.add(dto);
        }

        return result;
    }

    @PostMapping("/program")
    public FullProgramDTO getEducationalProgram(@RequestBody ProgramIdDTO programIdDto) throws JsonProcessingException {
        return programService.getEducationalProgramById(programIdDto.id());
    }

    @PostMapping("/create")
    public void createEducationalProgram(@RequestBody CreateProgramDTO educationalProgramDTO) throws JsonProcessingException {
        programService.createEducationalProgram(educationalProgramDTO);
    }
}
