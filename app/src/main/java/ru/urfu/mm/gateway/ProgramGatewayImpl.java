package ru.urfu.mm.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.ProgramGateway;
import ru.urfu.mm.domain.Group;
import ru.urfu.mm.domain.Program;
import ru.urfu.mm.entity.*;
import ru.urfu.mm.repository.EducationalProgramRepository;
import ru.urfu.mm.repository.GroupRepository;
import ru.urfu.mm.service.mapper.Mapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProgramGatewayImpl implements ProgramGateway {
    private final EducationalProgramRepository educationalProgramRepository;
    private final GroupRepository groupRepository;
    private final Mapper<EducationalProgram, Program> programMapper;

    @Autowired
    public ProgramGatewayImpl(
            EducationalProgramRepository educationalProgramRepository,
            GroupRepository groupRepository,
            Mapper<EducationalProgram, Program> programMapper) {
        this.educationalProgramRepository = educationalProgramRepository;
        this.groupRepository = groupRepository;
        this.programMapper = programMapper;
    }

    @Override
    public Program getById(UUID id) {
        ru.urfu.mm.entity.EducationalProgram educationalProgram = educationalProgramRepository.getReferenceById(id);
        Program program = new Program(
                educationalProgram.getId(),
                educationalProgram.getName(),
                educationalProgram.getTrainingDirection()
        );
        List<Group> groups = groupRepository.findAllByEducationalProgram(educationalProgram)
                .stream()
                .map(x -> new Group(x.getId(), x.getNumber()))
                .toList();
        program.setGroups(groups);
        return program;
    }

    @Override
    public Optional<Program> findById(UUID id) {
        return educationalProgramRepository
                .findById(id)
                .map(x -> new Program(
                        x.getId(),
                        x.getName(),
                        x.getTrainingDirection()
                ));
    }

    @Override
    public List<Program> getAll() {
        return educationalProgramRepository
                .findAll()
                .stream()
                .map(programMapper::map)
                .toList();
    }

    @Override
    public void save(Program program) {
        EducationalProgram entity = new EducationalProgram(
                program.getId(),
                program.getName(),
                program.getTrainingDirection()
        );
        List<StudyPlanEntity> studyPlanEntities = program.getStudyPlans()
                .stream()
                .map(x -> new StudyPlanEntity(
                                x.getId(),
                                new SemesterPlanEntity(
                                        x.getFirstSemesterPlan().getId(),
                                        new Semester(
                                                x.getFirstSemesterPlan().getSemester().getId(),
                                                x.getFirstSemesterPlan().getSemester().getYear(),
                                                SemesterType.values()[x.getFirstSemesterPlan().getSemester().getType().ordinal()]
                                        ),
                                        x.getFirstSemesterPlan().getRecommendedCredits()
                                ),
                                new SemesterPlanEntity(
                                        x.getSecondSemesterPlan().getId(),
                                        new Semester(
                                                x.getSecondSemesterPlan().getSemester().getId(),
                                                x.getSecondSemesterPlan().getSemester().getYear(),
                                                SemesterType.values()[x.getSecondSemesterPlan().getRecommendedCredits()]
                                        ),
                                        x.getSecondSemesterPlan().getRecommendedCredits()
                                ),
                                new SemesterPlanEntity(
                                        x.getThirdSemesterPlan().getId(),
                                        new Semester(
                                                x.getThirdSemesterPlan().getSemester().getId(),
                                                x.getThirdSemesterPlan().getSemester().getYear(),
                                                SemesterType.values()[x.getThirdSemesterPlan().getRecommendedCredits()]
                                        ),
                                        x.getThirdSemesterPlan().getRecommendedCredits()
                                ),
                                new SemesterPlanEntity(
                                        x.getFourthSemesterPlan().getId(),
                                        new Semester(
                                                x.getFourthSemesterPlan().getId(),
                                                x.getFourthSemesterPlan().getSemester().getYear(),
                                                SemesterType.values()[x.getFourthSemesterPlan().getRecommendedCredits()]
                                        ),
                                        x.getFourthSemesterPlan().getRecommendedCredits()
                                ),
                                entity
                        )
                )
                .toList();
        Iterable<GroupEntity> groups = groupRepository
                .findAllById(program.getGroups().stream().map(Group::getId).toList());
        groups.forEach(group -> group.setEducationalProgram(entity));
        groupRepository.saveAll(groups);
        educationalProgramRepository.save(entity);
    }

    @Override
    public Optional<Program> findByGroup(Group group) {
        return groupRepository.findById(group.getId())
                .map(GroupEntity::getEducationalProgram)
                .map(x -> new Program(
                        x.getId(),
                        x.getName(),
                        x.getTrainingDirection()
                ));
    }
}
