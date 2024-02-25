import { CreateSemester } from "./CreateSemester";
import { FullSemester } from "./FullSemester";

export interface FullEducationalProgram {
    id: string;
    title: string
    recomendedCredits: number[]
    semesters: FullSemester[]
}
