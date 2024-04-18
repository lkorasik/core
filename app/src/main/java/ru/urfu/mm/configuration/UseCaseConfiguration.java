package ru.urfu.mm.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.urfu.mm.application.gateway.*;
import ru.urfu.mm.application.usecase.*;
import ru.urfu.mm.application.usecase.create.administrator.CreateAdministrator;
import ru.urfu.mm.application.usecase.create.student.CreateStudent;
import ru.urfu.mm.application.usecase.creategroup.CreateGroup;
import ru.urfu.mm.application.usecase.get_program_for_student.GetProgramForStudent;
import ru.urfu.mm.application.usecase.getallmodules.GetAllModules;
import ru.urfu.mm.application.usecase.getallprograms.GetAllPrograms;
import ru.urfu.mm.application.usecase.getgroups.GetGroupForEducationalProgram;
import ru.urfu.mm.application.usecase.getmodule.GetModuleWithCourses;
import ru.urfu.mm.application.usecase.load_available_courses.LoadAvailableCourses;
import ru.urfu.mm.application.usecase.loginuser.LoginUser;

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
    public GetCoursesByEducationalProgramAndSemesters getCoursesByEducationalProgramAndSemesters(
            CourseGateway courseGateway,
            StudentGateway studentGateway) {
        return new GetCoursesByEducationalProgramAndSemesters(courseGateway, studentGateway);
    }

    @Bean
    public SelectCourses selectCourses(
            CourseGateway courseGateway,
            SemesterGateway semesterGateway,
            StudentGateway studentGateway,
            GetCoursesByEducationalProgramAndSemesters getCoursesByEducationalProgramAndSemesters) {
        return new SelectCourses(
                courseGateway,
                semesterGateway,
                studentGateway,
                getCoursesByEducationalProgramAndSemesters);
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
    public CreateModuleSpecialCourse createModuleSpecialCourse(
            ModuleGateway moduleGateway,
            CourseGateway courseGateway) {
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
    public CreateModuleWithCourses createModuleWithCourses(ModuleGateway moduleGateway, CourseGateway courseGateway) {
        return new CreateModuleWithCourses(moduleGateway, courseGateway);
    }

    @Bean
    public DeleteModuleById deleteModuleById(ModuleGateway moduleGateway, CourseGateway courseGateway) {
        return new DeleteModuleById(moduleGateway, courseGateway);
    }

    @Bean
    public GetEducationalProgram getEducationalProgram(ProgramGateway programGateway, StudentGateway studentGateway) {
        return new GetEducationalProgram(programGateway, studentGateway);
    }

    @Bean
    public GetAllPrograms getAllEducationalPrograms(ProgramGateway programGateway) {
        return new GetAllPrograms(programGateway);
    }

    @Bean
    public GetSkillsForStudent getSkillsForStudent(SkillGateway skillGateway) {
        return new GetSkillsForStudent(skillGateway);
    }

    @Bean
    public SaveSkillsForStudent saveSkillsForStudent(SkillGateway skillGateway, StudentGateway studentGateway) {
        return new SaveSkillsForStudent(skillGateway, studentGateway);
    }

    @Bean
    public GetDesiredSkillsForStudent getDesiredSkillsForStudent(SkillGateway skillGateway) {
        return new GetDesiredSkillsForStudent(skillGateway);
    }

    @Bean
    public SaveDesiredSkillsForStudent saveDesiredSkillsForStudent(
            SkillGateway skillGateway,
            StudentGateway studentGateway) {
        return new SaveDesiredSkillsForStudent(skillGateway, studentGateway);
    }

    @Bean
    public GetActualSemesters getActualSemesters(SemesterGateway semesterGateway) {
        return new GetActualSemesters(semesterGateway);
    }

    @Bean
    public GetSkills getSkills(SkillGateway skillGateway) {
        return new GetSkills(skillGateway);
    }

    @Bean
    public GetSelectedCoursesByStudentAndSemester getSelectedCoursesByStudentAndSemester(
            CourseGateway courseGateway,
            StudentGateway studentGateway) {
        return new GetSelectedCoursesByStudentAndSemester(courseGateway, studentGateway);
    }

    @Bean
    public GetProgramForStudent getProgramForStudent(
            StudentGateway studentGateway,
            ProgramGateway programGateway,
            SemesterGateway semesterGateway,
            CourseGateway courseGateway) {
        return new GetProgramForStudent(studentGateway, programGateway, semesterGateway, courseGateway);
    }

    @Bean
    public LoadAvailableCourses loadAvailableCourses(StudentGateway studentGateway, CourseGateway courseGateway) {
        return new LoadAvailableCourses(studentGateway, courseGateway);
    }

    @Bean
    public GetModuleWithCourses getModule(ModuleGateway moduleGateway, CourseGateway courseGateway) {
        return new GetModuleWithCourses(moduleGateway, courseGateway);
    }

    @Bean
    public GetGroupForEducationalProgram getGroupForEducationalProgram(ProgramGateway programGateway) {
        return new GetGroupForEducationalProgram(programGateway);
    }

    @Bean
    public CreateGroup createGroup(GroupGateway groupGateway, ProgramGateway programGateway) {
        return new CreateGroup(groupGateway, programGateway);
    }

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}
