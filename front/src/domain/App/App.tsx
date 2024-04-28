import {FC, useEffect} from 'react';
import './App.css';
import {useAuthentication} from "../../hooks/useAuthentication";
import {Route, Routes, useLocation, useNavigate} from "react-router-dom";
import {UserRole} from "../../apis/api/UserRole";
import {LoginInfo} from "../../hooks/LoginInfo";
import {AdministratorMainScreen} from '../AdministratorMainScreen/AdministratorMainScreen';
import {EducationalProgramScreen} from '../EducationalProgramScreen/EducationalProgramScreen';
import {EditModuleScreen} from "../EditModuleScreen/EditModuleScreen";
import {CourseScreen} from "../CoursesScreen/CourseScreen";
import {EditModuleCourseScreen} from "../EditCoursesScreen/EditModuleCourseScreen";
import {WelcomeScreen} from '../WelcomeBackground/WelcomeBackground';
import {RecommendationsScreen} from "../RecommendationsScreen/RecommendationsScreen";
import { AddEducationalProgramScreen } from '../EducationalProgramScreen/AddEducationalProgramScreen/AddEducationalProgramScreen';
import {StatisticsScreen} from "../StatisticsScreen/StatisticsScreen";
import { StudentScreen } from '../StudentScreen/StudentScreen';
import { StudyPlan } from '../StudyPlan/StudyPlan';
import { ModuleList } from '../ModuleList/ModuleList';
import { ModulesScreen } from '../ModulesScreen';
import { ModuleCourses } from '../ModuleCourses/ModuleCourses';
import { EducationalProgramDetailsScreen } from '../EducationalProgramScreen/EducationalProgramDetailsScreen/EducationalProgramDetailsScreen';
import { AddGroupScreen } from '../EducationalProgramScreen/AddGroupScreen/AddGroupScreen';
import { GroupScreen } from '../EducationalProgramScreen/GroupScreen/GroupScreen';
import { EditEducationalProgramScreen } from '../EducationalProgramScreen/EditEducationalProgramScreen/EditEducationalProgramScreen';
import { AddModuleScreen } from '../AddModuleScreen/AddModuleScreen';
import { AddCourseScreen } from '../AddCourseScreen/AddCourseScreen';

export const EDUCATIONAL_PROGRAM_SCREEN_URL: string = "/administrator/educational_program/";
export const MODULES_SCREEN_URL: string = "/administrator/courses_and_modules";
export const MODULE_COURSES_SCREEN_URL: string = "/administrator/module/";
export const COURSES_SCREEN_URL: string = "/administrator/courses/";
export const EDIT_MODULE_SCREEN_URL: string = MODULE_COURSES_SCREEN_URL + "edit/";
export const EDIT_MODULE_COURSES_SCREEN_URL: string = COURSES_SCREEN_URL + "edit/";

export const STUDENT: string = "/studentEntity";
export const STUDENT_PLAN: string = "/student_plan";
export const ADMINISTRATOR: string = "/administrator";
export const EDUCATIONAL_PROGRAM: string = "/educational_program";
export const GROUP: string = "/group";
export const COURESE_AND_MODULES = "/courses_and_modules";
export const STATISTICS = "/statistics";
export const EDIT = "/edit";

const App: FC = () => {
    const {loginInfo, saveLoginInfo} = useAuthentication();
    const location = useLocation();
    const navigate = useNavigate();

    const navigateIfNeeded = (loginInfo: LoginInfo | null) => {
        if (loginInfo) {
            switch (loginInfo.userEntityRole) {
                case UserRole.Student:
                    navigate(STUDENT + STUDENT_PLAN);
                    break;
                case UserRole.Admin:
                    navigate(ADMINISTRATOR + EDUCATIONAL_PROGRAM)
                    break;
            }
        } else if (location.pathname !== "/") {
            navigate("/")
        }
    };

    useEffect(() => {
        navigateIfNeeded(loginInfo);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [loginInfo]);

    return (
        <Routes>
            <Route path={"/"} element={<WelcomeScreen saveLoginInfo={saveLoginInfo}/>}/>
            <Route path={ADMINISTRATOR} element={<AdministratorMainScreen/>}>
                <Route path={ADMINISTRATOR + EDUCATIONAL_PROGRAM} element={<EducationalProgramScreen/>}/>
                    <Route path={ADMINISTRATOR + EDUCATIONAL_PROGRAM + EDIT + "/:educationalProgramId"} element={<EditEducationalProgramScreen/>}/>
                <Route path={ADMINISTRATOR + COURESE_AND_MODULES} element={<ModulesScreen />}/>
                <Route path={ADMINISTRATOR + STATISTICS} element={<StatisticsScreen/>}/>

                    <Route path={ADMINISTRATOR + EDUCATIONAL_PROGRAM + "/:educationalProgramId"} element={<EducationalProgramDetailsScreen />}/>
                <Route path={ADMINISTRATOR + "/educational_program/add"} element={<AddEducationalProgramScreen />}/>
                <Route path={ADMINISTRATOR + GROUP + "/add"} element={<AddGroupScreen />} />
                <Route path={ADMINISTRATOR + GROUP + "/:groupId"} element={<GroupScreen />} />
                    <Route path={ADMINISTRATOR + "/module/add"} element={<AddModuleScreen />}/>
                    <Route path={ADMINISTRATOR + "/module/edit" + "/*"} element={<EditModuleScreen/>}/>
                <Route path={ADMINISTRATOR + "/module/:moduleId"} element={<ModuleCourses />}/>
                    <Route path={ADMINISTRATOR + "/courses/add"} element={<AddCourseScreen />}/>
                    <Route path={ADMINISTRATOR + "/courses" + "/*"} element={<CourseScreen/>}/>
                    <Route path={ADMINISTRATOR + "/module/edit" + "/*"} element={<EditModuleCourseScreen/>}/>
            </Route>
            <Route path={STUDENT + "/recommendationService"} element={<RecommendationsScreen />} />
            <Route path={STUDENT} element={<StudentScreen/>}>
                <Route path={STUDENT + STUDENT_PLAN} element={<StudyPlan/>}/>
                <Route path={STUDENT + COURESE_AND_MODULES} element={<ModuleList />}/>
            </Route>
        </Routes>
    );
}

export default App;
