package ru.urfu.mm.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.urfu.mm.application.gateway.*;
import ru.urfu.mm.application.usecase.*;
import ru.urfu.mm.application.usecase.create.CreateAdministrator;
import ru.urfu.mm.application.usecase.create.CreateStudent;
import ru.urfu.mm.application.usecase.create.account.CreateAccount;
import ru.urfu.mm.application.usecase.create_course.CreateCourse;
import ru.urfu.mm.application.usecase.create_group.CreateGroup;
import ru.urfu.mm.application.usecase.create_educational_program.CreateEducationalProgram;
import ru.urfu.mm.application.usecase.create_module.CreateModule;
//import ru.urfu.mm.application.usecase.create_semester_plan.CreateSemesterPlan;
import ru.urfu.mm.application.usecase.create_study_plan.CreateBaseSyllabus;
import ru.urfu.mm.application.usecase.download_tokens.DownloadTokens;
//import ru.urfu.mm.application.usecase.generate_token.GenerateStudentRegistrationTokens;
import ru.urfu.mm.application.usecase.generate_student_registration_token.GenerateStudentRegistrationToken;
import ru.urfu.mm.application.usecase.get_group.GetAcademicGroup;
import ru.urfu.mm.application.usecase.get_program_for_student.GetProgramForStudent;
import ru.urfu.mm.application.usecase.get_all_modules.GetAllModules;
import ru.urfu.mm.application.usecase.get_all_programs.GetAllPrograms;
import ru.urfu.mm.application.usecase.get_groups.GetGroupsByEducationalProgram;
import ru.urfu.mm.application.usecase.get_module.GetModuleWithCourses;
import ru.urfu.mm.application.usecase.get_modules_courses.GetModulesCourses;
import ru.urfu.mm.application.usecase.get_program_by_id.GetProgramById;
import ru.urfu.mm.application.usecase.get_available_years.GetAvailableYears;
import ru.urfu.mm.application.usecase.get_study_plan.GetStudyPlan;
import ru.urfu.mm.application.usecase.get_token.GetTokensForGroup;
import ru.urfu.mm.application.usecase.login_user.LoginUser;
import ru.urfu.mm.application.usecase.update_program.UpdateProgram;

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
    public CreateStudent createStudent(
            PasswordGateway passwordGateway,
            UserGateway userGateway,
            StudentGateway studentGateway,
            GroupGateway groupGateway) {
        return new CreateStudent(
                studentGateway,
                passwordGateway,
                userGateway,
                groupGateway
        );
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

//    @Bean
//    public GetSelectedCoursesIds getSelectedCoursesIds(CourseGateway courseGateway) {
//        return new GetSelectedCoursesIds(courseGateway);
//    }

//    @Bean
//    public GetCoursesByEducationalProgramAndSemesters getCoursesByEducationalProgramAndSemesters(
//            CourseGateway courseGateway,
//            StudentGateway studentGateway) {
//        return new GetCoursesByEducationalProgramAndSemesters(courseGateway, studentGateway);
//    }

//    @Bean
//    public SelectCourses selectCourses(
//            CourseGateway courseGateway,
//            SemesterGateway semesterGateway,
//            StudentGateway studentGateway,
//            GetCoursesByEducationalProgramAndSemesters getCoursesByEducationalProgramAndSemesters) {
//        return new SelectCourses(
//                courseGateway,
//                semesterGateway,
//                studentGateway,
//                getCoursesByEducationalProgramAndSemesters);
//    }

    @Bean
    public EditModuleSpecialCourse editModuleSpecialCourse(CourseGateway courseGateway) {
        return new EditModuleSpecialCourse(courseGateway);
    }

    @Bean
    public DeleteCourse deleteCourse(CourseGateway courseGateway) {
        return new DeleteCourse(courseGateway);
    }

    @Bean
    public CreateCourse createCourse(ModuleGateway moduleGateway, CourseGateway courseGateway) {
        return new CreateCourse(moduleGateway, courseGateway);
    }

    @Bean
    public GetCourse getCourse(CourseGateway courseGateway) {
        return new GetCourse(courseGateway);
    }

    @Bean
    public GetSpecialCourseStudentsCount getSpecialCourseStudentsCount(CourseGateway courseGateway) {
        return new GetSpecialCourseStudentsCount(courseGateway);
    }

//    @Bean
//    public GetActualSpecialCoursesStatistics getActualSpecialCoursesStatistics(CourseGateway courseGateway) {
//        return new GetActualSpecialCoursesStatistics(courseGateway);
//    }

    @Bean
    public GetAllModules getAllModules(ModuleGateway moduleGateway) {
        return new GetAllModules(moduleGateway);
    }

    @Bean
    public GetModulesByIds getModulesByIds(ModuleGateway moduleGateway) {
        return new GetModulesByIds(moduleGateway);
    }

    @Bean
    public CreateModule createModuleWithCourses(ModuleGateway moduleGateway) {
        return new CreateModule(moduleGateway);
    }

    @Bean
    public DeleteModuleById deleteModuleById(ModuleGateway moduleGateway, CourseGateway courseGateway) {
        return new DeleteModuleById(moduleGateway, courseGateway);
    }

//    @Bean
//    public GetEducationalProgram getEducationalProgram(ProgramGateway programGateway, StudentGateway studentGateway) {
//        return new GetEducationalProgram(programGateway, studentGateway);
//    }

    @Bean
    public GetAllPrograms getAllEducationalPrograms(ProgramGateway programGateway) {
        return new GetAllPrograms(programGateway);
    }

//    @Bean
//    public GetSkillsForStudent getSkillsForStudent(SkillGateway skillGateway) {
//        return new GetSkillsForStudent(skillGateway);
//    }

    @Bean
    public SaveSkillsForStudent saveSkillsForStudent(SkillGateway skillGateway, StudentGateway studentGateway) {
        return new SaveSkillsForStudent(skillGateway, studentGateway);
    }

//    @Bean
//    public GetDesiredSkillsForStudent getDesiredSkillsForStudent(SkillGateway skillGateway) {
//        return new GetDesiredSkillsForStudent(skillGateway);
//    }

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

//    @Bean
//    public GetSelectedCoursesByStudentAndSemester getSelectedCoursesByStudentAndSemester(
//            CourseGateway courseGateway,
//            StudentGateway studentGateway) {
//        return new GetSelectedCoursesByStudentAndSemester(courseGateway, studentGateway);
//    }

    @Bean
    public GetProgramForStudent getProgramForStudent(StudentGateway studentGateway) {
        return new GetProgramForStudent(studentGateway);
    }

//    @Bean
//    public LoadAvailableCourses loadAvailableCourses(StudentGateway studentGateway, CourseGateway courseGateway) {
//        return new LoadAvailableCourses(studentGateway, courseGateway);
//    }

    @Bean
    public GetModuleWithCourses getModule(ModuleGateway moduleGateway) {
        return new GetModuleWithCourses(moduleGateway);
    }

    @Bean
    public GetGroupsByEducationalProgram getGroupForEducationalProgram(ProgramGateway programGateway) {
        return new GetGroupsByEducationalProgram(programGateway);
    }

    @Bean
    public CreateGroup createGroup(
            GroupGateway groupGateway,
            ProgramGateway programGateway,
            SemesterGateway semesterGateway,
            CreateBaseSyllabus createBaseSyllabus) {
        return new CreateGroup(groupGateway, programGateway, semesterGateway, createBaseSyllabus);
    }

    @Bean
    public GetAcademicGroup getAcademicGroup(GroupGateway groupGateway) {
        return new GetAcademicGroup(groupGateway);
    }

    @Bean
    public GenerateStudentRegistrationToken generateStudentRegistrationToken(
            GetAcademicGroup getAcademicGroup,
            ProgramGateway programGateway,
            StudentGateway studentGateway) {
        return new GenerateStudentRegistrationToken(getAcademicGroup, programGateway, studentGateway);
    }

    @Bean
    public GetTokensForGroup getTokensForGroup(GetAcademicGroup getAcademicGroup, StudentGateway studentGateway) {
        return new GetTokensForGroup(getAcademicGroup, studentGateway);
    }

    @Bean
    public DownloadTokens downloadTokens(GetAcademicGroup getAcademicGroup) {
        return new DownloadTokens(getAcademicGroup);
    }

    @Bean
    public CreateEducationalProgram createProgram(ProgramGateway programGateway) {
        return new CreateEducationalProgram(programGateway);
    }

    @Bean
    public GetProgramById getProgramById(ProgramGateway programGateway) {
        return new GetProgramById(programGateway);
    }

//    @Bean
//    public CreateStudyPlan createStudyPlan(
//            SemesterGateway semesterGateway,
//            StudyPlanGateway studyPlanGateway,
//            ProgramGateway programGateway,
//            CreateSemesterPlan createSemesterPlan) {
//        return new CreateStudyPlan(semesterGateway, studyPlanGateway, programGateway, createSemesterPlan);
//    }

    @Bean
    public UpdateProgram updateProgram(ProgramGateway programGateway) {
        return new UpdateProgram(programGateway);
    }

//    @Bean
//    public CreateSemesterPlan createSemesterPlan(SemesterPlanGateway semesterPlanGateway) {
//        return new CreateSemesterPlan(semesterPlanGateway);
//    }

    @Bean
    public GetAvailableYears getAvailableYears(ProgramGateway programGateway) {
        return new GetAvailableYears(programGateway);
    }

    @Bean
    public GetStudyPlan getStudyPlan(ProgramGateway programGateway) {
        return new GetStudyPlan(programGateway);
    }

    @Bean
    public GetModulesCourses getModulesCourses(CourseGateway courseGateway) {
        return new GetModulesCourses(courseGateway);
    }

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}
