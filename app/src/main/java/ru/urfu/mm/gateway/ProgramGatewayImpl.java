package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.exception.NotImplementedException;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.*;
import ru.urfu.mm.persistance.entity.EducationalProgramEntity;
import ru.urfu.mm.persistance.entity.GroupEntity;
import ru.urfu.mm.persistance.entity.enums.SemesterType;
import ru.urfu.mm.persistance.repository.ProgramRepository;
import ru.urfu.mm.persistance.repository.GroupRepository;
import ru.urfu.mm.persistance.repository.StudyPlanRepository;
import ru.urfu.mm.service.mapper.Mapper;
import ru.urfu.mm.service.mapper.ProgramMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProgramGatewayImpl implements ProgramGateway {
    private final ProgramRepository programRepository;
    private final GroupRepository groupRepository;
    private final StudyPlanRepository studyPlanRepository;
    private final ProgramMapper programMapper;

    @Autowired
    public ProgramGatewayImpl(
            ProgramRepository programRepository,
            GroupRepository groupRepository,
            StudyPlanRepository studyPlanRepository,
            ProgramMapper programMapper) {
        this.programRepository = programRepository;
        this.groupRepository = groupRepository;
        this.studyPlanRepository = studyPlanRepository;
        this.programMapper = programMapper;
    }

    @Override
    public EducationalProgram getById(UUID id) {
        throw new NotImplementedException();
//        EducationalProgramEntity educationalProgramEntity = programRepository.getReferenceById(id);
//        EducationalProgram program = new EducationalProgram(
//                educationalProgramEntity.getId(),
//                educationalProgramEntity.getName(),
//                educationalProgramEntity.getTrainingDirection()
//        );
//        List<AcademicGroup> groups = educationalProgramEntity.getGroups()
//                .stream()
//                .map(x -> new AcademicGroup(x.getId(), x.getNumber()))
//                .toList();
//        program.getGroups().addAll(groups);
//        List<StudentSyllabus> studentSyllabi = educationalProgramEntity.getSyllabuses()
//                .stream()
//                .map(x -> new StudentSyllabus(
//                                x.getId(),
//                                new SemesterPlan(
//                                        x.getFirstSemester().getId(),
//                                        new Semester(
//                                                x.getFirstSemester().getSemester().getId(),
//                                                x.getFirstSemester().getSemester().getYear(),
//                                                SemesterType.toDomain(x.getFirstSemester().getSemester().getType())
//                                        ),
//                                        x.getFirstSemester().getRecommendedCredits()
//                                ),
//                                new SemesterPlan(
//                                        x.getSecondSemester().getId(),
//                                        new Semester(
//                                                x.getSecondSemester().getSemester().getId(),
//                                                x.getSecondSemester().getSemester().getYear(),
//                                                SemesterType.toDomain(x.getSecondSemester().getSemester().getType())
//                                        ),
//                                        x.getSecondSemester().getRecommendedCredits()
//                                ),
//                                new SemesterPlan(
//                                        x.getThirdSemester().getId(),
//                                        new Semester(
//                                                x.getThirdSemester().getSemester().getId(),
//                                                x.getThirdSemester().getSemester().getYear(),
//                                                SemesterType.toDomain(x.getThirdSemester().getSemester().getType())
//                                        ),
//                                        x.getThirdSemester().getRecommendedCredits()
//                                ),
//                                new SemesterPlan(
//                                        x.getFourthSemester().getId(),
//                                        new Semester(
//                                                x.getFourthSemester().getSemester().getId(),
//                                                x.getFourthSemester().getSemester().getYear(),
//                                                SemesterType.toDomain(x.getFourthSemester().getSemester().getType())
//                                        ),
//                                        x.getFourthSemester().getRecommendedCredits()
//                                )
//                        )
//                )
//                .toList();
//        program.getSyllabi().addAll(studentSyllabi);
//        return program;
    }

    @Override
    public Optional<EducationalProgram> findById(UUID id) {
        return programRepository
                .findById(id)
                .map(x -> new EducationalProgram(
                        x.getId(),
                        x.getName(),
                        x.getTrainingDirection()
                ));
    }

    @Override
    public List<EducationalProgram> getAll() {
        return programRepository
                .findAll()
                .stream()
                .map(programMapper::toDomain)
                .toList();
    }

    @Override
    public void save(EducationalProgram educationalProgram) {
        EducationalProgramEntity entity = programMapper.toEntity(educationalProgram);
//        Iterable<GroupEntity> groups = groupRepository
//                .findAllById(educationalProgram.getGroups().stream().map(AcademicGroup::getId).toList());
//        groups.forEach(group -> group.setProgram(entity));
//        groupRepository.saveAll(groups);
        programRepository.save(entity);
    }

    public Optional<EducationalProgram> findByGroup(AcademicGroup academicGroup) {
        throw new NotImplementedException();
//        return groupRepository.findById(academicGroup.getId())
//                .map(GroupEntity::getProgram)
//                .map(x -> new EducationalProgram(x.getId(), x.getName(), x.getTrainingDirection()));
    }
}
