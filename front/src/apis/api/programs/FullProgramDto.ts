import { CreateSemester } from "./CreateSemester";
import { FullSemester } from "./FullSemester";

export interface FullProgramDto {
    id: string;
    title: string
    recommendedCredits: number[]
    semesters: FullSemester[]
}
