package ru.urfu.mm.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.urfu.mm.applicationlegacy.gateway.*;
import ru.urfu.mm.applicationlegacy.usecase.*;

@Configuration
public class UseCaseConfiguration {
    @Bean
    public CreateAdministrator createAdministrator(
            TokenGateway tokenGateway,
            LoggerGateway loggerGateway,
            PasswordGateway passwordGateway,
            UserGateway userGateway) {
        return new CreateAdministrator(tokenGateway, loggerGateway, passwordGateway, userGateway);
    }

    @Bean
    public CreateStudent createStudent(
            TokenGateway tokenGateway,
            LoggerGateway loggerGateway,
            PasswordGateway passwordGateway,
            UserGateway userGateway,
            ProgramGateway programGateway,
            StudentGateway studentGateway) {
        return new CreateStudent(
                tokenGateway,
                loggerGateway,
                passwordGateway,
                userGateway,
                programGateway,
                studentGateway);
    }

    @Bean
    public LoginUser loginUser(UserGateway userGateway, PasswordGateway passwordGateway) {
        return new LoginUser(userGateway, passwordGateway);
    }

    @Bean
    public GetAllCourses getAllCourses(CourseGateway courseGateway) {
        return new GetAllCourses(courseGateway);
    }

    @Bean
    public GetEducationalModuleCourses getEducationalModuleCourses(CourseGateway courseGateway) {
        return new GetEducationalModuleCourses(courseGateway);
    }

    @Bean
    public GetSelectedCoursesIds getSelectedCoursesIds(CourseGateway courseGateway) {
        return new GetSelectedCoursesIds(courseGateway);
    }

    @Bean
    public GetCoursesByEducationalProgramAndSemesters getCoursesByEducationalProgramAndSemesters(CourseGateway courseGateway, StudentGateway studentGateway) {
        return new GetCoursesByEducationalProgramAndSemesters(courseGateway, studentGateway);
    }
    @Bean
    public SelectCourses selectCourses(CourseGateway courseGateway, SemesterGateway semesterGateway, StudentGateway studentGateway, GetCoursesByEducationalProgramAndSemesters getCoursesByEducationalProgramAndSemesters) {
        return new SelectCourses(courseGateway, semesterGateway, studentGateway, getCoursesByEducationalProgramAndSemesters);
    }

    @Bean
    public EditModuleSpecialCourse editModuleSpecialCourse(CourseGateway courseGateway) {
        return new EditModuleSpecialCourse(courseGateway);
    }

    @Bean
    public DeleteCourse deleteCourse(CourseGateway courseGateway) {
        return new DeleteCourse(courseGateway);
    }

    @Bean
    public CreateModuleSpecialCourse createModuleSpecialCourse(ModuleGateway moduleGateway, CourseGateway courseGateway) {
        return new CreateModuleSpecialCourse(moduleGateway, courseGateway);
    }

    @Bean
    public GetCourse getCourse(CourseGateway courseGateway) {
        return new GetCourse(courseGateway);
    }

    @Bean
    public GetSpecialCourseStudentsCount getSpecialCourseStudentsCount(CourseGateway courseGateway) {
        return new GetSpecialCourseStudentsCount(courseGateway);
    }

    @Bean
    public GetActualSpecialCoursesStatistics getActualSpecialCoursesStatistics(CourseGateway courseGateway) {
        return new GetActualSpecialCoursesStatistics(courseGateway);
    }

    @Bean
    public GetAllModules getAllModules(ModuleGateway moduleGateway) {
        return new GetAllModules(moduleGateway);
    }

    @Bean
    public GetModulesByIds getModulesByIds(ModuleGateway moduleGateway) {
        return new GetModulesByIds(moduleGateway);
    }

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}
