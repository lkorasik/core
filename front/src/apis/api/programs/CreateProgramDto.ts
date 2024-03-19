import { CreateSemester } from "../../dto/CreateSemester"

export interface CreateProgramDto {
    title: string
    recommendedCredits: number[]
    semesters: CreateSemester[]
}
