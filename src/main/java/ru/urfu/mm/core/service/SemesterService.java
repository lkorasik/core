package ru.urfu.mm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.core.dto.SemesterDTO;
import ru.urfu.mm.core.entity.Semester;
import ru.urfu.mm.core.repository.SemesterRepository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class SemesterService {
    @Autowired
    private SemesterRepository semesterRepository;

    public List<SemesterDTO> getActualSemesters() {
        ZonedDateTime now = Instant.now().atZone(ZoneId.systemDefault());
        return GetLaterOrEqual(GetAcademicYear(now))
                .stream()
                .map(semester -> new SemesterDTO(semester.getId(), semester.getYear(), semester.getSemesterNumber()))
                .toList();
    }

    private List<Semester> GetLaterOrEqual(int year) {
        return semesterRepository.findAll().stream().filter(semester -> semester.getYear() >= year).toList();
    }

    private static int GetAcademicYear(ZonedDateTime now)
    {
        var academicYear = now.getYear();
        if (now.getMonth().ordinal() < 9)
        {
            academicYear--;
        }

        return academicYear;
    }
}
