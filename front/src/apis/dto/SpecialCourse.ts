import {Semester} from "./Semester";
import {Control} from "./Control";

export interface SpecialCourse {
    id: string;
    name: string;
    creditsCount: number;
    control: Control;
    description: string;
    semesters: Semester[];
    educationalModuleId: string | null;
    department?: string;
    teacherName?: string;
}
