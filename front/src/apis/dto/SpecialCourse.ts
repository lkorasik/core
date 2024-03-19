import {SemesterDto} from "../api/recommendation/SemesterDto";
import {Control} from "./Control";

export interface SpecialCourse {
    id: string;
    name: string;
    creditsCount: number;
    control: Control;
    description: string;
    semesters: SemesterDto[];
    educationalModuleId: string | null;
    department?: string;
    teacherName?: string;
}
