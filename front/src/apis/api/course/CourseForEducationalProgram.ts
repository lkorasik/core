import {Control} from "../Control";
import {SemesterDto} from "../recommendation/SemesterDto";

export interface CourseForEducationalProgram {
    id: string;
    name: string;
    creditsCount: number;
    controlTypes: Control;
    description: string;
    semesters: SemesterDto[];
    moduleId: string | null;
    requiredSemesterId: string | null;
}
