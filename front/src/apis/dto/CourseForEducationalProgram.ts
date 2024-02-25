import {Control} from "./Control";
import {Semester} from "./Semester";

export interface CourseForEducationalProgram {
    id: string;
    name: string;
    creditsCount: number;
    control: Control;
    description: string;
    semesters: Semester[];
    educationalModuleId: string | null;
    requiredSemesterId: string | null;
}
