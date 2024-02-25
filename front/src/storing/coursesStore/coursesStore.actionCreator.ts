import {CoursesStoreAction, CoursesStoreActionType} from "./coursesStore";
import {CourseForEducationalProgram} from "../../apis/dto/CourseForEducationalProgram";
import {CoursesBySemesterDto} from "../../apis/dto/CoursesBySemesterDto";

class CoursesStoreActionCreatorClass {
    public addCourse(semesterId: string, specialCourse: CourseForEducationalProgram): CoursesStoreAction {
        return { type: CoursesStoreActionType.AddCourse, semesterId: semesterId, course: specialCourse };
    }
    public removeCourse(specialCourseId: string): CoursesStoreAction {
        return { type: CoursesStoreActionType.RemoveCourse, courseId: specialCourseId };
    }
    public clearCourses(): CoursesStoreAction {
        return { type: CoursesStoreActionType.ClearAllCourses };
    }
    public initSelectedCourses(allCourses: CourseForEducationalProgram[], selectedCourses: CoursesBySemesterDto[]): CoursesStoreAction {
        return {
            type: CoursesStoreActionType.InitSelectedCourses,
            allCourses: allCourses,
            selectedCourses: selectedCourses
        };
    }
    public addModuleCourses(courses: CourseForEducationalProgram[]): CoursesStoreAction {
        return { type: CoursesStoreActionType.AddModuleCourses, courses: courses };
    }
}

export const CoursesStoreActionCreator = new CoursesStoreActionCreatorClass();
