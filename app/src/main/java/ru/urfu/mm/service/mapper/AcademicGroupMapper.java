package ru.urfu.mm.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.domain.AcademicGroup;
import ru.urfu.mm.persistance.entity.GroupEntity;
import ru.urfu.mm.persistance.entity.enums.Years;

@Service
public class AcademicGroupMapper {
    private final BaseSyllabusMapper baseSyllabusMapper;
    private final StudentMapper studentMapper;

    @Autowired
    public AcademicGroupMapper(BaseSyllabusMapper baseSyllabusMapper, StudentMapper studentMapper) {
        this.baseSyllabusMapper = baseSyllabusMapper;
        this.studentMapper = studentMapper;
    }

    public GroupEntity toEntity(AcademicGroup group) {
        return new GroupEntity(
                group.getId(),
                group.getNumber(),
                Years.fromDomain(group.getYear()),
                group.getStudents()
                        .stream()
                        .map(studentMapper::toEntity)
                        .toList(),
                baseSyllabusMapper.toEntity(group.getBaseSyllabus())
        );
    }
}
