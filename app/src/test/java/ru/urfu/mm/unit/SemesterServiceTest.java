package ru.urfu.mm.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.mm.controller.semester.SemesterDTO;
import ru.urfu.mm.entity.Semester;
import ru.urfu.mm.repository.SemesterRepository;
import ru.urfu.mm.service.SemesterService;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SemesterServiceTest {
    @Mock
    private SemesterRepository semesterRepository;

    /**
     * Описание бага смотри в Notion -> Описание нерабочих кейсов -> Кейс 3
     *
     * https://github.com/lkorasik/core/issues/47
     */
    @Disabled
    @Test
    public void test() {
        int year = Instant.now().atZone(ZoneId.systemDefault()).getYear();
        List<Semester> semesters = List.of(
                new Semester(UUID.randomUUID(), year, 1),
                new Semester(UUID.randomUUID(), year + 1, 2),
                new Semester(UUID.randomUUID(), year + 1, 3),
                new Semester(UUID.randomUUID(), year + 2, 4),
                new Semester(UUID.randomUUID(), year - 2, 1),
                new Semester(UUID.randomUUID(), year - 1, 2),
                new Semester(UUID.randomUUID(), year - 1, 3),
                new Semester(UUID.randomUUID(), year, 4)
        );

        when(semesterRepository.findAll()).thenReturn(semesters);

        SemesterService semesterService = new SemesterService(semesterRepository);

        List<SemesterDTO> actual = semesterService.getActualSemesters();

        List<SemesterDTO> expected = semesters
                .stream()
                .limit(4)
                .map(x -> new SemesterDTO(x.getId(), x.getYear(), x.getSemesterNumber()))
                .toList();

        Assertions.assertEquals(expected, actual);
    }
}
