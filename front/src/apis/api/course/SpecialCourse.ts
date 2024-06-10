import {SemesterDto} from "../recommendation/SemesterDto";
import {Control} from "../Control";

export interface SpecialCourse {
    id: string;
    name: string;
    creditsCount: number;
    controlTypes: Control;
    description: string;
    semesterEntities: SemesterDto[];
    moduleId: string | null;
    department?: string;
    teacherName?: string;
}
