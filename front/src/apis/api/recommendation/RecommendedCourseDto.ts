import {Control} from "../Control";
import {SemesterDto} from "./SemesterDto";
import {SkillDto} from "./SkillDto";

export interface RecommendedCourseDto {
    id: string;
    name: string;
    creditsCount: number;
    semesters: SemesterDto[];
    moduleId: string | null;
    requiredSemesterId: string | null;
    requiredSkills: SkillDto[];
    resultSkills: SkillDto[];
}
