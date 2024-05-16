package ru.urfu.mm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.persistance.entity.StudentEntity;
import ru.urfu.mm.persistance.entity.StudentSkills;
import ru.urfu.mm.persistance.repository.CourseToRequiredSkillsRepository;
import ru.urfu.mm.persistance.repository.CourseToResultSkillsRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CoursesSkillsService {
    @Autowired
    private CourseToRequiredSkillsRepository courseToRequiredSkillsRepository;
    @Autowired
    private CourseToResultSkillsRepository courseToResultSkillsRepository;

    public Map<UUID, List<StudentSkills>> getCoursesToRequiredSkills(StudentEntity studentEntity, List<UUID> availableCoursesIds) {
        return courseToRequiredSkillsRepository
                .findAll()
                .stream()
                .filter(x -> availableCoursesIds.contains(x.getSpecialCourse().getId()))
                .collect(
                        Collectors.groupingBy(
                                x -> x.getSpecialCourse().getId(),
                                Collectors.mapping(
                                        x -> new StudentSkills(
                                                studentEntity,
                                                x.getRequiredSkills(),
                                                x.getSkillLevel()
                                        ),
                                        Collectors.toList()
                                )
                        ))
                .entrySet()
                .stream()
                .collect(
                        Collectors
                                .toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue
                                )
                );
    }

    public Map<UUID, List<StudentSkills>> getCoursesToResultSkills(StudentEntity studentEntity, List<UUID> availableCoursesIds) {
        return courseToResultSkillsRepository
                .findAll()
                .stream()
                .filter(x -> availableCoursesIds.contains(x.getSpecialCourse().getId()))
                .collect(
                        Collectors.groupingBy(
                                x -> x.getSpecialCourse().getId(),
                                Collectors.mapping(
                                        x -> new StudentSkills(
                                                studentEntity,
                                                x.getSkill(),
                                                x.getSkillLevel()
                                        ),
                                        Collectors.toList()
                                )
                        ))
                .entrySet()
                .stream()
                .collect(
                        Collectors
                                .toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue
                                )
                );
    }
}
