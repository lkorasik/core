package ru.urfu.mm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.controller.semester.SemesterDTO;
import ru.urfu.mm.entity.Semester;
import ru.urfu.mm.repository.SemesterRepository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class SemesterService {
    private final SemesterRepository semesterRepository;

    @Autowired
    public SemesterService(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    public List<SemesterDTO> getActualSemesters() {
        ZonedDateTime now = Instant.now().atZone(ZoneId.systemDefault());
        return GetLaterOrEqual(GetAcademicYear(now))
                .stream()
                // todo: fix it
                .map(semester -> new SemesterDTO(semester.getId(), semester.getYear(), 0))
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
