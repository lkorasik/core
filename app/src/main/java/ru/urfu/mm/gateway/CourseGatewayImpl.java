package ru.urfu.mm.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.applicationlegacy.gateway.CourseGateway;
import ru.urfu.mm.domainlegacy.Control;
import ru.urfu.mm.domainlegacy.Module;
import ru.urfu.mm.domainlegacy.SpecialCourse;
import ru.urfu.mm.repository.SpecialCourseRepository;

import java.util.List;

@Component
public class CourseGatewayImpl implements CourseGateway {
    private final SpecialCourseRepository courseRepository;

    @Autowired
    public CourseGatewayImpl(SpecialCourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<SpecialCourse> getAllCourses() {
        return courseRepository
                .findAll()
                .stream()
                .map(x -> new SpecialCourse(
                        x.getId(),
                        x.getName(),
                        x.getCreditsCount(),
                        Control.values()[x.getControl().ordinal()],
                        x.getDescription(),
                        x.getDepartment(),
                        x.getTeacherName(),
                        new Module(
                                x.getEducationalModule().getId(),
                                x.getEducationalModule().getName()
                        )
                ))
                .toList();
    }
}
