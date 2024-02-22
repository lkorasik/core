package ru.urfu.mm.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.core.entity.EducationalProgram;
import ru.urfu.mm.core.repository.EducationalProgramRepository;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class EducationalProgramService {
    @Autowired
    private EducationalProgramRepository educationalProgramRepository;
    @Autowired
    private ObjectMapper objectMapper;

    public EducationalProgram getEducationalProgram(UUID educationalProgramId) {
        return educationalProgramRepository
                .findById(educationalProgramId)
                .orElseThrow();
    }

    public HashMap getSemesterIdToRequiredCreditsCount(EducationalProgram educationalProgram) throws JsonProcessingException {
        return objectMapper.readValue(educationalProgram.getSemesterIdToRequiredCreditsCount(), HashMap.class);
    }

    public List<EducationalProgram> getEducationalPrograms() {
        return educationalProgramRepository.findAll();
    }
}
