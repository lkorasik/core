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
    public GetCoursesByEducationalProgramAndSemesters getCoursesByEducationalProgramAndSemesters(CourseGateway courseGateway) {
        return new GetCoursesByEducationalProgramAndSemesters(courseGateway);
    }

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}
