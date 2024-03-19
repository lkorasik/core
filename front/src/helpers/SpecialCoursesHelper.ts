import {SpecialCourse} from "../apis/api/course/SpecialCourse";

export class SpecialCoursesHelper {
    public static doesCourseFitSemester(specialCourse: SpecialCourse, semesterId: string) {
        return specialCourse.semesters.map(x => x.id).includes(semesterId);
    }

    public static filterCoursesHavingSemester(specialCourses: SpecialCourse[], semesterId: string) {
        return specialCourses.filter(x => SpecialCoursesHelper.doesCourseFitSemester(x, semesterId));
    }
}
