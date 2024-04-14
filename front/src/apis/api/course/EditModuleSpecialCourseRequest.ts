import {Control} from "../Control";

export interface EditModuleSpecialCourseRequest {
    courseId: string;
    courseName: string;
    creditsCount: number;
    controlTypes: Control;
    department: string;
    teacherName: string;
    courseDescription: string;
}
