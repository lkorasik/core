package ru.urfu.mm.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.urfu.mm.application.gateway.*;
import ru.urfu.mm.application.usecase.create.CreateAdministrator;
import ru.urfu.mm.application.usecase.create.CreateStudent;
import ru.urfu.mm.application.usecase.create.CreateAccount;
import ru.urfu.mm.application.usecase.create_course.CreateCourse;
import ru.urfu.mm.application.usecase.create_group.CreateGroup;
import ru.urfu.mm.application.usecase.create_educational_program.CreateEducationalProgram;
import ru.urfu.mm.application.usecase.create_module.CreateModule;
import ru.urfu.mm.application.usecase.create_syylabus.CreateBaseSyllabus;
import ru.urfu.mm.application.usecase.download_tokens.DownloadTokens;
import ru.urfu.mm.application.usecase.generate_student_registration_token.GenerateStudentRegistrationToken;
import ru.urfu.mm.application.usecase.get_group.GetAcademicGroup;

import ru.urfu.mm.application.usecase.get_all_modules.GetAllModules;
import ru.urfu.mm.application.usecase.get_all_programs.GetAllPrograms;
import ru.urfu.mm.application.usecase.get_module.GetModuleWithCourses;
import ru.urfu.mm.application.usecase.get_program_by_id.GetProgramById;
import ru.urfu.mm.application.usecase.get_base_syllabus.GetAllSyllabi;
import ru.urfu.mm.application.usecase.get_token.GetTokensForGroup;
import ru.urfu.mm.application.usecase.login_user.LoginUser;

@Configuration
public class UseCaseConfiguration {
    @Bean
    public CreateAccount createUser(
            CreateStudent createStudent,
            CreateAdministrator createAdministrator,
            TokenGateway tokenGateway) {
        return new CreateAccount(createStudent, createAdministrator, tokenGateway);
    }

    @Bean
    public CreateAdministrator createAdministrator(
            TokenGateway tokenGateway,
            PasswordGateway passwordGateway,
            UserGateway userGateway) {
        return new CreateAdministrator(tokenGateway, passwordGateway, userGateway);
    }

    @Bean
    public CreateStudent createStudent() {
        return new CreateStudent();
    }

    @Bean
    public LoginUser loginUser(UserGateway userGateway, PasswordGateway passwordGateway) {
        return new LoginUser(userGateway, passwordGateway);
    }

    @Bean
    public CreateCourse createCourse(ModuleGateway moduleGateway, CourseGateway courseGateway) {
        return new CreateCourse(moduleGateway, courseGateway);
    }

    @Bean
    public GetAllModules getAllModules(ModuleGateway moduleGateway) {
        return new GetAllModules(moduleGateway);
    }

    @Bean
    public CreateModule createModuleWithCourses(ModuleGateway moduleGateway) {
        return new CreateModule(moduleGateway);
    }

    @Bean
    public GetAllPrograms getAllEducationalPrograms(ProgramGateway programGateway) {
        return new GetAllPrograms(programGateway);
    }

    @Bean
    public GetModuleWithCourses getModule(ModuleGateway moduleGateway) {
        return new GetModuleWithCourses(moduleGateway);
    }

    @Bean
    public CreateBaseSyllabus createBaseSyllabus(
            BaseSyllabusPlanGateway baseSyllabusPlanGateway,
            CourseGateway courseGateway
    ) {
        return new CreateBaseSyllabus(baseSyllabusPlanGateway, courseGateway);
    }

    @Bean
    public CreateGroup createGroup(
            GroupGateway groupGateway,
            ProgramGateway programGateway,
            SemesterGateway semesterGateway,
            BaseSyllabusPlanGateway baseSyllabusPlan,
            BaseSemesterPlanGateway baseSemesterPlanGateway) {
        return new CreateGroup(
                groupGateway,
                programGateway,
                semesterGateway,
                baseSyllabusPlan,
                baseSemesterPlanGateway
        );
    }

    @Bean
    public GetAcademicGroup getAcademicGroup(GroupGateway groupGateway) {
        return new GetAcademicGroup(groupGateway);
    }

    @Bean
    public GenerateStudentRegistrationToken generateStudentRegistrationToken(
            GetAcademicGroup getAcademicGroup,
            StudentGateway studentGateway,
            GroupGateway groupGateway
    ) {
        return new GenerateStudentRegistrationToken(getAcademicGroup, studentGateway, groupGateway);
    }

    @Bean
    public GetTokensForGroup getTokensForGroup(GetAcademicGroup getAcademicGroup) {
        return new GetTokensForGroup(getAcademicGroup);
    }

    @Bean
    public DownloadTokens downloadTokens() {
        return new DownloadTokens();
    }

    @Bean
    public CreateEducationalProgram createProgram(ProgramGateway programGateway) {
        return new CreateEducationalProgram(programGateway);
    }

    @Bean
    public GetProgramById getProgramById(ProgramGateway programGateway) {
        return new GetProgramById(programGateway);
    }

    @Bean
    public GetAllSyllabi getStudyPlan(ProgramGateway programGateway) {
        return new GetAllSyllabi(programGateway);
    }

}
