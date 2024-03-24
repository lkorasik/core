package ru.urfu.mm.applicationlegacy.usecase;

import ru.urfu.mm.applicationlegacy.gateway.CourseGateway;
import ru.urfu.mm.domainlegacy.SelectedCourses;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class GetSelectedCoursesIds {
    private final CourseGateway courseGateway;

    public GetSelectedCoursesIds(CourseGateway courseGateway) {
        this.courseGateway = courseGateway;
    }

    public Map<UUID, List<UUID>> getSelectedCoursesIds(UUID studentId, List<UUID> semestersIds) {
        var semesterIdsSet = new HashSet<>(semestersIds);
        var selectedCourses = courseGateway.getSelectedCourses(studentId);
        return selectedCourses
                .stream()
                .filter(x -> semesterIdsSet.contains(x.getSemester().getId()))
                .collect(Collectors.toMap(SelectedCourses::getId, x -> List.of(x.getSpecialCourse().getId())));
    }
}
