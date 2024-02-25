import {Control} from "./Control";

export interface EditModuleSpecialCourseRequest {
    specialCourseId: string;
    courseName: string;
    creditsCount: number;
    controlType: Control;
    department: string;
    teacherName: string;
    courseDescription: string;
}
