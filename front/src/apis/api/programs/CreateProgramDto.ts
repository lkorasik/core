import { CreateSemester } from "./CreateSemester"

export interface CreateProgramDto {
    title: string
    recommendedCredits: number[]
    semesters: CreateSemester[]
}
