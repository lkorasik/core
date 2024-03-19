import { CreateSemester } from "../../dto/CreateSemester";
import { FullSemester } from "../../dto/FullSemester";

export interface FullProgramDto {
    id: string;
    title: string
    recommendedCredits: number[]
    semesters: FullSemester[]
}
