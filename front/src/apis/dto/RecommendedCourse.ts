import {Control} from "./Control";
import {Semester} from "./Semester";
import {SkillDto} from "./SkillDto";

export interface RecommendedCourse {
    id: string;
    name: string;
    creditsCount: number;
    control: Control;
    description: string;
    semesters: Semester[];
    educationalModuleId: string | null;
    requiredSemesterId: string | null;
    requiredSkills: SkillDto[];
    resultSkills: SkillDto[];
}
