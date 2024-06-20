package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.BaseSyllabusPlanGateway;
import ru.urfu.mm.domain.BaseSyllabus;
import ru.urfu.mm.persistance.entity.BaseSyllabusEntity;
import ru.urfu.mm.persistance.repository.BaseSyllabusRepository;
import ru.urfu.mm.service.mapper.BaseSyllabusMapper;

@Component
public class BaseSyllabusGatewayImpl implements BaseSyllabusPlanGateway {
    private final BaseSyllabusRepository baseSyllabusRepository;
    private final BaseSyllabusMapper baseSyllabusMapper;

    @Autowired
    public BaseSyllabusGatewayImpl(
            BaseSyllabusRepository baseSyllabusRepository,
            BaseSyllabusMapper baseSyllabusMapper
    ) {
        this.baseSyllabusRepository = baseSyllabusRepository;
        this.baseSyllabusMapper = baseSyllabusMapper;
    }

    @Override
    public void save(BaseSyllabus syllabus) {
        BaseSyllabusEntity entity = baseSyllabusMapper.toEntity(syllabus);
        baseSyllabusRepository.save(entity);
    }
}
