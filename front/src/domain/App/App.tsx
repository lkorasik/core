import React, {FC, useEffect} from 'react';
import './App.css';
import {ChooseCoursesScreen} from "../ChooseCoursesScreen/ChooseCoursesScreen";
import {useAuthentication} from "../../hooks/useAuthentication";
import {Route, Routes, useLocation, useNavigate} from "react-router-dom";
import {EmployeeScreen} from "../EmployeeScreen/EmployeeScreen";
import {UserRole} from "../../apis/api/UserRole";
import {LoginInfo} from "../../hooks/LoginInfo";
import {AdministratorMainScreen} from '../AdministratorMainScreen/AdministratorMainScreen';
import {EducationalProgramScreen} from '../EducationalProgramScreen/EducationalProgramScreen';
import {ModulesScreen} from "../ModulesScreen/ModulesScreen";
import {AddModuleScreen} from "../AddModuleScreen/AddModuleScreen";
import {ModuleCoursesScreen} from "../ModuleCoursesScreen/ModuleCoursesScreen";
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
import { StudentModuleCourses } from '../StudentModuleCourses/StudentModuleCourses';

export const EDUCATIONAL_PROGRAM_SCREEN_URL: string = "/administrator/educational_program/";
export const MODULES_SCREEN_URL: string = "/administrator/courses_and_modules";
export const MODULE_COURSES_SCREEN_URL: string = "/administrator/module/";
export const COURSES_SCREEN_URL: string = "/administrator/courses/";
export const EDIT_MODULE_SCREEN_URL: string = MODULE_COURSES_SCREEN_URL + "edit/";
export const EDIT_MODULE_COURSES_SCREEN_URL: string = COURSES_SCREEN_URL + "edit/";

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
                case UserRole.UniversityEmployee:
                    navigate("/employee")
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
            <Route path={"/student"} element={<StudentScreen/>}>
                <Route path={"/student/student_plan"} element={<StudyPlan/>}/>
                <Route path={"/student/courses_and_modules"} element={<StudentModuleCourses/>}/>
            </Route>
            <Route path={"/student/recommendationService"} element={<RecommendationsScreen />} />
            <Route path={"/employee"} element={<EmployeeScreen/>}/>
            <Route path={"/administrator"} element={<AdministratorMainScreen/>}>
                <Route path={"/administrator/educational_program"} element={<EducationalProgramScreen/>}/>
                    <Route path={EDUCATIONAL_PROGRAM_SCREEN_URL + "*"} element={<AddEducationalProgramScreen />}/>
                <Route path={"/administrator/educational_program/add"} element={<AddEducationalProgramScreen />}/>
                <Route path={MODULES_SCREEN_URL} element={<ModulesScreen/>}/>
                    <Route path={"/administrator/module/add"} element={<AddModuleScreen/>}/>
                    <Route path={EDIT_MODULE_SCREEN_URL + "*"} element={<EditModuleScreen/>}/>
                <Route path={MODULE_COURSES_SCREEN_URL + "*"} element={<ModuleCoursesScreen/>}/>
                    <Route path={"/administrator/courses/add"} element={<AddModuleCourseScreen/>}/>
                    <Route path={COURSES_SCREEN_URL + "*"} element={<CourseScreen/>}/>
                    <Route path={EDIT_MODULE_COURSES_SCREEN_URL + "*"} element={<EditModuleCourseScreen/>}/>
                <Route path={"/administrator/statistics"} element={<StatisticsScreen/>}/>
            </Route>
        </Routes>
    );
}

export default App;
