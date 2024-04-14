import {FC, useEffect} from 'react';
import './App.css';
import {useAuthentication} from "../../hooks/useAuthentication";
import {Route, Routes, useLocation, useNavigate} from "react-router-dom";
import {UserRole} from "../../apis/api/UserRole";
import {LoginInfo} from "../../hooks/LoginInfo";
import {AdministratorMainScreen} from '../AdministratorMainScreen/AdministratorMainScreen';
import {EducationalProgramScreen} from '../EducationalProgramScreen';
import {AddModuleScreen} from "../AddModuleScreen/AddModuleScreen";
import {AddModuleCourseScreen} from "../AddModuleCourseScreen/AddModuleCourseScreen";
import {EditModuleScreen} from "../EditModuleScreen/EditModuleScreen";
import {CourseScreen} from "../CoursesScreen/CourseScreen";
import {EditModuleCourseScreen} from "../EditCoursesScreen/EditModuleCourseScreen";
import {WelcomeScreen} from '../WelcomeBackground/WelcomeBackground';
import {RecommendationsScreen} from "../RecommendationsScreen/RecommendationsScreen";
import { AddEducationalProgramScreen } from '../AddEducationalProgramScreen/AddEducationalProgramScreen';
import {StatisticsScreen} from "../StatisticsScreen/StatisticsScreen";
import { StudentScreen } from '../StudentScreen/StudentScreen';
import { StudyPlan } from '../StudyPlan/StudyPlan';
import { ModuleList } from '../ModuleList/ModuleList';
import { ModulesScreen } from '../ModulesScreen';
import { ModuleCourses } from '../ModuleCourses/ModuleCourses';

export const EDUCATIONAL_PROGRAM_SCREEN_URL: string = "/administrator/educational_program/";
export const MODULES_SCREEN_URL: string = "/administrator/courses_and_modules";
export const MODULE_COURSES_SCREEN_URL: string = "/administrator/module/";
export const COURSES_SCREEN_URL: string = "/administrator/courses/";
export const EDIT_MODULE_SCREEN_URL: string = MODULE_COURSES_SCREEN_URL + "edit/";
export const EDIT_MODULE_COURSES_SCREEN_URL: string = COURSES_SCREEN_URL + "edit/";

export const STUDENT: string = "/student";
export const ADMINISTRATOR: string = "/administrator";

const App: FC = () => {
    const {loginInfo, saveLoginInfo} = useAuthentication();
    const location = useLocation();
    const navigate = useNavigate();

    const navigateIfNeeded = (loginInfo: LoginInfo | null) => {
        if (loginInfo) {
            switch (loginInfo.userRole) {
                case UserRole.Student:
                    navigate("/student/student_plan");
                    break;
                case UserRole.Admin:
                    navigate("/administrator/educational_program")
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
            <Route path={STUDENT} element={<StudentScreen/>}>
                <Route path={STUDENT + "/student_plan"} element={<StudyPlan/>}/>
                <Route path={STUDENT + "/courses_and_modules"} element={<ModuleList />}/>
            </Route>
            <Route path={STUDENT + "/recommendationService"} element={<RecommendationsScreen />} />
            <Route path={ADMINISTRATOR} element={<AdministratorMainScreen/>}>
                <Route path={ADMINISTRATOR + "/educational_program"} element={<EducationalProgramScreen/>}/>
                    <Route path={EDUCATIONAL_PROGRAM_SCREEN_URL + "*"} element={<AddEducationalProgramScreen />}/>
                <Route path={ADMINISTRATOR + "/educational_program/add"} element={<AddEducationalProgramScreen />}/>
                <Route path={MODULES_SCREEN_URL} element={<ModulesScreen />}/>
                    <Route path={ADMINISTRATOR + "/module/add"} element={<AddModuleScreen/>}/>
                    <Route path={EDIT_MODULE_SCREEN_URL + "*"} element={<EditModuleScreen/>}/>
                <Route path={ADMINISTRATOR + "/module/:moduleId"} element={<ModuleCourses />}/>
                    <Route path={ADMINISTRATOR + "/courses/add"} element={<AddModuleCourseScreen/>}/>
                    <Route path={COURSES_SCREEN_URL + "*"} element={<CourseScreen/>}/>
                    <Route path={EDIT_MODULE_COURSES_SCREEN_URL + "*"} element={<EditModuleCourseScreen/>}/>
                <Route path={ADMINISTRATOR + "/statistics"} element={<StatisticsScreen/>}/>
            </Route>
        </Routes>
    );
}

export default App;
