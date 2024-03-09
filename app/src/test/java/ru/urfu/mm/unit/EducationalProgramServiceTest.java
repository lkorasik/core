package ru.urfu.mm.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.mm.dsl.EntityDSL;
import ru.urfu.mm.dto.CreateEducationalProgramDTO;
import ru.urfu.mm.dto.CreateSemesterDTO;
import ru.urfu.mm.dto.FullEducationalProgramDTO;
import ru.urfu.mm.entity.EducationalProgram;
import ru.urfu.mm.entity.Semester;
import ru.urfu.mm.entity.SpecialCourse;
import ru.urfu.mm.exceptions.EducationalProgramNotFoundException;
import ru.urfu.mm.repository.EducationalProgramRepository;
import ru.urfu.mm.repository.EducationalProgramToCoursesWithSemestersRepository;
import ru.urfu.mm.repository.SemesterRepository;
import ru.urfu.mm.repository.SpecialCourseRepository;
import ru.urfu.mm.service.EducationalProgramService;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EducationalProgramServiceTest {
    @Mock
    private EducationalProgramRepository educationalProgramRepository;
    @Mock
    private SemesterRepository semesterRepository;
    @Mock
    private SpecialCourseRepository specialCourseRepository;
    @Mock
    private EducationalProgramToCoursesWithSemestersRepository educationalProgramToCoursesWithSemestersRepository;
    @Mock
    private ObjectMapper serializer;

    @Test
    public void test_getEducationalProgram() {
        EducationalProgram educationalProgram = EntityDSL.createEducationalProgram();

        when(educationalProgramRepository.findById(educationalProgram.getId())).thenReturn(Optional.of(educationalProgram));

        EducationalProgramService educationalProgramService = new EducationalProgramService(
                educationalProgramRepository,
                semesterRepository,
                specialCourseRepository,
                educationalProgramToCoursesWithSemestersRepository,
                serializer
        );

        EducationalProgram actual = educationalProgramService.getEducationalProgram(educationalProgram.getId());

        Assertions.assertEquals(educationalProgram, actual);
    }

    @Test
    public void test_getEducationalProgram_notFound() {
        EducationalProgram educationalProgram = EntityDSL.createEducationalProgram();

        when(educationalProgramRepository.findById(educationalProgram.getId())).thenReturn(Optional.empty());

        EducationalProgramService educationalProgramService = new EducationalProgramService(
                educationalProgramRepository,
                semesterRepository,
                specialCourseRepository,
                educationalProgramToCoursesWithSemestersRepository,
                serializer
        );

        Assertions.assertThrows(EducationalProgramNotFoundException.class, () -> educationalProgramService.getEducationalProgram(educationalProgram.getId()));
    }

    /**
     * Проблема с тем как замокать serializer.readValue
     */
    @Disabled
    @Test
    public void test_getSemesterIdToRequiredCreditsCount() throws JsonProcessingException {
        List<UUID> uuids = List.of(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID()
        );
        String json = "{" +
                      "\"" + uuids.get(0) +  "\": " + 1 + "," +
                      "\"" + uuids.get(1) +  "\": " + 2 + "," +
                      "\"" + uuids.get(2) +  "\": " + 3 + "," +
                      "\"" + uuids.get(3) +  "\": " + 4 +
                      "}";
        EducationalProgram educationalProgram = new EducationalProgram(
                UUID.randomUUID(),
                "EP1",
                "T1",
                json
        );
        HashMap<UUID, Integer> map = new HashMap<>();
        map.put(uuids.get(0), 1);
        map.put(uuids.get(1), 2);
        map.put(uuids.get(2), 3);
        map.put(uuids.get(3), 4);

        when(serializer.readValue(anyString(), eq(new TypeReference<HashMap<UUID, Integer>>() {}))).thenReturn(map);

        EducationalProgramService educationalProgramService = new EducationalProgramService(
                educationalProgramRepository,
                semesterRepository,
                specialCourseRepository,
                educationalProgramToCoursesWithSemestersRepository,
                serializer
        );

        Map<UUID, Integer> actual = educationalProgramService.getSemesterIdToRequiredCreditsCount(educationalProgram);

        uuids.forEach(x -> Assertions.assertTrue(actual.containsKey(x)));
        for(var i = 0; i < uuids.size(); i++) {
            Assertions.assertEquals((int) actual.get(uuids.get(i)), (i + 1));
        }
    }

    @Test
    public void test_getEducationalPrograms() {
        List<EducationalProgram> educationalPrograms = List.of(
                EntityDSL.createEducationalProgram(),
                EntityDSL.createEducationalProgram()
        );

        when(educationalProgramRepository.findAll()).thenReturn(educationalPrograms);

        EducationalProgramService educationalProgramService = new EducationalProgramService(
                educationalProgramRepository,
                semesterRepository,
                specialCourseRepository,
                educationalProgramToCoursesWithSemestersRepository,
                serializer
        );

        List<EducationalProgram> actual = educationalProgramService.getEducationalPrograms();

        actual.forEach(x -> Assertions.assertTrue(educationalPrograms.contains(x)));
    }

    @Test
    public void test_getEducationalPrograms_empty() {
        when(educationalProgramRepository.findAll()).thenReturn(List.of());

        EducationalProgramService educationalProgramService = new EducationalProgramService(
                educationalProgramRepository,
                semesterRepository,
                specialCourseRepository,
                educationalProgramToCoursesWithSemestersRepository,
                serializer
        );

        List<EducationalProgram> actual = educationalProgramService.getEducationalPrograms();

        Assertions.assertTrue(actual.isEmpty());
    }

    /**
     * Тест на getEducationalProgramById. Выключен по той же причине: не понятно как замокать serializer.readValue
     */
    @Disabled
    @Test
    public void test_getEducationalProgramById() throws JsonProcessingException {
        EducationalProgram educationalProgram = EntityDSL.createEducationalProgram();

        when(educationalProgramRepository.findById(educationalProgram.getId())).thenReturn(Optional.of(educationalProgram));

        EducationalProgramService educationalProgramService = new EducationalProgramService(
                educationalProgramRepository,
                semesterRepository,
                specialCourseRepository,
                educationalProgramToCoursesWithSemestersRepository,
                serializer
        );

        FullEducationalProgramDTO actual = educationalProgramService.getEducationalProgramById(educationalProgram.getId());
    }
}
