import {Control} from "./Control";
import {SemesterDto} from "../api/recommendation/SemesterDto";

export interface CourseForEducationalProgram {
    id: string;
    name: string;
    creditsCount: number;
    control: Control;
    description: string;
    semesters: SemesterDto[];
    educationalModuleId: string | null;
    requiredSemesterId: string | null;
}
