package ru.urfu.mm.core.dto;

import java.util.List;

public class SelectedCoursesDTO {
    private List<CoursesBySemesterDTO> coursesBySemesters;

    public SelectedCoursesDTO() {

    }

    public SelectedCoursesDTO(List<CoursesBySemesterDTO> coursesBySemesters) {
        this.coursesBySemesters = coursesBySemesters;
    }

    public List<CoursesBySemesterDTO> getCoursesBySemesters() {
        return coursesBySemesters;
    }
}
